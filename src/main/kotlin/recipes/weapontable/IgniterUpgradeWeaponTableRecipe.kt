package recipes.weapontable

import items.weapons.guns.CollapseRifleGunItem
import items.weapons.guns.LightRifleGunItem
import items.weapons.modifiers.WeaponModifiers
import items.weapons.parts.IgniterItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class IgniterUpgradeWeaponTableRecipe : UpgradeWeaponTableRecipe() {
    override val base = listOf(
        CollapseRifleGunItem.getItem(),
        LightRifleGunItem.getItem(),
    )

    override val modifierKey = WeaponModifiers.IGNITER.key
    override val lore = Component.text("Установлен фитиль", TextColor.color(150,150,150))

    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(IgniterItem.getItem())
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))
}
