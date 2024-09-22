
import ailments.RLEngineAilments
import com.sk89q.worldguard.WorldGuard
import commands.*
import items.RLEngineItems
import listeners.RLEngineListeners
import net.coreprotect.CoreProtect
import net.coreprotect.CoreProtectAPI
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.command.CommandExecutor
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import quests.RLEngineQuests
import recipes.RLEngineRecipes
import util.InventoryDeserialiser
import util.InventorySerialiser
import util.RLEngineTaskManager
import java.io.File

class RadioLampEngine: JavaPlugin() {
    var coreProtectAPI: CoreProtectAPI? = null
    var worldGuardAPI: WorldGuard? = null

    private val commandExecutors = mapOf<String, CommandExecutor>(
        "get" to GetCommand(),
        "ailments" to AilmentsCommand(),
        "snowstorm" to SnowstormCommand(),
        "bug_report" to BugReportCommand(),
        "set_lives" to SetLivesCommand(),
        "radiolamp" to AboutCommandExecutor(),
        "quests" to QuestsCommandExecutor(),
        "sv_cheats" to StanleyEasterEggCommand(),
        "get_lives" to GetLivesCommand(),
        "lost_backpacks" to LostBackpacksCommand(),
    )
    private val backpackFolder = File(this.dataFolder.absolutePath + "/backpacks")
    private val questFolder = File(this.dataFolder.absolutePath + "/quests")
    override fun onEnable() {
        coreProtectAPI = getCoreProtect()
        worldGuardAPI = getWorldGuard()

        // creating directories
        if (!this.dataFolder.exists()) this.dataFolder.mkdir()
        if(!backpackFolder.exists()) backpackFolder.mkdir()
        if(!questFolder.exists()) questFolder.mkdir()

        // Command registration
        commandExecutors.forEach {
            val command = this.getCommand(it.key)
            if(command != null) command.setExecutor(it.value)
            else logger.warning("Command ${it.key} not found in plugin.yml file!")
        }

        instance = this
        RLEngineTaskManager
        RLEngineListeners
        InventoryDeserialiser
        InventorySerialiser
        RLEngineItems
        RLEngineAilments
        RLEngineRecipes
        RLEngineQuests
    }
    override fun onDisable(){
        // remove all weather locks
        Bukkit.getWorlds().forEach{
            it.persistentDataContainer.set(
                NamespacedKey("rle", "weathering"),
                PersistentDataType.BOOLEAN,
                false,
            )
        }
        // cancel all tasks
        Bukkit.getScheduler().cancelTasks(this)
    }
    companion object{
        lateinit var instance: Plugin
    }

    private fun getCoreProtect(): CoreProtectAPI? {
        val plugin: Plugin? = server.pluginManager.getPlugin("CoreProtect")

        if (plugin !is CoreProtect) {
            return null
        }

        val coreProtect = plugin.api

        return if (coreProtect.isEnabled) coreProtect else null
    }

    private fun getWorldGuard(): WorldGuard? {
        return WorldGuard.getInstance()
    }
}