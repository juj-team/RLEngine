package listeners.datafixer

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object DataFixerListener : Listener {
    private val dataFixers: List<AbstractDataFixer>

    init {
        val dataFixersToAdd = mutableListOf<AbstractDataFixer>()

        dataFixersToAdd.add(NightGogglesDataFixer)
        dataFixersToAdd.add(PowerGlovesDataFixer)

        dataFixers = dataFixersToAdd.toList()
    }

    private fun proceedItem(item: ItemStack?) {
        if (item == null) return

        for (fixer in dataFixers) fixer.tryToFix(item)
    }

    private fun proceedInventory(inventory: Inventory) {
        for (item in inventory.contents) {
            proceedItem(item)
        }
    }

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.player.isOp) return

        proceedInventory(event.view.topInventory)
        proceedInventory(event.view.bottomInventory)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.whoClicked.isOp) return

        val item = if (event.currentItem != null) event.currentItem else event.cursor
        proceedItem(item)
    }

    @EventHandler
    fun onPickupEvent(event: InventoryPickupItemEvent) {
        proceedItem(event.item.itemStack)
    }

    @EventHandler
    fun onItemSelect(event: PlayerInteractEvent) {
        if (event.player.isOp) return

        proceedItem(event.player.inventory.itemInMainHand)
        proceedItem(event.player.inventory.itemInOffHand)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (event.player.isOp) return

        proceedInventory(event.player.inventory)
    }
}