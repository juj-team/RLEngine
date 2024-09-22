package items.ingredients

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object CopperBucketItem : AbstractRLItem {
    override val baseItem = Material.CHAIN_COMMAND_BLOCK
    override val model = 44406
    override val id = "copper_bucket"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(
            Component.text("Ведро меди").decoration(TextDecoration.ITALIC, false)
                .color(TextColor.color(255, 255, 255))
        )

        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)

        return result
    }
}