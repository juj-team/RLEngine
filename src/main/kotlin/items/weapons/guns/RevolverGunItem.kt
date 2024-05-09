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

object RevolverGunItem: RangedWeapon {
    override val cooldown: Int = 50
    override val magCapacity: Int = 6
    override val model: Int = 44405
    override val id: String = "revolver_gun"
    override fun checkItemAsAmmo(item: ItemStack): Boolean {
        return item.type == Material.ARROW
    }

    override fun shoot(player: Player, weapon: ItemStack) {
        val arrow = player.launchProjectile(
            Arrow::class.java,
            player.eyeLocation.direction.multiply(4.5)
        )
        arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
        arrow.damage = 10.0
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
        resultMeta.displayName(Component.text("Револьвер (настоящий)", TextColor.color(200, 150, 0)).decoration(TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }
}