package items.depers

import util.RLEngineTaskManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object FanTotemItem: DepersTotemItem {
    override val itemName: String = "Ветряк"
    override val model: Int = 1
    override val id: String = "depers_fan"
    

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        player.fallDistance = 0f
    }

    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return
        val revivalTask = Runnable{
            player.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 20*1, 30))
            player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 20*20, 0))
        }
        RLEngineTaskManager.runTaskLater(revivalTask, 1L)
    }

}