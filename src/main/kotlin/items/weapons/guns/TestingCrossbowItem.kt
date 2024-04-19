package items.weapons.guns

import items.AbstractRLItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CrossbowMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import util.RLEngineTaskManager

object TestingCrossbowItem: AbstractRLItem {
    override val baseItem: Material = Material.CROSSBOW
    override val model: Int = 44401
    override val id: String = "ultra_crossbow"
    init{
        this.createItem()
    }
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)
        return result
    }

    @EventHandler
    fun onShoot(event: EntityShootBowEvent){
        val player = event.entity
        if(player !is Player) return
        val bow = event.bow ?: return
        if(!compare(bow)) return
        val bowMeta = bow.itemMeta as CrossbowMeta
        player.sendMessage("хаха пистолет делает брррр")
        RLEngineTaskManager.runTaskLater({
            bowMeta.setChargedProjectiles(listOf(ItemStack(Material.ARROW, 1)))
            bow.setItemMeta(bowMeta)
        }, 1L)
    }
}