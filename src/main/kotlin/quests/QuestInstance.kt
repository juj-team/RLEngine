package quests

import com.bladecoder.ink.runtime.Story
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta
import org.bukkit.persistence.PersistentDataType

@Suppress("UnstableApiUsage")
class QuestInstance(val player: Player, val story: Story) {
    enum class AdvanceState{
        ADVANCE,
        FINISH
    }
    private val bundleItem = ItemStack(Material.BUNDLE)
    val bundleContents = bundleItem.itemMeta as BundleMeta
    init{
        advance(-1)
    }
    fun advance(choiceIndex: Int): AdvanceState{
        if(choiceIndex > -1){
            story.chooseChoiceIndex(choiceIndex)
        }
        if(story.canContinue()){
            player.sendMessage(MiniMessage.miniMessage().deserialize(story.Continue()))
            JulyInkInstructionParser.parse(story.currentTags, this)
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
    fun giveBundle(){
        bundleItem.setItemMeta(bundleContents)
        player.playSound(
            player.location,
            Sound.ENTITY_ITEM_PICKUP,
            1.0f,
            1.0f
        )
        if(player.inventory.addItem(bundleItem).size > 0){
            player.world.dropItemNaturally(player.location, bundleItem)
        }

        bundleContents.setItems(null)
    }
}