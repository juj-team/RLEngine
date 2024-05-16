package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object BlackHat : AbstractRLItem {
    override val baseItem: Material = Material.CARVED_PUMPKIN
    override val model: Int = 44301
    override val id: String = "black_hat"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Шляпа").decoration(TextDecoration.ITALIC, false))
        resultMeta.lore(listOf(Component.text("black")))

        result.setItemMeta(resultMeta)
        return result
    }
}