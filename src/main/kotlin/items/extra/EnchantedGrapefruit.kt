package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object EnchantedGrapefruit: AbstractRLItem {
    override val baseItem: Material = Material.GLOW_BERRIES
    override val model: Int = 44301
    override val id: String = "enchanted_grapefruit"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Зачарованный грейпфрут"))
        resultMeta.lore(
            listOf(
                Component.text("Небольшой артефакт церкви Грейпфрута")
            )
        )

        resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        result.setItemMeta(resultMeta)
        return result
    }
}