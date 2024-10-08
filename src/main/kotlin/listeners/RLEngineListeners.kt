package listeners
import RadioLampEngine
import listeners.depers.DepersInteraction
import items.weapons.modifiers.ModifierListener
import listeners.datafixer.DataFixerListener
import listeners.gameclass.*
import listeners.metaphysics.BedDisabler
import listeners.metaphysics.CorpseInteractionListener
import listeners.metaphysics.PlayerDeathListener
import listeners.metaphysics.PlayerJoinListener
import listeners.quests.QuestedPlayerTerminationListener
import listeners.temperature.TempBarCreator
import listeners.temperature.TempBarDeleter
import listeners.weapons.BrickThrowListener
import listeners.weapons.SkeletonDropsRemover
import listeners.weapons.WeaponTableInteractor
import org.bukkit.Bukkit
import org.bukkit.event.Listener

object RLEngineListeners {
    private val knownListeners = listOf(
        PlayerLockEnforce,
        NewPlayerListener,
        MageForgeWhitelist,
        TempBarCreator,
        TempBarDeleter,
        PlayerDeathListener,
        CorpseInteractionListener,
        BedDisabler,
        PlayerJoinListener,
        FlyingBoatPlacementListener,
        EnchantingTableDisabler,
        PlayerNameHider,
        WeaponTableInteractor,
        SkeletonDropsRemover,
        ModifierListener,
        BrickThrowListener,
        QuestedPlayerTerminationListener,
        NoDebugDupe,
        DepersInteraction,
        HeadHunterDrop,
        DataFixerListener,
        NoCustomJukeboxPlacement,
        HatRightButtonWearListener,
    )
    init {
        registerKnownListeners()
    }
    private fun registerKnownListeners(){
        knownListeners.forEach { register(it) }
    }
    fun register(listener: Listener){
        Bukkit.getPluginManager().registerEvents(listener, RadioLampEngine.instance)
    }
}