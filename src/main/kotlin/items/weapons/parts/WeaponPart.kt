package items.weapons.parts

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

interface WeaponPart: AbstractRLItem {
    override val baseItem: Material
        get() = Material.CHAIN_COMMAND_BLOCK
    val name: Component

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(name)
        result.setItemMeta(resultMeta)
        return result
    }
}