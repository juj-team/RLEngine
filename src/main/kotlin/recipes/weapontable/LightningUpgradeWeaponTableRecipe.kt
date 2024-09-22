package recipes.weapontable

import gameclass.RLEngineGameClass
import items.weapons.guns.CollapseRifleGunItem
import items.weapons.guns.LightRifleGunItem
import items.weapons.modifiers.WeaponModifiers
import items.weapons.parts.WeaponLightningItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class LightningUpgradeWeaponTableRecipe : UpgradeWeaponTableRecipe() {
    override val base = listOf(
        CollapseRifleGunItem.getItem(),
        LightRifleGunItem.getItem(),
    )

    override val modifierKey = WeaponModifiers.LIGHTNING.key
    override val lore = Component.text("Установлен громоотвод", TextColor.color(150,150,150))

    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(WeaponLightningItem.getItem())
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))
    override val crafterClass = listOf(RLEngineGameClass.ENGINEER)
}
