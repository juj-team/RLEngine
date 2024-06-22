package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object RiceBowl: AbstractRLItem {
    override val baseItem: Material = Material.COOKED_BEEF
    override val model: Int = 44301
    override val id: String = "rice_bowl"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Миска риса"))

        result.setItemMeta(resultMeta)
        return result
    }

}