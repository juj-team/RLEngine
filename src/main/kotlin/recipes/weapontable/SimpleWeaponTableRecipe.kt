package recipes.weapontable

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class SimpleWeaponTableRecipe : AbstractWeaponTableRecipe() {
    abstract val result: ItemStack
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))

    override fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) : ItemStack {
        spendResources(base, metal, modifier, fuel, crafter)

        return result.clone()
    }
}
