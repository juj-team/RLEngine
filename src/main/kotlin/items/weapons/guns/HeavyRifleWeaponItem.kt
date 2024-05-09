package items.weapons.guns

import items.weapons.RangedWeapon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Particle
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

object HeavyRifleWeaponItem: RangedWeapon {
    override val cooldown: Int = 80
    override val magCapacity: Int = 1
    override val model: Int = 44402
    override val id: String = "heavy_rifle_gun"
    
    override fun checkItemAsAmmo(item: ItemStack): Boolean {
        return item.type == Material.ARROW
    }

    override fun onInventoryTick(player: Player, item: ItemStack) {
        super.onInventoryTick(player, item)
        player.addPotionEffect(
            PotionEffect(PotionEffectType.SLOW, 20*2, 3, false, false, false)
        )
    }

    override fun shoot(player: Player, weapon: ItemStack) {
        val arrow = player.launchProjectile(
            Arrow::class.java,
            player.eyeLocation.direction.multiply(6)
        )
        arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
        arrow.damage = 5.0* (3/6.0)
        player.world.playSound(
            player.location,
            Sound.BLOCK_ANVIL_LAND,
            SoundCategory.PLAYERS,
            5.0f,
            0.5f
        )
        player.world.spawnParticle(
            Particle.EXPLOSION_NORMAL,
            player.eyeLocation.add(player.eyeLocation.direction),
            10
        )
    }


    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(
            Component.text("БОЛЬШАЯ СУКА", TextColor.color(250, 200, 0)).decoration(
                TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }
}