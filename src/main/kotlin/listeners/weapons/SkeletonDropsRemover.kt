package listeners.weapons

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

object SkeletonDropsRemover: Listener {
    @EventHandler
    fun onSkeletonDrop(event: EntityDeathEvent){
        if(event.isCancelled) return
        if(event.entityType != EntityType.SKELETON) return
        val entity = event.entity

        val drops = event.drops.iterator()
        // Iterate through the drops and remove bows and arrows
        while (drops.hasNext()) {
            val drop = drops.next()
            if (drop.type == Material.BOW || drop.type == Material.ARROW) {
                drops.remove()
            }
        }
    }
}