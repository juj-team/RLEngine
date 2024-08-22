package listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object HatRightButtonWearListener : Listener {
    @EventHandler
    private fun onRightButton(event: PlayerInteractEvent) {
        if (event.isCancelled && event.action != Action.RIGHT_CLICK_AIR) return

        if (event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) return

        val inventory = event.player.inventory
        val item = inventory.itemInMainHand
        if (item.type != Material.REPEATING_COMMAND_BLOCK) return
        if (!item.itemMeta.hasCustomModelData()) return

        val clickedBlock = event.clickedBlock
        if (clickedBlock != null && clickedBlock.type.isInteractable) return

        val helmetItem = inventory.helmet
        inventory.setItemInMainHand(helmetItem)

        inventory.helmet = item

        event.isCancelled = true
    }
}