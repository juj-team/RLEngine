package commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import quests.QuestLoader
import quests.RLEngineQuests
import util.RLEngineTaskManager

class StanleyEasterEggCommand: CommandExecutor, TabCompleter {
    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): List<String> {
        return when(args.size){
            1 -> listOf("0", "1")
            else -> listOf()
        }
    }
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(sender !is Player){
            sender.sendMessage("Only players can use this command!")
            return true
        }
        if(sender.persistentDataContainer.has(NamespacedKey("rle", "stanley_easter_egg"), PersistentDataType.BOOLEAN)){
            sender.sendMessage(
                Component.text("Серверные читы отключены в этой сессии :(", TextColor.color(150,0,0))
            )
            return true
        }
        if(args.isNotEmpty() && args[0] == "1"){
            val stanleyQuest = QuestLoader.loadStory("stanley.json") ?: return true
            sender.persistentDataContainer.set(NamespacedKey("rle", "stanley_easter_egg"), PersistentDataType.BOOLEAN, true)
            RLEngineTaskManager.runTaskLater({
                RLEngineQuests.startQuest(sender, stanleyQuest)
            }, 10L)
        }
        return true
    }
}