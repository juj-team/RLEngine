package listeners.gameclass

import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.persistence.PersistentDataType

object EnchantingTableDisabler: Listener {
    @EventHandler
    fun onEnchant(event: EnchantItemEvent){
        val player = event.enchanter

        val isMage = player.persistentDataContainer.get(
            NamespacedKey("jujclasses", "playerclass"),
            PersistentDataType.STRING
        ) == "mage"

        if (isMage) return

        val newItem = CursedStubItem.getItem()
        newItem.addUnsafeEnchantments(event.enchantsToAdd)
        event.inventory.setItem(0, newItem)

        player.world.playSound(
            player.location,
            Sound.ENTITY_ITEM_BREAK,
            SoundCategory.PLAYERS,
            1.0f,
            1.0f,
        )

        event.isCancelled = true
    }
}