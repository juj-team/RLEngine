package recipes.weapontable

import items.weapons.guns.CollapseRifleGunItem
import items.weapons.guns.LightRifleGunItem
import items.weapons.modifiers.WeaponModifiers
import items.weapons.parts.BarrelGrenadeItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GrenadesUpgradeWeaponTableRecipe : UpgradeWeaponTableRecipe() {
    override val base = listOf(
        CollapseRifleGunItem.getItem(),
        LightRifleGunItem.getItem(),
    )

    override val modifierKey = WeaponModifiers.GRENADES.key
    override val lore = Component.text("Установлен гранатомёт", TextColor.color(150,150,150))

    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(BarrelGrenadeItem.getItem())
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))
}
