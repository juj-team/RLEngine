package recipes.weapontable

import gameclass.RLEngineGameClass
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class InsufficientWeaponTableResources : Exception("Tried to craft weapon without sufficient amount of resources!")

abstract class AbstractWeaponTableRecipe : WeaponTableRecipeInterface {
    abstract val base: List<ItemStack>
    abstract val modifier: List<ItemStack>
    abstract val metal: List<ItemStack>
    abstract val fuel: List<ItemStack>

    open val crafterClass: List<RLEngineGameClass> = listOf()

    internal fun getRecipeComponentOrNull(item: ItemStack, component: WeaponComponent) : ItemStack? {
        val componentList = when (component) {
            WeaponComponent.BASE -> this.base
            WeaponComponent.METAL -> this.metal
            WeaponComponent.MODIFIER -> this.modifier
            WeaponComponent.FUEL -> this.fuel
        }

        return componentList.firstOrNull { compareItems(it, item) && it.amount <= item.amount }
    }

    private fun compareItems(item1: ItemStack, item2: ItemStack) : Boolean {
        if (item1.type != item2.type) return false

        if (!item1.itemMeta.hasCustomModelData() && !item2.itemMeta.hasCustomModelData()) return true

        if (item1.itemMeta.customModelData != item2.itemMeta.customModelData) return false

        return true
    }

    private fun isSufficientResources(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
    ) : Boolean {
        val components = listOf(
            getRecipeComponentOrNull(base, WeaponComponent.BASE),
            getRecipeComponentOrNull(metal, WeaponComponent.METAL),
            getRecipeComponentOrNull(modifier, WeaponComponent.MODIFIER),
            getRecipeComponentOrNull(fuel, WeaponComponent.FUEL),
        )

        return components.all { it != null }
    }

    override fun canCraft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) : Boolean {
        val rightClass = if (crafterClass.isNotEmpty()) RLEngineGameClass.getClass(crafter) in crafterClass else true
        return rightClass && isSufficientResources(base, metal, modifier, fuel)
    }

    fun spendResources(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) {
        if (!canCraft(base, metal, modifier, fuel, crafter)) throw InsufficientWeaponTableResources()

        base.amount -= getRecipeComponentOrNull(base, WeaponComponent.BASE)!!.amount
        metal.amount -= getRecipeComponentOrNull(metal, WeaponComponent.METAL)!!.amount
        modifier.amount -= getRecipeComponentOrNull(modifier, WeaponComponent.MODIFIER)!!.amount
        fuel.amount -= getRecipeComponentOrNull(fuel, WeaponComponent.FUEL)!!.amount
    }
}
