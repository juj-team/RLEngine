package items.misc

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import util.InventoryDeserialiser
import util.InventorySerialiser

object BackpackItem: AbstractRLItem {
    override val baseItem: Material = Material.CHAIN_COMMAND_BLOCK
    override val model: Int = 44417
    override val id: String = "backpack"
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(
            Component.text("Рюкзак", TextColor.color(250,250,250))
                .decoration(TextDecoration.ITALIC, false)
        )
        resultMeta.lore(listOf(
            Component.text("спасибо за поддержку сервера!", TextColor.color(250,250,250))
                .decoration(TextDecoration.ITALIC, false)
        ))
        resultMeta.setCustomModelData(model)

        resultPDC.set(
            NamespacedKey("jujmiscs", "backpackid"),
            PersistentDataType.LONG,
            System.currentTimeMillis()
        )

        result.setItemMeta(resultMeta)
        return result
    }
    @EventHandler
    fun onBackpackClose(event: InventoryCloseEvent){
        val backpackItem = event.player.inventory.itemInMainHand
        if(!compare(backpackItem)) return
        if(event.view.title() != Component.text("Рюкзак", TextColor.color(0,170,0))) return
        if(event.reason == InventoryCloseEvent.Reason.DISCONNECT) return
        val backpackID = backpackItem.itemMeta.persistentDataContainer.get(
            NamespacedKey("jujmiscs", "backpackid"),
            PersistentDataType.LONG
        ) ?: return

        InventorySerialiser.saveToFile(
            backpackID,
            event.inventory
        )
    }
    @EventHandler
    fun onBackpackOpen(event: PlayerInteractEvent){
        if(event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        if(!event.isBlockInHand) return

        val backpackItem = event.player.inventory.itemInMainHand
        if(!compare(backpackItem)) return

        val backpackID = backpackItem.itemMeta.persistentDataContainer.get(
            NamespacedKey("jujmiscs", "backpackid"),
            PersistentDataType.LONG
        ) ?: return

        val queuedInv = InventoryDeserialiser.loadFromFile(backpackID)
        event.player.openInventory(queuedInv)
    }
}