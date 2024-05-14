package quests

import com.bladecoder.ink.runtime.Story
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

class QuestInstance(val player: Player, val story: Story) {
    enum class AdvanceState{
        ADVANCE,
        FINISH
    }
    init{
        player.persistentDataContainer.set(
                NamespacedKey("rle", "locked"),
                PersistentDataType.BOOLEAN,
            true
        )
        clearChat(player)
        advance(-1)
    }
    fun advance(choiceIndex: Int): AdvanceState{
        if(choiceIndex > -1){
            story.chooseChoiceIndex(choiceIndex)
        }
        if(story.canContinue()){
            player.sendMessage(MiniMessage.miniMessage().deserialize(story.Continue()))
        }
        if(story.currentChoices.size < 1){
            if(!story.canContinue()) return AdvanceState.FINISH
            player.sendMessage(MiniMessage.miniMessage().deserialize("<gold><click:run_command:'/quests advance -1'>[Далее...]</click></gold>"))
        }
        else{
            story.currentChoices.forEachIndexed{ index, choice ->
                player.sendMessage(MiniMessage.miniMessage().deserialize("<gold><click:run_command:'/quests advance $index'>[${choice.text}]</click></gold>"))
            }
        }
        return AdvanceState.ADVANCE
    }

    fun finish(){
        player.persistentDataContainer.set(
            NamespacedKey("rle", "locked"),
            PersistentDataType.BOOLEAN,
            false
        )
    }

    private fun clearChat(player: Player){
        for(i in 1..100) player.sendMessage(" ")
    }

}