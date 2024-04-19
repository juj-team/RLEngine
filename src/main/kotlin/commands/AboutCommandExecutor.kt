package commands

import RadioLampEngine
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class AboutCommandExecutor: CommandExecutor, TabCompleter {
    val aboutMsg = "<gold><b>RADIO LAMP ENGINE</b> - ГЛАВНЫЙ ДВИЖОК ИЮЛЯ</gold>" +
            "<br><br><b><dark_green>Наш сайт</dark_green></b>: <u><click:open_url:'https://july.pp.ru/'>july.pp.ru</click></u>" +
            "<br><green><b>Авторы</b></green>: " +
            "<u><br><click:open_url:'https://somichev.dev/'>somichev.dev</click></u> - код" +
            "<br>mrknrt - концепт, дизайн" +
            "<br>nulla_quattuor - графика" +
            "<br><b><green>Особые благодарности</green></b>:" +
            "<br>dest0re - модуль климата и температур" +
            "<br>0ocrop - оксидайзер" +
            "<br>gafirudo_ - консультации, код" +
            "<br>mirayyy - тестировка" +
            "<br>metaquatura - создатель идеи Июля" +
            "<br>Тюль Лени, mekval, Mr. У - спонсирование" +
            "<br>Все подписчики <gold>Июль+</gold> и <dark_red>Июль++</dark_red>"

    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if(sender !is Player){
            sender.sendMessage("Only players can use this command!")
            return true
        }
        sender.sendMessage(MiniMessage.miniMessage().deserialize(aboutMsg))
        sender.sendMessage(Component.text("Версия: ${RadioLampEngine.instance.pluginMeta.version}", TextColor.color(50, 200, 50)))
        return true
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): List<String> {
        return listOf()
    }
}