package recipes.weapontable

import items.weapons.modifiers.AmmoModifiers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class WitheringAmmoWeaponTableRecipe : AmmoWeaponTableRecipe() {
    override val modifierKey = AmmoModifiers.WITHERED.key
    override val name = Component.text("Иссушающий патрон", TextColor.color(20,16,16))

    override val base = listOf(ItemStack(Material.ARROW, 16))
    override val metal = listOf(ItemStack(Material.COPPER_INGOT, 8))
    override val modifier = listOf(ItemStack(Material.WITHER_ROSE))
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))
}
