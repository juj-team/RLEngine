package items.weapons.guns

import items.weapons.RangedWeapon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object MachineGunItem: RangedWeapon {
    override val cooldown: Int = 0
    override val magCapacity: Int = 64
    override val model: Int = 44404
    override val id: String = "machine_gun"
    
    override fun checkItemAsAmmo(item: ItemStack): Boolean {
        return item.type == Material.ARROW
    }

    override fun onInventoryTick(player: Player, item: ItemStack) {
        super.onInventoryTick(player, item)
        player.addPotionEffect(
            PotionEffect(PotionEffectType.SLOW, 20*2, 127, false, false, false)
        )
    }
    override fun shoot(player: Player, weapon: ItemStack) {
        val arrow = player.launchProjectile(
            Arrow::class.java,
            player.eyeLocation.direction.multiply(3)
        )
        arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
        arrow.damage = 4.5
        arrow.persistentDataContainer
        player.world.playSound(
            player.location,
            Sound.ITEM_CROSSBOW_SHOOT,
            SoundCategory.PLAYERS,
            1.0f,
            1.0f
        )
    }


    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(Component.text("PIPA THE BANANGUN", TextColor.color(250, 200, 0)).decoration(TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }
}