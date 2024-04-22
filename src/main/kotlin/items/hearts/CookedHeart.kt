package items.hearts

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object CookedHeart: AbstractRLItem {
    override val baseItem: Material = Material.COOKED_BEEF
    override val model: Int = 44401
    override val id: String = "cooked_player_heart"
    

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(
            Component.text("Жареное сердце июльца", TextColor.color(86, 40, 7))
                .decoration(TextDecoration.ITALIC, false),
        )
        resultMeta.lore(
            listOf(
                Component.text("нямка :)"),
            ),
        )
        result.setItemMeta(resultMeta)
        return result
    }
}