package recipes.weapontable

import items.weapons.guns.RevolverGunItem
import items.weapons.guns.ShotgunGunItem
import items.weapons.parts.DoubleBarrelItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ShotgunWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(RevolverGunItem.getItem())
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 8))
    override val modifier = listOf(DoubleBarrelItem.getItem())
    override val result: ItemStack = ShotgunGunItem.getItem()
}
