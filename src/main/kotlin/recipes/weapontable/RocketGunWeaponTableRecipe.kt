package recipes.weapontable

import items.weapons.guns.HeavyRifleWeaponItem
import items.weapons.guns.RocketGunItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class RocketGunWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(HeavyRifleWeaponItem.getItem())
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 64))
    override val modifier = listOf(ItemStack(Material.NETHERITE_SCRAP))
    override val result: ItemStack = RocketGunItem.getItem()
}
