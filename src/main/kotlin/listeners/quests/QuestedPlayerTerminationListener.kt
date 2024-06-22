package listeners.quests

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerQuitEvent
import quests.RLEngineQuests

object QuestedPlayerTerminationListener: Listener {
    @EventHandler
    fun onQuestedExit(event: PlayerQuitEvent){
        if(RLEngineQuests.getQuestStatus(event.player)) RLEngineQuests.terminateQuest(event.player)
    }
    @EventHandler
    fun onQuestedDeath(event: PlayerDeathEvent){
        if(event.isCancelled) return
        if(RLEngineQuests.getQuestStatus(event.player)) RLEngineQuests.terminateQuest(event.player)
    }
}