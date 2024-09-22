package recipes

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import recipes.weapontable.*

object WeaponTableRecipes {
    private val recipes = mutableListOf<WeaponTableRecipeInterface>()

    init {
        // weapons
        register(CollapseRifleWeaponTableRecipe())
        register(HeavyRifleWeaponTableRecipe())
        register(LightRifleWeaponTableRecipe())
        register(MachineGunWeaponTableRecipe())
        register(RevolverWeaponTableRecipe())
        register(RocketGunWeaponTableRecipe())
        register(SawedOffShotgunWeaponTableRecipe())
        register(ShotgunWeaponTableRecipe())

        // upgrades
        register(BayonetUpgradeWeaponTableRecipe())
        register(BuldygaUpgradeWeaponTableRecipe())
        register(GrenadesUpgradeWeaponTableRecipe())
        register(HookUpgradeWeaponTableRecipe())
        register(IgniterUpgradeWeaponTableRecipe())
        register(LightningUpgradeWeaponTableRecipe())
        register(NetsUpgradeWeaponTableRecipe())
        register(ShotgunMagUpgradeWeaponTableRecipe())

        // ammo
        register(BoneBreakerAmmoWeaponTableRecipe())
        register(HeavyAmmoWeaponTableRecipe())
        register(PoisonedAmmoWeaponTableRecipe())
        register(WeakeningAmmoWeaponTableRecipe())
        register(WitheringAmmoWeaponTableRecipe())
    }

    fun getRecipeByIngredients(
        weapon: ItemStack,
        metal: ItemStack,
        modifier: ItemStack,
        crafter: Player,
        fuel: ItemStack
    ): ItemStack?
    {
        val targetRecipe = recipes.firstOrNull { it.canCraft(weapon, metal, modifier, fuel, crafter) } ?: return null
        println(targetRecipe)
        return targetRecipe.craft(weapon, metal, modifier, fuel, crafter)
    }

    fun register(recipe: WeaponTableRecipeInterface) {
        recipes.add(recipe)
    }
}
