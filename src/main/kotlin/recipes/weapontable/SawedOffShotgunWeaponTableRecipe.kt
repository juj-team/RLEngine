package recipes.weapontable

import items.weapons.guns.SawedOffGunItem
import items.weapons.guns.ShotgunGunItem
import items.weapons.parts.FileInstrumentItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SawedOffShotgunWeaponTableRecipe : SimpleWeaponTableRecipe() {
    override val base = listOf(ShotgunGunItem.getItem())
    override val metal = listOf(ItemStack(Material.IRON_INGOT, 16))
    override val modifier = listOf(FileInstrumentItem.getItem())
    override val result: ItemStack = SawedOffGunItem.getItem()
}
