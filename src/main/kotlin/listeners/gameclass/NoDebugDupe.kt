package listeners.gameclass

import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object NoDebugDupe : Listener {
    private val forbiddenMaterials = listOf(
        Material.PETRIFIED_OAK_SLAB,
        Material.DECORATED_POT,
        Material.SEA_PICKLE,
        Material.TURTLE_EGG,
        Material.NETHER_WART,
        Material.SWEET_BERRY_BUSH,
    )

    private val forbiddenTags = listOf(
        Tag.CROPS,
        Tag.CANDLES,
        Tag.SLABS,
        Tag.BEDS,
        Tag.FLOWERS,
        Tag.CAVE_VINES,
        Tag.BEEHIVES,
    )

    @EventHandler
    fun onDebugStickClick(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.DEBUG_STICK) return

        val clickedBlock = event.clickedBlock ?: return

        val materialOverlap = forbiddenMaterials.any { clickedBlock.type == it }
        val tagOverlap = forbiddenTags.any { it.isTagged(clickedBlock.type) }

        if (materialOverlap || tagOverlap) event.isCancelled = true
    }
}