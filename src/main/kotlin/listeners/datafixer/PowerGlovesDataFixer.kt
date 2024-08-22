package listeners.datafixer

import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object PowerGlovesDataFixer : AbstractDataFixer {
    private const val LORE = "Промышленные перчатки"
    private val materials = listOf(Material.NETHERITE_CHESTPLATE, Material.DIAMOND_CHESTPLATE)

    override fun checkItem(item: ItemStack): Boolean {
        if (item.type !in materials) return false

        if (item.itemMeta.hasCustomModelData()) return false

        val lore = item.lore()
        if (lore.isNullOrEmpty()) return false

        val firstLore = lore[0] as TextComponent

        return this.LORE in firstLore.content()
    }

    override fun fixItem(item: ItemStack) {
        val itemMeta = item.itemMeta
        itemMeta.setCustomModelData(1)
        item.setItemMeta(itemMeta)
    }
}
