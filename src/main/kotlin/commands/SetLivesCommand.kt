package commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import util.metaphysics.Lives

class SetLivesCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(args.size != 2) return false
        val player = Bukkit.getPlayer(args[0])
        if(player == null){
            sender.sendMessage("This player is not online!")
            return true
        }
        val amount = args[1].toIntOrNull() ?: return false
        Lives.set(player, amount)
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