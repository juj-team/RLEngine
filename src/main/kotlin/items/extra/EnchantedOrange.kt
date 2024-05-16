package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object EnchantedOrange: AbstractRLItem {
    override val baseItem: Material = Material.CARROT
    override val model: Int = 44301
    override val id: String = "enchanted_orange"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Зачарованный апельсин"))
        resultMeta.lore(
            listOf(
                Component.text("Небольшой артефакт культа Апельсина")
            )
        )

        resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        result.setItemMeta(resultMeta)
        return result
    }
}