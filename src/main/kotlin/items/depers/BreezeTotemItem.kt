package items.depers

import util.RLEngineTaskManager
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object BreezeTotemItem: DepersTotemItem {
    override val model: Int = 11
    override val itemName: String = "Бриз"
    override val id: String = "depers_breeze"
    private const val RADIUS = 7.0
    init { this.createItem() }
    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        player.addPotionEffect(PotionEffect(PotionEffectType.WATER_BREATHING, 40, 0))
    }
    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return

        val revivalTask = Runnable{
            player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 20*20, 2))
            if(player.location.block.type != Material.WATER){
                player.addPotionEffect(PotionEffect(PotionEffectType.WEAKNESS, 20*40, 2))
            }
            else{
                player.addPotionEffect(PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20*40, 2))
                for (mob: Entity in player.world.getNearbyEntities(player.location, RADIUS, RADIUS, RADIUS)) {
                    if (mob !is LivingEntity || mob == player) continue
                    mob.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 20*40, 2))
                }
                //TODO Добавить облако пыли
            }
        }

        RLEngineTaskManager.runTaskLater(revivalTask, 1L)
    }
}