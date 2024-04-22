package items.depers

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import util.LivingEntitiesInRadius
import util.RLEngineTaskManager

object DuloTotemItem: DepersTotemItem {
    override val itemName: String = "Дуло"
    override val model: Int = 5
    override val id: String = "depers_dulo"
    private const val RADIUS = 5.0
    private const val FORCE = 100.0
    

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        for(mob in player.world.getNearbyEntities(player.location, RADIUS, RADIUS, RADIUS)){
            if(mob.type == EntityType.FIREBALL) mob.remove()
        }
    }
    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return

        val revivalTask = Runnable{
            player.world.createExplosion(player.location, 0f)
            for(mob in LivingEntitiesInRadius.get(player, RADIUS)) mob.velocity = mob.location.toVector().subtract(player.location.toVector()).normalize().multiply(FORCE)
            //TODO ЧЕРНЫЕ ЧАСТИЦЫ
        }

        RLEngineTaskManager.runTaskLater(revivalTask, 1L)
    }
}