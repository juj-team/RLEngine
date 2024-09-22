package recipes

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

object BundleRecipe {
    init {
        RLEngineRecipes.register(this.getRecipe())
    }

    private fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(
            NamespacedKey("rle", "bundle"),
            ItemStack(Material.BUNDLE),
        )

        recipe.shape(
            "S",
            "L",
        )

        recipe.setIngredient('S', Material.STRING)
        recipe.setIngredient('L', Material.LEATHER)

        return recipe
    }
}