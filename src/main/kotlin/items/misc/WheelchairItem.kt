package items.misc

import items.AbstractRLItem
import mobs.Wheelchair
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object WheelchairItem: AbstractRLItem {
    override val baseItem: Material = Material.PIG_SPAWN_EGG
    override val model: Int = 44402
    override val id: String = "wheelchair_spawner"
    
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(
            Component.text("Инвалидная коляска", TextColor.color(200,100,250))
                .decoration(TextDecoration.ITALIC, false)
        )

        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)
        return result
    }

    @EventHandler
    fun onUse(event: PlayerInteractEvent){
        if(event.player.gameMode == GameMode.SPECTATOR) return
        if(event.action != Action.RIGHT_CLICK_BLOCK) return
        val item = event.item ?: return
        if(!compare(item)) return

        event.isCancelled = true
        item.amount -= 1
        Wheelchair(event.interactionPoint ?: return)
    }
}