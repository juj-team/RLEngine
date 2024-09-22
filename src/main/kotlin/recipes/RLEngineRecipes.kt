package recipes

import org.bukkit.Bukkit
import org.bukkit.inventory.Recipe

object RLEngineRecipes {
    init{
        KalykRecipe
        MetalToolBoxRecipe
        CopperGearRecipe
        CopperBucketRecipe
        CopperPlatesRecipe
        BundleRecipe
    }
    fun register(recipe: Recipe){
        Bukkit.addRecipe(recipe)
    }
}