package listeners.gameclass

import gameclass.RLEngineGameClass
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

object HeadHunterDrop : Listener {
    @EventHandler
    private fun onPlayerKilled(event: PlayerDeathEvent) {
        val killer = event.player.killer ?: return
        if (RLEngineGameClass.getClass(killer) != RLEngineGameClass.HEADHUNTER) return

        val playerHead = ItemStack(Material.PLAYER_HEAD)
        val playerHeadMeta = playerHead.itemMeta as SkullMeta
        playerHeadMeta.owningPlayer = event.player
        playerHead.itemMeta = playerHeadMeta

        event.drops.add(playerHead)
    }
}