package quests

import com.bladecoder.ink.runtime.Choice
import com.bladecoder.ink.runtime.Story
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta
import org.bukkit.persistence.PersistentDataType

const val questAdvanceMessage = """
<gold><click:run_command:'/quests advance -1'>[Далее...]</click></gold>
"""

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

    private fun continueStory() {
        if (story.canContinue()) {
            val textContent = story.Continue()
            val definedCalls = JulyInkInstructionParser.parse(story.currentTags)
            JulyInstructionMediator.dispatch(definedCalls, this)
            player.sendMessage(MiniMessage.miniMessage().deserialize(textContent))
        }
    }

    private fun displayContinueButton() {
        player.sendMessage(MiniMessage.miniMessage().deserialize(questAdvanceMessage))
    }

    private fun isChoiceLine(): Boolean {
        return story.currentChoices.isNotEmpty()
    }

    private fun displayChoiceButton(index: Int, choice: Choice) {
        val buttonText = "<gold><click:run_command:'/quests advance $index'>[${choice.text}]</click></gold>"

        player.sendMessage(MiniMessage.miniMessage().deserialize(buttonText))
    }

    private fun displayChoiceButtons() {
        story.currentChoices.forEachIndexed(::displayChoiceButton)
    }

    fun advance(choiceIndex: Int): AdvanceState{
        if(choiceIndex > -1){
            story.chooseChoiceIndex(choiceIndex)
        }

        continueStory()

        if (!isChoiceLine()) {
            if(!story.canContinue()) return AdvanceState.FINISH

            displayContinueButton()
        }
        else {
            displayChoiceButtons()
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