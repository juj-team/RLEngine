package listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent

object EnchantingTableDisabler: Listener {
    @EventHandler
    fun onEnchant(event: EnchantItemEvent){
        event.item.amount = 0
        event.isCancelled = true
    }
}