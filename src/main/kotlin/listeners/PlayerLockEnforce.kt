package listeners

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.persistence.PersistentDataType

object PlayerLockEnforce : Listener {

    @EventHandler
    fun enforceLock(event: PlayerInteractEvent) {
        if (event.player.persistentDataContainer.get(
                NamespacedKey("rle", "locked"),
                PersistentDataType.BOOLEAN,
            ) == true
        ) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun enforceLock(event: PlayerMoveEvent) {
        if (event.player.persistentDataContainer.get(
                NamespacedKey("rle", "locked"),
                PersistentDataType.BOOLEAN,
            ) == true
        ) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun enforceLock(event: InventoryOpenEvent) {
        if (event.player.persistentDataContainer.get(
                NamespacedKey("rle", "locked"),
                PersistentDataType.BOOLEAN,
            ) == true
        ) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun enforceLock(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) return
        if (event.entity.persistentDataContainer.get(
                NamespacedKey("rle", "locked"),
                PersistentDataType.BOOLEAN,
            ) == true
        ) {
            event.isCancelled = true
        }
    }
}