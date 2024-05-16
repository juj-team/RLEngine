package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object RumBottle: AbstractRLItem {
    override val baseItem: Material = Material.POTION
    override val model: Int = 44301
    override val id: String = "rum_bottle"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val potionMeta = resultMeta as PotionMeta

        potionMeta.displayName(Component.text("Ром").decoration(TextDecoration.ITALIC, false))
        potionMeta.lore(listOf(Component.text("*")))

        val potionEffects = listOf(
            PotionEffect(PotionEffectType.FAST_DIGGING, 20*90, 0),
            PotionEffect(PotionEffectType.WEAKNESS, 20*40, 1),
            PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20*120, 0),
            PotionEffect(PotionEffectType.SATURATION, 20*90, 1),
        )
        potionEffects.forEach { potionMeta.addCustomEffect(it, false) }

        result.setItemMeta(potionMeta)
        return result
    }
}