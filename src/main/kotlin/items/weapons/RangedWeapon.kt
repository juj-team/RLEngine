package items.weapons

import items.AbstractRLItem
import items.weapons.modifiers.AmmoModifiers
import items.weapons.modifiers.WeaponModifiers
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.persistence.PersistentDataType
import util.RLEngineTaskManager

interface RangedWeapon: AbstractRLItem {
    val cooldown: Int
    val maxWeaponDamage: Int
    val damageKey: NamespacedKey
        get() = NamespacedKey("rle", "weapon_damage")
    override val baseItem: Material
        get() = Material.WOODEN_SWORD
    val cooldownKey: NamespacedKey
        get()= NamespacedKey("rle", "weapon_cooldown")
    val magCapacity: Int
    val magLoadKey: NamespacedKey
        get() = NamespacedKey("rle", "ammo_left")

    override fun createItem() {
        super.createItem()
        RLEngineTaskManager.runTask({
            Bukkit.getOnlinePlayers().filter{
                val mainHandItem = it.inventory.itemInMainHand
                val offHandItem = it.inventory.itemInOffHand
                compare(mainHandItem) || compare(offHandItem)
            }.forEach {
                val mainHandItem = it.inventory.itemInMainHand
                val offHandItem = it.inventory.itemInOffHand

                if (compare(mainHandItem)) onInventoryTick(it, mainHandItem)
                if (compare(offHandItem)) onInventoryTick(it, offHandItem)
            }
        }, 2L, 1L)
    }

    fun onInventoryTick(player: Player, item: ItemStack){
        reduceCooldown(player, player.inventory.itemInMainHand)
    }

    fun reduceCooldown(player: Player, item: ItemStack){
        val itemMeta = item.itemMeta as Damageable
        val cooldownRemaining: Int? = itemMeta.persistentDataContainer.get(cooldownKey, PersistentDataType.INTEGER)
        val strengthRemaining = when(itemMeta.persistentDataContainer.get(damageKey, PersistentDataType.INTEGER)){
            null -> maxWeaponDamage
            else -> itemMeta.persistentDataContainer.get(damageKey, PersistentDataType.INTEGER)!!
        }
        val damageVisual = when (cooldownRemaining) {
            null -> {
                itemMeta.persistentDataContainer.set(cooldownKey, PersistentDataType.INTEGER, 0)
                58 * (maxWeaponDamage - strengthRemaining) / maxWeaponDamage
            }
            0 -> {
                58 * (maxWeaponDamage - strengthRemaining) / maxWeaponDamage
            }
            else -> {
                itemMeta.persistentDataContainer.set(cooldownKey, PersistentDataType.INTEGER, cooldownRemaining - 1)
                58 * cooldownRemaining / cooldown
            }
        }
        itemMeta.damage = damageVisual
        item.setItemMeta(itemMeta)
        player.updateInventory()
    }
    fun getAmmoLeft(weapon: ItemStack): Int{
        val result = weapon.itemMeta.persistentDataContainer.get(magLoadKey, PersistentDataType.INTEGER) ?: 0
        return result
    }
    fun setAmmoLeft(weapon: ItemStack, amount: Int){
        val itemMeta = weapon.itemMeta
        itemMeta.persistentDataContainer.set(magLoadKey, PersistentDataType.INTEGER, amount)
        weapon.setItemMeta(itemMeta)
    }
    fun setOnCooldown(item: ItemStack){
        val itemMeta = item.itemMeta
        itemMeta.persistentDataContainer.set(cooldownKey, PersistentDataType.INTEGER, cooldown)
        item.setItemMeta(itemMeta)
    }

    fun checkItemAsAmmo(item: ItemStack) : Boolean
    fun transferModifierDataToEntity(arrow: Projectile, weapon: ItemStack, ammo: ItemStack){
        //transfer weapon mods
        WeaponModifiers.entries.forEach {
            if(WeaponModifiers.hasModifier(weapon.itemMeta.persistentDataContainer, it))
                arrow.persistentDataContainer.set(it.key, PersistentDataType.BOOLEAN, true)
        }
        //transfer ammo mods
        AmmoModifiers.entries.forEach {
            if(AmmoModifiers.hasModifier(ammo.itemMeta.persistentDataContainer, it))
                arrow.persistentDataContainer.set(it.key, PersistentDataType.BOOLEAN, true)
        }
    }
    @EventHandler
    fun onShoot(event: PlayerInteractEvent){
        if(event.player.gameMode == GameMode.SPECTATOR) return
        // preliminary checks
        if(event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        val weapon = event.item ?: return
        if(!compare(weapon)) return
        // check for cooldown
        val remainingCooldown = weapon.itemMeta.persistentDataContainer.get(cooldownKey, PersistentDataType.INTEGER) ?: return
        if(remainingCooldown > 0) return
        // main routine
        val ammoLeft = getAmmoLeft(weapon)
        if(ammoLeft > 0){
            shoot(event.player, weapon)
            // show ammo left
            event.player.sendActionBar(Component.text("${ammoLeft - 1}/${magCapacity}"))
            setAmmoLeft(weapon, ammoLeft - 1)
            if(!decreaseDurability(weapon)){
                event.player.world.playSound(
                    event.player.location,
                    Sound.BLOCK_ANVIL_LAND,
                    SoundCategory.PLAYERS,
                    5.0f,
                    0.5f
                )
                event.player.world.spawnParticle(
                    Particle.EXPLOSION_NORMAL,
                    event.player.eyeLocation.add(event.player.eyeLocation.direction),
                    10
                )
            }
        }
        else{
            reload(event.player, weapon)
        }

    }

    fun decreaseDurability(weapon: ItemStack): Boolean {
        val weaponMeta = weapon.itemMeta
        val durabilityRemaining = (weaponMeta.persistentDataContainer.get(damageKey, PersistentDataType.INTEGER) ?: maxWeaponDamage) - 1
        if(durabilityRemaining < 1){
            weapon.amount = 0
            return false
        }
        weaponMeta.persistentDataContainer.set(damageKey, PersistentDataType.INTEGER, durabilityRemaining)
        weapon.setItemMeta(weaponMeta)
        return true
    }

    fun reload(player: Player, weapon: ItemStack){
        val eligibleItemStacks = player.inventory.contents.filter { it != null && checkItemAsAmmo(it) && it.amount >= magCapacity }
        if(eligibleItemStacks.isEmpty()){
            player.sendActionBar(Component.text("Не хватает патронов :("))
            return
        }
        val reloadStack = eligibleItemStacks.first() ?: return
        reloadStack.amount -= magCapacity
        setAmmoLeft(weapon, magCapacity)
        val ammoLeftInPockets = player.inventory.contents.filter { it != null && checkItemAsAmmo(it) }.sumOf { it?.amount ?: 0 }
        player.sendActionBar(Component.text("Патронов осталось: $ammoLeftInPockets"))
        player.playSound(
            player,
            Sound.ITEM_CROSSBOW_QUICK_CHARGE_1,
            SoundCategory.PLAYERS,
            1.0f,
            1.0f
        )
        setOnCooldown(weapon)
    }
    fun shoot(player: Player, weapon: ItemStack)
}