package commands

import items.RLEngineItems
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GetCommand: CommandExecutor, TabCompleter {
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<String>
    ): Boolean {
        if(sender !is Player) return true
        if(args.isEmpty()) return false

        val id = args[0]
        val item = RLEngineItems.fetchItem(id) ?: return true

        sender.inventory.addItem(item.getItem())
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        return RLEngineItems.getItems().filter{it.startsWith(args.last(), ignoreCase = true)}
    }

}