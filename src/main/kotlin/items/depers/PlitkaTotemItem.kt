package items.depers

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

object PlitkaTotemItem: DepersTotemItem {
    override val itemName: String = "Плитка"
    override val model: Int = 14
    override val id: String = "depers_plitka"
    

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        if(!player.isSneaking) return
        // spawn particles
        if(Random.nextDouble(0.0,1.0) > 0.7) {
            for(i in 0..Random.nextInt(10, 20)){
                player.world.spawnParticle(
                    Particle.EXPLOSION_NORMAL, player.location, 0,
                    Random.nextDouble(0.0, 0.1), Random.nextDouble(0.1, 0.2), Random.nextDouble(0.0, 0.1),
                    0.1)
            }
        }
        // give effect
        val world = player.world
        val radius = 1.0
        for(mob: Entity in world.getNearbyEntities(player.location, radius, radius, radius)){
            if(mob is Item && mob.itemStack.type == Material.IRON_INGOT && !player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
                mob.itemStack.amount -= 1
                player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*40, 1))
            }
        }
    }

    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        TODO("Это всё равно никогда не произойдёт")
    }

}