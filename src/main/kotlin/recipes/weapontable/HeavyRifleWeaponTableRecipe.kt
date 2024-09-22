package recipes.weapontable

import items.weapons.guns.HeavyRifleWeaponItem
import items.weapons.parts.SmoothBarrelItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class HeavyRifleWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(ItemStack(Material.BOW))
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 18))
    override val modifier = listOf(SmoothBarrelItem.getItem())
    override val result: ItemStack = HeavyRifleWeaponItem.getItem()
}
