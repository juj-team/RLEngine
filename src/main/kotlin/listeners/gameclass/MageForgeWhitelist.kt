package listeners.gameclass

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.persistence.PersistentDataType

object MageForgeWhitelist: Listener {
    @EventHandler
    fun onPrepareItemEnchant(event: InventoryClickEvent) {
        val player = event.whoClicked
        val inventory = event.clickedInventory ?: return
        if(inventory !is AnvilInventory) return
        if(event.slot != 2) return

        val enchanting = inventory.secondItem?.type == Material.ENCHANTED_BOOK
        val bannedItem = inventory.firstItem?.type in listOf(Material.WOODEN_SWORD)
        val isMage = player.persistentDataContainer.get(
            NamespacedKey("jujclasses", "playerclass"),
            PersistentDataType.STRING
        ) == "mage"

        if((enchanting && !isMage) || bannedItem){
            event.isCancelled = true
            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN)
            player.sendActionBar(Component.text("Вы не умеете читать :(", TextColor.color(200,120, 0)))
        }
    }
}