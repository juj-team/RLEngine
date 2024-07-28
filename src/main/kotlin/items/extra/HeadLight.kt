package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object HeadLight : AbstractRLItem {
    override val baseItem: Material = Material.LANTERN
    override val model: Int = 44301
    override val id: String = "bitard_helmet"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Налобный фонарь", TextColor.color(200,200,0)).decoration(TextDecoration.ITALIC, false))
        resultMeta.lore(
            listOf(
                Component.text("Светит", TextColor.color(200,200,200)).decoration(TextDecoration.ITALIC, false),
                Component.text("Наденьте командой /hat", TextColor.color(200,200,200)).decoration(TextDecoration.ITALIC, false)
            )
        )

        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)
        return result
    }
}