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

object CoffeeBottle: AbstractRLItem {
    override val baseItem: Material = Material.POTION
    override val model: Int = 44301
    override val id: String = "coffee_bottle"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val potionMeta = resultMeta as PotionMeta

        potionMeta.displayName(Component.text("Кофе").decoration(TextDecoration.ITALIC, false))
        potionMeta.lore(listOf(Component.text("в бутылке")))

        val potionEffects = listOf(
            PotionEffect(PotionEffectType.SPEED, 20*20, 0),
        )
        potionEffects.forEach { potionMeta.addCustomEffect(it, false) }

        result.setItemMeta(potionMeta)
        return result
    }
}