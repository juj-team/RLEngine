package commands

import RadioLampEngine
import items.misc.BackpackItem
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import util.InventoryDeserialiser
import java.io.File

class LostBackpacksCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): Boolean {
        if (sender !is Player){
            sender.sendMessage("Only players can use this command!")
            return true
        }

        if (!sender.isOp) {
            sender.sendMessage("Команда для опов брат")
            return true
        }

        val backpackDir = File(RadioLampEngine.instance.dataFolder.absolutePath + "/backpacks/")

        for (file in backpackDir.walk()) {
            val backpackId: Long
            try {
                backpackId = file.name.toLong()
            } catch (e: NumberFormatException) {
                continue
            }

            println(backpackId)

            val inventory: Inventory
            try {
                inventory = InventoryDeserialiser.loadFromFile(backpackId)
            } catch (e: IllegalArgumentException) {
                continue
            }
            val backpacks = inventory.contents.filter { it != null && BackpackItem.compare(it) }
            val sameBackpacks = backpacks.filter { BackpackItem.getBackpackId(it!!) == backpackId }

            if (sameBackpacks.isNotEmpty()) {
                val giveBackpackCommand = """<click:run_command:'/give @s minecraft:chain_command_block{CustomModelData: 44417, PublicBukkitValues: {"jujmiscs:backpackid": ${backpackId}L}}'>$backpackId</click>"""
                sender.sendMessage(MiniMessage.miniMessage().deserialize(giveBackpackCommand))
            }

        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<String>): List<String> {
        return listOf()
    }
}