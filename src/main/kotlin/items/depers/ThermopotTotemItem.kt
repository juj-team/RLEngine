package items.depers

import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.LivingEntitiesInRadius

object ThermopotTotemItem: DepersTotemItem {
    override val itemName: String = "Термопот"
    override val model: Int = 10
    override val id: String = "depers_thermo"
    private const val RADIUS = 5.0
    

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        if(player.hasPotionEffect(PotionEffectType.POISON)) player.removePotionEffect(PotionEffectType.POISON)
        player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0))
    }

    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return

        for(mob in LivingEntitiesInRadius.get(player, RADIUS)){
            mob.damage(6.5)
            mob.fireTicks = 160
        }
        player.world.spawnParticle(Particle.FLAME, player.location, 30)
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_BURN, 1.0f, 1.0f)

    }

}