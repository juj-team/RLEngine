package items.depers

import util.RLEngineTaskManager
import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

interface DepersTotemItem: AbstractRLItem {
    val itemName: String
    override val baseItem: Material
        get() = Material.TOTEM_OF_UNDYING

    override fun createItem() {
        super.createItem()
        RLEngineTaskManager.runTask({ Bukkit.getOnlinePlayers().forEach { onInventoryTick(it) }}, 1L, 1L)
    }

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(
            Component.text(itemName, NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
        )
        result.setItemMeta(resultMeta)
        return result
    }
    fun onInventoryTick(player: Player)
    fun onPlayerResurrect(event: EntityResurrectEvent)
}