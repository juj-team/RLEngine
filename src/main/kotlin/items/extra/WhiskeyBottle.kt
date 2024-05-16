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

object WhiskeyBottle: AbstractRLItem {
    override val baseItem: Material = Material.POTION
    override val model: Int = 44301
    override val id: String = "whiskey_bottle"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val potionMeta = resultMeta as PotionMeta

        potionMeta.displayName(Component.text("Виски").decoration(TextDecoration.ITALIC, false))
        potionMeta.lore(listOf(Component.text("*")))

        val potionEffects = listOf(
            PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*25, 1),
            PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*20, 1),
            PotionEffect(PotionEffectType.DARKNESS, 20*40, 0),
        )
        potionEffects.forEach { potionMeta.addCustomEffect(it, false) }

        result.setItemMeta(potionMeta)
        return result
    }
}