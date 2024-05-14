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
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class QuestInstance(val player: Player, val story: Story) {
    enum class AdvanceState{
        ADVANCE,
        FINISH
    }
    val bundleItem = ItemStack(Material.BUNDLE)
    val bundleContents = bundleItem.itemMeta as BundleMeta
    init{
        advance(-1)
        player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 9999999, 0, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 20 * 9999999, 0, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.HEAL, 20 * 9999999, 10, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20 * 9999999, 127, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 20 * 9999999, 127, false, false, false))
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
        player.removePotionEffect(PotionEffectType.INVISIBILITY)
        player.removePotionEffect(PotionEffectType.SATURATION)
        player.removePotionEffect(PotionEffectType.HEAL)
        player.removePotionEffect(PotionEffectType.SLOW)
        player.removePotionEffect(PotionEffectType.JUMP)
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