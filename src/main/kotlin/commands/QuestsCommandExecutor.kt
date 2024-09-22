package commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import quests.RLEngineQuests

class QuestsCommandExecutor: CommandExecutor, TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        if(!sender.isOp) return listOf()
        return when(args.size){
            1 -> listOf("start","terminate","advance")
            2 -> {
                when(args[1]){
                    "start" -> RLEngineQuests.getAvailableQuests()
                    "terminate" -> Bukkit.getOnlinePlayers().map{it.name}
                    else -> listOf()
                }
            }
            3 -> Bukkit.getOnlinePlayers().map{it.name}
            else -> listOf()
        }
}
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(sender !is Player){
            sender.sendMessage("Only players can use this command!")
            return true
        }
        if(args.isEmpty()) return false
        val operation = args[0]
        when(operation){
            "start" -> {
                if(!sender.isOp) return true
                val subject: Player = if (args.size == 3) {
                    Bukkit.getPlayer(args[2]) ?: return true
                } else {
                    sender
                }

                if(RLEngineQuests.getQuestStatus(subject)){
                    sender.sendMessage("Player is already in quest!")
                    return true
                }
                if(args.size < 2){
                    sender.sendMessage("No quest id has been provided!")
                    return true
                }
                val questId = args[1]
                RLEngineQuests.startQuest(subject, questId)
            }
            "terminate" -> {
                if(!sender.isOp) return true
                if(!RLEngineQuests.getQuestStatus(sender)){
                    sender.sendMessage("Player is not in quest!")
                    return true
                }
                if(args.size < 2){
                    sender.sendMessage("No player name has been provided!")
                    return true
                }
                val player = Bukkit.getPlayer(args[1]) ?: return true
                RLEngineQuests.terminateQuest(player)

            }
            "advance" -> {
                if(!RLEngineQuests.getQuestStatus(sender)){
                    sender.sendMessage("Player is not in quest!")
                    return true
                }
                if(args.size < 2){
                    sender.sendMessage("No choice has been provided!")
                    return true
                }
                val choice = args[1].toIntOrNull() ?: return true
                RLEngineQuests.advanceQuest(sender, choice)
            }
        }
        return true
    }
}