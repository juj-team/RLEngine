package listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object NoCustomJukeboxPlacement : Listener {
    @EventHandler
    private fun onPlace(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val item = event.item ?: return
        if (item.type != Material.JUKEBOX) return

        if (!item.itemMeta.hasCustomModelData()) return

        event.isCancelled = true
    }
}