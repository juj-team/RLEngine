package recipes.weapontable

import items.weapons.guns.RevolverGunItem
import items.weapons.parts.RevolverDrumItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class RevolverWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(ItemStack(Material.CROSSBOW))
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(RevolverDrumItem.getItem())
    override val result: ItemStack = RevolverGunItem.getItem()
}
