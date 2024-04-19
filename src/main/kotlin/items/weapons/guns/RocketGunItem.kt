package items.weapons.guns

import items.weapons.RangedWeapon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object RocketGunItem : RangedWeapon {
    override val cooldown: Int = 20
    override val magCapacity: Int = 1
    override val model: Int = 44406
    override val id: String = "rocket_gun"
    init{
        this.createItem()
    }

    override fun checkItemAsAmmo(item: ItemStack): Boolean {
        return item.type == Material.FIRE_CHARGE
    }

    override fun shoot(player: Player, weapon: ItemStack) {
        val force = 2
        val launchDirection = player.eyeLocation.direction.multiply(-1)
        player.world.playSound(
            player.location,
            Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,
            SoundCategory.PLAYERS,
            1.0f,
            1.0f
        )
        player.world.spawnParticle(
            Particle.EXPLOSION_HUGE,
            player.location,
            1
        )
        player.velocity = player.velocity.add(launchDirection.multiply(force))
    }


    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(Component.text("Ракетница", TextColor.color(200, 150, 0)).decoration(TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }

}