package items.depers

import util.RLEngineTaskManager
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType

object DurilkaTotemItem: DepersTotemItem {
    override val itemName: String = "Дурилка"
    override val model: Int = 15
    override val id: String = "depers_durilka"
    private val potionEffectTypes = arrayOf<PotionEffectType>(
        PotionEffectType.SLOW,
        PotionEffectType.CONFUSION,
        PotionEffectType.BLINDNESS,
        PotionEffectType.DARKNESS,
        PotionEffectType.HUNGER,
        PotionEffectType.POISON,
        PotionEffectType.SLOW_DIGGING,
        PotionEffectType.UNLUCK,
        PotionEffectType.WEAKNESS
    )
    

    override fun onInventoryTick(player: Player) {
        if (!compare(player.inventory.itemInOffHand)) return
        potionEffectTypes.forEach {
            if (player.hasPotionEffect(it)) player.removePotionEffect(it)
        }

        //check main hand
        var item = player.inventory.itemInMainHand
        if (item.type == Material.POTION) {
            val bottleMeta = item.itemMeta as PotionMeta
            if (bottleMeta.basePotionData.type == PotionType.WATER) player.inventory.setItemInMainHand(getCoffee())
            return
        }
        //check offhand
        item = player.inventory.itemInOffHand
        if (item.type == Material.POTION) {
            val bottleMeta = item.itemMeta as PotionMeta
            if (bottleMeta.basePotionData.type == PotionType.WATER) player.inventory.setItemInOffHand(getCoffee())
            return
        }

    }

    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if (event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if (!compare(player.inventory.getItem(hand))) return

        val revivalTask = Runnable {
            player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 45, 2))
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20 * 45, 2))
            player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 20 * 45, 2))
        }
        val hangoverTask = Runnable {
            player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20 * 40, 3))
        }
        RLEngineTaskManager.runTaskLater(revivalTask, 1L)
        RLEngineTaskManager.runTaskLater(hangoverTask, 20 * 45L)

    }

    private fun getCoffee(): ItemStack {
        val coffee = ItemStack(Material.POTION, 1)
        val coffeeMeta = coffee.itemMeta as PotionMeta

        coffeeMeta.displayName(Component.text("кофе"))
        coffeeMeta.addCustomEffect(PotionEffect(PotionEffectType.SPEED, 20 * 20, 1), true)

        coffeeMeta.color = Color.fromRGB(255, 87, 51)
        coffeeMeta.basePotionData = PotionData(PotionType.WATER, false, false)

        coffee.setItemMeta(coffeeMeta)
        return coffee
    }
}