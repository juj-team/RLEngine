package listeners.gameclass

import items.AbstractRLItem
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object CursedStubItem: AbstractRLItem {
    override val baseItem: Material = Material.CHARCOAL
    override val id: String = "cursed_stub"
    override val model: Int = 44403

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val nameComponent = Component.text("Проклятый огрызок", Style.style(TextColor.color(202, 16, 221), TextDecoration.ITALIC))
        resultMeta.displayName(nameComponent)

        resultMeta.lore(
            listOf(Component.text("Кажется, что-то пошло не по плану..."))
        )

        result.setItemMeta(resultMeta)

        return result
    }
}