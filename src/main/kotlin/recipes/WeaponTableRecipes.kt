package recipes

import items.weapons.guns.LightRifleGunItem
import items.weapons.parts.RifledBarrelItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class WeaponTableRecipes(
    val base: (ItemStack) -> Boolean,
    val modifier: (ItemStack) -> Boolean,
    val metal: (ItemStack) -> Boolean,
    val fuel: (ItemStack) -> Boolean = {item -> item.type == Material.BLAZE_ROD},
    val result: (ItemStack, ItemStack, ItemStack, ItemStack) -> ItemStack
) {
    LIGHT_RIFLE(
        base = {item -> item.type == Material.BOW},
        modifier = {item -> RifledBarrelItem.compare(item) },
        metal = {item -> item.amount >= 6 && item.type == Material.IRON_INGOT },
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            LightRifleGunItem.getItem() }
    );
    companion object{
        fun getRecipe(weapon: ItemStack, metal: ItemStack, modifier: ItemStack, fuel: ItemStack): ItemStack? {
            val targetRecipe = WeaponTableRecipes.entries
                .filter{it.base(weapon)}
                .filter{it.metal(metal)}
                .filter{it.fuel(fuel)}
                .firstOrNull { it.modifier(modifier) } ?: return null
            return targetRecipe.result(weapon, modifier, metal, fuel)
        }
    }
}
