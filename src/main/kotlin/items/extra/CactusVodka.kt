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

object CactusVodka: AbstractRLItem {
    override val baseItem: Material = Material.POTION
    override val model: Int = 44301
    override val id: String = "cactus_vodka"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val potionMeta = resultMeta as PotionMeta

        potionMeta.displayName(Component.text("Кактусовая водка").decoration(TextDecoration.ITALIC, false))
        potionMeta.lore(listOf(Component.text("*")))

        val potionEffects = listOf(
            PotionEffect(PotionEffectType.CONFUSION, 20*15, 0),
            PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*20, 0),
            PotionEffect(PotionEffectType.POISON, 20*30, 0),
            PotionEffect(PotionEffectType.WITHER, 20*5, 0),
            PotionEffect(PotionEffectType.LUCK, 20*160, 2),
        )
        potionEffects.forEach { potionMeta.addCustomEffect(it, false) }

        result.setItemMeta(potionMeta)
        return result
    }
}