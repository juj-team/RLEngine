package commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import util.metaphysics.Lives

class GetLivesCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(args.size != 1) return false
        val player = Bukkit.getPlayer(args[0])
        if(player == null){
            sender.sendMessage("This player is not online!")
            return true
        }
        sender.sendMessage("Lives: ${Lives.get(player)}/${Lives.getLimit(player)}")
        return true
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): List<String> {
        return when(args.size){
            1 -> {
                Bukkit.getOnlinePlayers().map{it.name}.filter{it.startsWith(args[0], ignoreCase = false)}
            }
            else -> {
                listOf()
            }
        }
    }
}