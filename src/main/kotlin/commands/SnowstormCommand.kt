package commands

import climate.weather.Snowstorm
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class SnowstormCommand : CommandExecutor, TabCompleter{
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(sender !is Player){
            sender.sendMessage("Only players can use this command!")
            return true
        }
        Snowstorm.start(sender.world)
        return true
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): List<String> {
        return listOf()
    }

}