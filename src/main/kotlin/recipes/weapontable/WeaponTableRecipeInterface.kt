package recipes.weapontable

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface WeaponTableRecipeInterface {
    fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) : ItemStack

    fun canCraft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) : Boolean
}
