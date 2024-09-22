package recipes.weapontable

import items.weapons.guns.ShotgunGunItem.setMagCapacity
import items.weapons.guns.SawedOffGunItem
import items.weapons.guns.ShotgunGunItem
import items.weapons.modifiers.WeaponModifiers
import items.weapons.parts.ShotgunMagItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ShotgunMagUpgradeWeaponTableRecipe : UpgradeWeaponTableRecipe() {
    override val base = listOf(
        ShotgunGunItem.getItem(),
        SawedOffGunItem.getItem(),
    )

    override val modifierKey = WeaponModifiers.SHOTGUN_MAG.key
    override val lore = Component.text("Установлен магазин", TextColor.color(95,80,100))

    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(ShotgunMagItem.getItem())
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))

    override fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player
    ): ItemStack {
        val result = super.craft(base, metal, modifier, fuel, crafter)

        setMagCapacity(result, 3)

        return result
    }
}
