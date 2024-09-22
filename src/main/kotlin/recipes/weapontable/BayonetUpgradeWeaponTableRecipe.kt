package recipes.weapontable

import items.weapons.guns.*
import items.weapons.modifiers.WeaponModifiers
import items.weapons.parts.BayonetItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class BayonetUpgradeWeaponTableRecipe : UpgradeWeaponTableRecipe() {
    override val base = listOf(
        HeavyRifleWeaponItem.getItem(),
        CollapseRifleGunItem.getItem(),
        LightRifleGunItem.getItem(),
        RevolverGunItem.getItem(),
        SawedOffGunItem.getItem(),
        ShotgunGunItem.getItem(),
    )

    override val modifierKey = WeaponModifiers.BAYONET.key
    override val lore = Component.text("Установлен штык", TextColor.color(150, 150, 150))

    override val metal = listOf(ItemStack(Material.IRON_INGOT, 6))
    override val modifier = listOf(BayonetItem.getItem())
    override val fuel = listOf(ItemStack(Material.BLAZE_ROD))

    override fun craft(
        base: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        fuel: ItemStack,
        crafter: Player
    ): ItemStack {
        val result = super.craft(base, metal, modifier, fuel, crafter)

        val resultMeta = result.itemMeta
        resultMeta.addEnchant(
            org.bukkit.enchantments.Enchantment.DAMAGE_ALL,
            2,
            true,
        )

        result.setItemMeta(resultMeta)

        return result
    }
}
