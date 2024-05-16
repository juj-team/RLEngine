package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object ZippoLighter: AbstractRLItem {
    override val baseItem: Material = Material.FLINT_AND_STEEL
    override val model: Int = 44301
    override val id: String = "zippo"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("zippo").decoration(TextDecoration.ITALIC,false))
        resultMeta.lore(
            listOf(
                Component.text("Оригинальный.").decoration(TextDecoration.ITALIC, false)
            )
        )

        result.setItemMeta(resultMeta)
        return result
    }
}