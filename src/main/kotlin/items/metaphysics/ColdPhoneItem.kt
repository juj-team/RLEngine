package items.metaphysics

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import util.RLEngineTaskManager
import util.metaphysics.Lives

object ColdPhoneItem: AbstractRLItem {
    override val baseItem: Material = Material.TOTEM_OF_UNDYING
    override val model: Int = 44401
    override val id: String = "cold_line"
    init{
        this.createItem()
    }
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Холодная линия", TextColor.color(113, 245, 245)))
        resultMeta.setCustomModelData(model)
        result.setItemMeta(resultMeta)
        return result
    }
    @EventHandler
    fun onTaking(event: PlayerItemHeldEvent){
        if(event.isCancelled) return
        val player = event.player
        val newItem = player.inventory.getItem(event.newSlot) ?: return

        if(compare(newItem) && Lives.get(player) < Lives.getLimit(player)){
            Lives.applyDelta(player, 1)
            RLEngineTaskManager.runTaskLater({
                player.damage(9999999.0)
            }, 1L)
        }
    }
}