package commands

import ailments.RLEngineAilments
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class AilmentsCommand: CommandExecutor, TabCompleter {
    private val actions = listOf("status", "toggle")
    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        return when(args.size){
            1 -> {
                RLEngineAilments.getAilments().filter{it.startsWith(args[0], ignoreCase = true)}
            }
            2 -> {
                actions
            }
            3 -> {
                Bukkit.getOnlinePlayers().filter{it.name.startsWith(args[2])}.map{it.name}
            }
            else -> listOf()
        }
    }

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<String>
    ): Boolean {
        if(args.size < 3) return false
        val ailment = RLEngineAilments.fetchAilment(args[0])
        if(ailment == null){
            sender.sendMessage(Component.text("Ailment not found!", TextColor.color(250, 0, 0)))
            return true
        }
        val action = args[1]
        if(action !in actions) return false
        val player = Bukkit.getPlayer(args[2])
        if(player == null){
            sender.sendMessage(Component.text("Player is not online!", TextColor.color(250, 0, 0)))
            return true
        }

        when(action){
            "status" -> {
                val status = ailment.getStatus(player)
                sender.sendMessage(status.toString())
                return true
            }
            "toggle" -> {
                val status = ailment.getStatus(player)
                ailment.setStatus(!status, player)
                sender.sendMessage((!status).toString())
                return true
            }
        }
        return false
    }
}