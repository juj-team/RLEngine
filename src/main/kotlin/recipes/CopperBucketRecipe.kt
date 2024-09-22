package recipes

import items.ingredients.CopperBucketItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapelessRecipe

object CopperBucketRecipe {
    init {
        RLEngineRecipes.register(this.getRecipe())
    }

    private fun getRecipe(): ShapelessRecipe {
        val recipe = ShapelessRecipe(
            NamespacedKey("rle", "copper_bucket"),
            CopperBucketItem.getItem(),
        )

        recipe
            .addIngredient(Material.COPPER_INGOT)
            .addIngredient(Material.COPPER_INGOT)
            .addIngredient(Material.COPPER_INGOT)
            .addIngredient(Material.LAVA_BUCKET)

        return recipe
    }
}