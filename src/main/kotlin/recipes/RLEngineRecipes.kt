package recipes

import org.bukkit.Bukkit
import org.bukkit.inventory.ShapedRecipe

object RLEngineRecipes {
    init{
        KalykRecipe
    }
    fun register(recipe: ShapedRecipe){
        Bukkit.addRecipe(recipe)
    }
}