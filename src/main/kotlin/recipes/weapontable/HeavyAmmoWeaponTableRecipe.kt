package recipes.weapontable

import items.weapons.modifiers.AmmoModifiers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HeavyAmmoWeaponTableRecipe : AmmoWeaponTableRecipe() {
    override val modifierKey = AmmoModifiers.BIG_AMMO.key
    override val name = Component.text("Крупнокалиберный патрон", TextColor.color(150,150,150))

    override val base = listOf(ItemStack(Material.ARROW, 16))
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 32))
    override val modifier = listOf(ItemStack(Material.COPPER_INGOT, 56))
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))

    override fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player
    ): ItemStack {
        val result = super.craft(base, metal, modifier, fuel, crafter)
        result.amount = 6
        return result
    }
}
