package recipes

import items.ingredients.CopperGearItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe

object CopperGearRecipe {
    init {
        RLEngineRecipes.register(this.getRecipe())
    }

    private fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(
            NamespacedKey("rle", "copper_gear"),
            CopperGearItem.getItem(),
        )

        recipe.shape(
            "0c0",
            "c0c",
            "0c0",
        )

        recipe.setIngredient('c', Material.COPPER_INGOT)

        return recipe
    }
}