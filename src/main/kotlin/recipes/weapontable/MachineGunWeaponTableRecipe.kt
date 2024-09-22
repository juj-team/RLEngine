package recipes.weapontable

import items.weapons.guns.MachineGunItem
import items.weapons.guns.RevolverGunItem
import items.weapons.parts.ShotgunMagItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MachineGunWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(RevolverGunItem.getItem())
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 48))
    override val modifier: List<ItemStack> get() {
        val item = ShotgunMagItem.getItem()
        item.amount = 3

        return listOf(item)
    }
    override val result: ItemStack = MachineGunItem.getItem()
}
