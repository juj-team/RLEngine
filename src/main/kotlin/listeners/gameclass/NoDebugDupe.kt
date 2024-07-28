package listeners.gameclass

import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object NoDebugDupe : Listener {
    @EventHandler
    fun onDebugStickClick(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.DEBUG_STICK) return

        val clickedBlock = event.clickedBlock ?: return
        if (clickedBlock.type != Material.PETRIFIED_OAK_SLAB) return
        if (!Tag.CROPS.isTagged(clickedBlock.type)) return

        event.isCancelled = true
    }
}