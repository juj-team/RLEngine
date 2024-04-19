package recipes

import items.weapons.guns.LightRifleGunItem
import items.weapons.parts.RifledBarrelItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class WeaponTableRecipes(
    base: ItemStack,
    modifier: ItemStack,
    extra: ItemStack,
    result: ItemStack
) {
    LIGHT_RIFLE(
        base = ItemStack(Material.BOW),
        extra = RifledBarrelItem.getItem(),
        modifier = ItemStack(Material.IRON_INGOT, 6),
        result = LightRifleGunItem.getItem()
    )
}