package recipes.weapontable

import items.weapons.modifiers.AmmoModifiers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class PoisonedAmmoWeaponTableRecipe : AmmoWeaponTableRecipe() {
    override val modifierKey = AmmoModifiers.POISONED.key
    override val name = Component.text("Отравляющий патрон", TextColor.color(40,80,0))

    override val base = listOf(ItemStack(Material.ARROW, 16))
    override val metal = listOf(ItemStack(Material.FERMENTED_SPIDER_EYE))
    override val modifier = listOf(ItemStack(Material.GLASS))
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))
}
