package listeners.metaphysics

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType
import util.metaphysics.Lives

object PlayerJoinListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val pdc = player.persistentDataContainer
        if (!pdc.has(Lives.livesKey)) {
            pdc.set(
                Lives.livesLimitKey,
                PersistentDataType.INTEGER,
                Lives.MAX_LIVES
            )
            Lives.set(player, Lives.MAX_LIVES)
        }
        Lives.updateScoreboardView(player, Lives.get(player))
    }
}