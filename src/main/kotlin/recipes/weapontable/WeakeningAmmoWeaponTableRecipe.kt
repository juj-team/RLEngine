package recipes.weapontable

import items.weapons.modifiers.AmmoModifiers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class WeakeningAmmoWeaponTableRecipe : AmmoWeaponTableRecipe() {
    override val modifierKey = AmmoModifiers.WEAKENING.key
    override val name = Component.text("Ослабляющий патрон", TextColor.color(79,1,6))

    override val base = listOf(ItemStack(Material.ARROW, 16))
    override val metal = listOf(ItemStack(Material.COPPER_INGOT))
    override val modifier = listOf(ItemStack(Material.HONEYCOMB))
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))
}
