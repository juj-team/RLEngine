package items.metaphysics

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object AntiDepersBootsItem : AbstractRLItem {
    override val baseItem = Material.LEATHER_BOOTS
    override val model = 400
    override val id = "anti_depers_boots"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(
            Component.text("Ботинки с деперс-защитой")
                .decoration(TextDecoration.ITALIC, false),
        )

        resultMeta.lore(
            listOf(
                Component.text("Защита от заражения")
                    .decoration(TextDecoration.ITALIC, false),
            )
        )

        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)

        return result
    }
}