package listeners.gameclass

import gameclass.RLEngineGameClass
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import quests.RLEngineQuests
import util.RLEngineTaskManager

object NewPlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerClass = RLEngineGameClass.getClass(player)

        if (playerClass == null) {
            RLEngineTaskManager.runTaskLater({
                RLEngineQuests.startQuest(player, "intro.json")
            }, 40L)
        }
    }
}