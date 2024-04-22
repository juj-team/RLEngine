package items.depers

import org.bukkit.entity.Creeper
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import util.LivingEntitiesInRadius

object CacadooTotemItem: DepersTotemItem {
    override val model: Int = 3
    override val itemName: String = "Какаду"
    override val id: String = "depers_cacadoo"
    

    private const val FORCE = 20.0
    private const val RADIUS = 5.0
    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        for(mob in LivingEntitiesInRadius.get(player, RADIUS)){
            if(mob is Creeper) {
                mob.explosionRadius = 0
                mob.fuseTicks = mob.maxFuseTicks-10
                mob.isIgnited = true
            }
        }
    }

    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return

        player.world.createExplosion(player.location, 0f)
        for(mob in LivingEntitiesInRadius.get(player, RADIUS)){
            mob.damage(7.0, player)
            mob.velocity = mob.location.toVector().subtract(player.location.toVector()).normalize().multiply(FORCE)
        }
    }

}