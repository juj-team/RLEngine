package items.ingredients

import items.AbstractRLItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object CopperGearItem : AbstractRLItem {
    override val baseItem = Material.DEAD_TUBE_CORAL_FAN
    override val model = null
    override val id = "copper_gear"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        return result
    }
}
