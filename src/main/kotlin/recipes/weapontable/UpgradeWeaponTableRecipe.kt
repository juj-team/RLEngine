package recipes.weapontable

import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class UpgradeWeaponTableRecipe : AbstractWeaponTableRecipe() {
    abstract val modifierKey: NamespacedKey
    abstract val lore: Component

    override fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player,
    ) : ItemStack {
        val result = base.clone()

        spendResources(base, metal, modifier, fuel, crafter)

        val resultMeta = result.itemMeta

        resultMeta.persistentDataContainer.set(modifierKey, PersistentDataType.BOOLEAN, true)
        val lore = if(resultMeta.hasLore()) resultMeta.lore() else mutableListOf<Component>()
        lore?.add(this.lore)
        resultMeta.lore(lore)

        result.setItemMeta(resultMeta)

        return result
    }
}
