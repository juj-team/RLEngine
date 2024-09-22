package recipes.weapontable

import items.weapons.guns.CollapseRifleGunItem
import items.weapons.guns.LightRifleGunItem
import items.weapons.parts.ReturnSpringItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class CollapseRifleWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(LightRifleGunItem.getItem())
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 32))
    override val modifier = listOf(ReturnSpringItem.getItem())
    override val result: ItemStack = CollapseRifleGunItem.getItem()
}
