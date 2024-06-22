package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.persistence.PersistentDataContainer

object EcononyMapArt: AbstractRLItem {
    override val baseItem: Material = Material.FILLED_MAP
    override val model: Int = 44301
    override val id: String = "econony_art"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val mapMeta = resultMeta as MapMeta

        mapMeta.mapId = 43
        mapMeta.displayName(Component.text("econony", TextColor.color(180,100,0)).decoration(TextDecoration.ITALIC, false))

        result.setItemMeta(mapMeta)
        return result
    }
}