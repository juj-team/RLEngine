package commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class BugReportCommand: CommandExecutor, TabCompleter {
    private val message: TextComponent =
        Component.text("Форма для сообщения о багах доступна ", TextColor.color(50,250,50))
            .append(
                Component.text("здесь", TextColor.color(50,50,200))
                    .clickEvent(
                        ClickEvent.openUrl("https://forms.gle/8rNo9Lv2g98jof5A7")
                    )
            )
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(sender !is Player){
            sender.sendMessage("Only players can use this command!")
            return true
        }
        sender.sendMessage(message)
        return true
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): List<String> {
        return listOf()
    }
}