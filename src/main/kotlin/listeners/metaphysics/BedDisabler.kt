package listeners.metaphysics

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBedEnterEvent

object BedDisabler: Listener {
    @EventHandler
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) {
        // Cancel any bed interactions
        event.isCancelled = true
    }
}