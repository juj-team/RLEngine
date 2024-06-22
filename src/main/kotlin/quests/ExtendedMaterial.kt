package quests

import items.RLEngineItems
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ExtendedMaterial {
        fun retrieve(id: String): ItemStack?{
            // handle for vanilla items
            if(Material.entries.firstOrNull { it.name == id } != null) return ItemStack(Material.entries.first { it.name == id })
            // handle for RL items
            if(RLEngineItems.getItems().firstOrNull { it == id } != null){
                val result = RLEngineItems.fetchItem(RLEngineItems.getItems().first { it == id }) ?: throw IllegalArgumentException("Item $id not found!")
                return result.getItem()
            }
            // fail gracefully
            return null
        }
}