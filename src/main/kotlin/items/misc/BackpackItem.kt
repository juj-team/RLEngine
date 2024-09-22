package items.misc

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import util.BackpackInventoryHolder
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

    fun getBackpackId(item: ItemStack) : Long? {
        return item.itemMeta.persistentDataContainer.get(
            NamespacedKey("jujmiscs", "backpackid"),
            PersistentDataType.LONG
        )
    }

    @EventHandler
    fun onBackpackClose(event: InventoryCloseEvent) {
        if (event.inventory.holder !is BackpackInventoryHolder) return
        val holder = event.inventory.holder as BackpackInventoryHolder
        if (event.reason == InventoryCloseEvent.Reason.DISCONNECT) return

        InventorySerialiser.saveToFile(
            holder.id,
            event.inventory,
        )
    }

    @EventHandler
    fun onBackpackDrop(event: InventoryClickEvent) {
        val droppedItem = if (event.currentItem != null) event.currentItem else event.cursor
        if (droppedItem == null) return

        val actions = listOf(
            InventoryAction.DROP_ALL_SLOT,
            InventoryAction.DROP_ONE_SLOT,
            InventoryAction.DROP_ALL_CURSOR,
            InventoryAction.DROP_ONE_CURSOR,
        )

        if (event.action !in actions) return
        if (!compare(droppedItem)) return


        val inventoryHolder = event.view.topInventory.holder as? BackpackInventoryHolder ?: return
        val backpackID = getBackpackId(droppedItem) ?: return

        if (inventoryHolder.id != backpackID) return

        event.view.close()
    }

    @EventHandler
    fun onBackpackOpen(event: PlayerInteractEvent){
        if(event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        if(!event.isBlockInHand) return
        if(event.player.gameMode == GameMode.SPECTATOR) return

        val backpackItem = event.player.inventory.itemInMainHand
        if(!compare(backpackItem)) return

        val backpackID = getBackpackId(backpackItem) ?: return

        val queuedInv = InventoryDeserialiser.loadFromFile(backpackID)
        event.player.openInventory(queuedInv)

        event.isCancelled = true
    }
}