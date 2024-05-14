package listeners.quests

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import quests.RLEngineQuests

object QuestedPlayerExitListener: Listener {
    @EventHandler
    fun onQuestedExit(event: PlayerQuitEvent){
        if(RLEngineQuests.getQuestStatus(event.player)) RLEngineQuests.terminateQuest(event.player)
    }
}