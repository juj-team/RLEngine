package recipes

import items.depers.PlitkaTotemItem
import items.misc.KalykItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe

object KalykRecipe {
    init{
        RLEngineRecipes.register(this.getRecipe())
    }
    private fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(
            NamespacedKey("rle", "kalyk"),
            KalykItem.getItem()
        )
        recipe.shape(
            "0B0",
            "wRw",
            "pPp"
        )
        recipe.setIngredient('P', PlitkaTotemItem.getItem())
        recipe.setIngredient('R', Material.BLAZE_ROD)
        recipe.setIngredient('B', Material.BAMBOO)
        recipe.setIngredient('p', Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
        recipe.setIngredient('w', Material.WATER_BUCKET)
        return recipe
    }
}