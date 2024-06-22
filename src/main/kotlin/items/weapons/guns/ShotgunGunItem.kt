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

object ShotgunGunItem: RangedWeapon {
    override val cooldown: Int = 70
    override val magCapacity: Int = 1
    override val model: Int = 44407
    override val maxWeaponDamage: Int = 256
    override val id: String = "shotgun_gun"
    
    override fun checkItemAsAmmo(item: ItemStack): Boolean {
        return item.type == Material.ARROW
    }

    override fun shoot(player: Player, weapon: ItemStack) {
        val arrows = listOf(
                player.launchProjectile(
                Arrow::class.java,
                player.eyeLocation.direction.multiply(4)
            ),
            player.launchProjectile(
                Arrow::class.java,
                player.eyeLocation.direction.multiply(4).rotateAroundY(Math.toRadians(3.5))
            ),
            player.launchProjectile(
                Arrow::class.java,
                player.eyeLocation.direction.multiply(4).rotateAroundY(Math.toRadians(-3.5))
            ),
            player.launchProjectile(
                Arrow::class.java,
                player.eyeLocation.direction.multiply(4).rotateAroundY(Math.toRadians(7.0))
            ),
            player.launchProjectile(
                Arrow::class.java,
                player.eyeLocation.direction.multiply(4).rotateAroundY(Math.toRadians(-7.0))
            ),

        )
        arrows.forEach{it.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED}
        arrows.forEach { it.damage = 4.5 * (3/4.0)
            transferModifierDataToEntity(it as Projectile, weapon, ItemStack(Material.ARROW)) }
        player.world.playSound(
            player.location,
            Sound.ITEM_CROSSBOW_SHOOT,
            SoundCategory.PLAYERS,
            1.0f,
            1.0f
        )
        val force = 1.2
        val launchDirection = player.eyeLocation.direction.multiply(-1)
        player.velocity = player.velocity.add(launchDirection.multiply(force))
    }


    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(
            Component.text("Дробовик", TextColor.color(200, 150, 0)).decoration(
                TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }
}