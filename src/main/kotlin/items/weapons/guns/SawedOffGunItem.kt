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
import org.bukkit.entity.Projectile
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object SawedOffGunItem: RangedWeapon {
    override val cooldown: Int = 50
    override val magCapacity: Int = 4
    override val model: Int = 44408
    override val maxWeaponDamage: Int = 256
    override val id: String = "sawed_off_gun"
    
    override fun checkItemAsAmmo(item: ItemStack): Boolean {
        return item.type == Material.ARROW
    }

    override fun shoot(player: Player, weapon: ItemStack) {
        val arrows = mutableListOf<Arrow>()
        for (arrowNum in -6..6) {
            val arrow = player.launchProjectile(
                Arrow::class.java,
                player.eyeLocation.direction
                    .multiply(5)
                    .rotateAroundY(Math.toRadians(arrowNum * (120.0 / 12)))
            )

            arrows.add(arrow)
        }

        arrows.forEach{it.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED}
        arrows.forEach { it.damage = 4.5 * (3 / 5.0)
            transferModifierDataToEntity(it as Projectile, weapon, ItemStack(Material.ARROW)) }
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
        resultMeta.displayName(
            Component.text("Обрез", TextColor.color(200, 150, 0)).decoration(
                TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }
}