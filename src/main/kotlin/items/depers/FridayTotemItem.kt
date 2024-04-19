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

object FridayTotemItem: DepersTotemItem {
    override val itemName: String = "Пятница"
    override val model: Int = 4
    override val id: String = "depers_friday"
    init { this.createItem() }

    override fun onInventoryTick(player: Player) {
        if (!compare(player.inventory.itemInOffHand)) return
        //check main hand
        var item = player.inventory.itemInMainHand
        if (item.type == Material.POTION) {
            val bottleMeta = item.itemMeta as PotionMeta
            if (bottleMeta.basePotionData.type == PotionType.WATER) player.inventory.setItemInMainHand(getBeer())
            return
        }
        //check offhand
        item = player.inventory.itemInOffHand
        if (item.type == Material.POTION) {
            val bottleMeta = item.itemMeta as PotionMeta
            if (bottleMeta.basePotionData.type == PotionType.WATER) player.inventory.setItemInOffHand(getBeer())
        }
    }

    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return
        val revivalTask = Runnable{
            player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*20, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 1, 6))
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20*20, 0))
        }
        val hangoverTask = Runnable{
            player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20 * 30, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 20 * 30, 0))
        }
        RLEngineTaskManager.runTaskLater(revivalTask, 1L)
        RLEngineTaskManager.runTaskLater(hangoverTask, 20*20L)
    }

    private fun getBeer(): ItemStack {
        val beer = ItemStack(Material.POTION, 1)
        val beerMeta = beer.itemMeta as PotionMeta

        beerMeta.displayName(Component.text("пиво"))
        beerMeta.addCustomEffect(PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 1), true)
        beerMeta.addCustomEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 30, 1), true)
        beerMeta.addCustomEffect(PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 0), true)

        beerMeta.color = Color.fromRGB(250, 233, 111)
        beerMeta.basePotionData = PotionData(PotionType.WATER, false, false)

        beer.setItemMeta(beerMeta)
        return beer
    }
}