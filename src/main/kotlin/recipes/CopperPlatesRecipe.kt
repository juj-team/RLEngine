package recipes

import items.ingredients.CopperBucketItem
import items.ingredients.CopperPlatesItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe

object CopperPlatesRecipe {
    init {
        RLEngineRecipes.register(this.getRecipe())
    }

    private fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(
            NamespacedKey("rle", "copper_plates"),
            CopperPlatesItem.getItem(),
        )

        recipe.shape(
            "iii",
            "ii0",
            "bb0",
        )

        recipe.setIngredient('i', Material.IRON_INGOT)
        recipe.setIngredient('b', CopperBucketItem.getItem())

        return recipe
    }
}