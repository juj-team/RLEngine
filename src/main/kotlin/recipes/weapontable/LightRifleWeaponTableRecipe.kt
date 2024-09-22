package recipes.weapontable

import items.weapons.guns.LightRifleGunItem
import items.weapons.parts.RifledBarrelItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class LightRifleWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(ItemStack(Material.BOW))
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(RifledBarrelItem.getItem())
    override val result: ItemStack = LightRifleGunItem.getItem()
}
