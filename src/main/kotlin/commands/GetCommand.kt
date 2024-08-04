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
        args: Array<String>,
    ): Boolean {
        if(sender !is Player) return true
        if(args.isEmpty()) return false

        val username = args[0]
        val player = sender.server.getPlayer(username) ?: return true

        val itemNames = args.slice(1 until args.size)
        val items = itemNames.map { RLEngineItems.fetchItem(it) }

        for (item in items) {
            if (item == null) continue
            player.inventory.addItem(item.getItem())
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<String>,
    ): List<String> {
        if (args.size == 1) {
            return sender.server.onlinePlayers.map { it.name }.filter { it.startsWith(args.last()) }
        }

        return RLEngineItems.getItems().filter { it.startsWith(args.last(), ignoreCase = true) }
    }

}