package listeners.weapons

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Snowball
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object BrickThrowListener: Listener {
    @EventHandler
    fun onBrickThrow(event: PlayerInteractEvent){
        if(event.useItemInHand() == Event.Result.DENY) return
        if(event.action !in listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)) return
        val item = event.item ?: return
        val player = event.player
        if (item.type != Material.BRICK) return

        item.amount -= 1
        val brick = player.launchProjectile(
            Snowball::class.java,
            player.eyeLocation.direction.multiply(0.5).add(player.velocity)
        )
        brick.item = ItemStack(Material.BRICK)
    }

    @EventHandler
    fun onBrickHit(event: ProjectileHitEvent){
        if(event.isCancelled) return
        if(event.hitEntity !is LivingEntity) return
        if(event.entity !is Snowball) return
        val snowball = event.entity as Snowball
        if(snowball.item.type == Material.BRICK){
            (event.hitEntity as LivingEntity).damage(4.0)
        }
    }
}