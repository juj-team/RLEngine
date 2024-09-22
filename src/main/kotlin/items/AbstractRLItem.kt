package items

import listeners.RLEngineListeners
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

interface AbstractRLItem: Listener {
    @Suppress("SameReturnValue")
    val baseItem: Material
    val model: Int?
    val id: String
    fun createItem(){
        RLEngineItems.registerItem(id, this)
        RLEngineListeners.register(this)
    }
    fun getItem(
        result: ItemStack = ItemStack(baseItem),
        resultMeta: ItemMeta = result.itemMeta,
        resultPDC: PersistentDataContainer = resultMeta.persistentDataContainer
    ): ItemStack

    fun compare(other: ItemStack): Boolean {
        val hasCustomModel = model != null

        if (!other.hasItemMeta()) return !hasCustomModel
        val otherMeta = other.itemMeta

        if(!otherMeta.hasCustomModelData()) return !hasCustomModel
        val otherModel = otherMeta.customModelData

        if (hasCustomModel) return other.type == baseItem && otherModel == model

        return other.type == baseItem
    }
}