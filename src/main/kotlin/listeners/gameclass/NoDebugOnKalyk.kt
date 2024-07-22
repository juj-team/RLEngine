package listeners.gameclass

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object NoDebugOnKalyk : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.DEBUG_STICK) return
        if (event.clickedBlock?.type != Material.PETRIFIED_OAK_SLAB) return

        event.isCancelled = true
        println(event.item)
    }
}