package recipes.weapontable

import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class AmmoWeaponTableRecipe : AbstractWeaponTableRecipe() {
    abstract val modifierKey: NamespacedKey
    abstract val name: Component

    override fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) : ItemStack {
        val result = base.clone()

        val baseComponent = getRecipeComponentOrNull(base, WeaponComponent.BASE)!!

        spendResources(base, metal, modifier, fuel, crafter)

        val resultMeta = result.itemMeta

        resultMeta.persistentDataContainer.set(modifierKey, PersistentDataType.BOOLEAN, true)
        resultMeta.displayName(name)

        result.setItemMeta(resultMeta)

        result.amount = baseComponent.amount

        return result
    }
}
