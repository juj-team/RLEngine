package items.hearts

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object Heart: AbstractRLItem {
    override val baseItem: Material = Material.CHAIN_COMMAND_BLOCK
    override val model: Int = 44410
    override val id: String = "player_heart"
    

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {

        resultMeta.setCustomModelData(model)
        resultMeta.displayName(Component.text("Сердце июльца").decoration(TextDecoration.ITALIC, false))

        result.setItemMeta(resultMeta)
        return result
    }
}