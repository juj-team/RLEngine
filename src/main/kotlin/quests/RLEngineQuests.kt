package quests

import com.bladecoder.ink.runtime.Story
import org.bukkit.entity.Player

object RLEngineQuests {
    private val activeQuests = mutableMapOf<Player, QuestInstance?>()
    init{
        QuestLoader
    }
    fun startQuest(player: Player, quest: Story){
        activeQuests[player] = QuestInstance(player, quest)
    }
    fun startQuest(player: Player, questName: String){
        val quest = QuestLoader.loadStory(questName) ?: throw IllegalArgumentException("Story $questName not found! (Loaded by ${player.name})")
        activeQuests[player] = QuestInstance(player, quest)
    }
    fun getQuestStatus(player: Player): Boolean{
        return activeQuests[player] != null
    }
    fun advanceQuest(player: Player, choice: Int){
        val advanceResult = activeQuests[player]?.advance(choice)
        if(advanceResult == QuestInstance.AdvanceState.FINISH){
            terminateQuest(player)
        }
    }
    fun terminateQuest(player: Player){
        activeQuests[player]?.finish()
        activeQuests[player] = null
    }
    fun getAvailableQuests(): List<String>{
        return QuestLoader.getAvailableQuests()
    }
}