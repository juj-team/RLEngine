package recipes

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

@Suppress("UnstableApiUsage")
object MetalToolBoxRecipe {
    init {
        RLEngineRecipes.register(this.getRecipe())
    }

    private fun getRecipe(): ShapedRecipe {
        val recipeResult = ItemStack(Material.BUNDLE)
        val itemMeta = recipeResult.itemMeta

        itemMeta.displayName(
            Component.text("Железный ящик").decoration(TextDecoration.ITALIC, false)
        )
        itemMeta.lore(
            listOf(
                Component.text("Для инструментов").decoration(TextDecoration.ITALIC, false)
            )
        )

        recipeResult.setItemMeta(itemMeta)

        val recipe = ShapedRecipe(
            NamespacedKey("rle", "metal_toolbox"),
            recipeResult,
        )

        recipe.shape(
            "0C0",
            "n0n",
            "nnn",
        )

        recipe.setIngredient('n', Material.IRON_NUGGET)
        recipe.setIngredient('C', Material.COPPER_INGOT)

        return recipe
    }
}