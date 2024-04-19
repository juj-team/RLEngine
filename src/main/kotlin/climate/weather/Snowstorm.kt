package climate.weather

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Vector
import util.RLEngineTaskManager
import java.util.*

object Snowstorm {
    // 60 секунд задержка перед стартом
    // В самом начале погода меняется на шторм
    // 40, 50, 60 секунда включают сирену
    // после 50 секунд каждую секунду играет звук ветра
    // после 60 секунд каждые n секунд всех игроков на поверхности и мобов в некотором радиусе от них швыряет в стороны
    private const val SIREN_SOUND = "custom_sounds:siren"
    private const val WIND_SOUND = "custom_sounds:wind"
    private const val SURFACE_HEIGHT = 40
    private val random = Random()

    fun start(
        world: World,
    ) {
        val pdc = world.persistentDataContainer
        // if snowstorm has already started in that world - stop
        val lock =
            pdc.get(
                NamespacedKey("rle", "weathering"),
                PersistentDataType.BOOLEAN,
            )
        if (lock == true) return
        // tell operators than the snowstorm has started
        Bukkit.getOperators().forEach { if (it.isOnline) it.player?.sendMessage("[Погода] Начинается циклоснежная метель.") }
        Bukkit.getLogger().info("[Погода] Начинается циклоснежная метель.")
        // lock the world to prevent doubling
        pdc.set(
            NamespacedKey("rle", "weathering"),
            PersistentDataType.BOOLEAN,
            true,
        )
        // set weather and prepare for the worst
        world.setStorm(true)
        world.players.forEach {
                player: Player? ->
            player?.sendActionBar(
                Component.text("Тучи сгущаются...", TextColor.color(150, 150, 150)).decoration(TextDecoration.ITALIC, false),
            )
        }
        // siren task
        val sirenLimit = 3
        val sirenTask = Runnable{
            world.players.forEach { it.playSound(it, SIREN_SOUND, 8.0f, 1.0f) }
        }
        for(i in 1..sirenLimit) RLEngineTaskManager.runTaskLater(sirenTask, 20*30L + 20*10L*i)
        // wind task
        val windLimit = 110
        val windTask = {
                world.players.forEach {
                    if (it.location.blockY >= SURFACE_HEIGHT) it.playSound(it, WIND_SOUND, 8.0f, random.nextFloat(0.8f, 1.2f))
                }
        }
        for(i in 1..windLimit) RLEngineTaskManager.runTaskLater(windTask, 20*50L + 20*i)
        // fling task
        val flingLimit = 120
        val flingTask = {
            world.players.forEach {
                if (it.location.blockY >= SURFACE_HEIGHT && it !in Bukkit.getOperators()) {
                    it.velocity =
                        Vector(
                            random.nextFloat(-1.0f, 1.0f),
                            random.nextFloat(0.2f, 1.0f),
                            random.nextFloat(-1.0f, 1.0f),
                        )
                }
            }
        }
        for(i in 1..flingLimit) RLEngineTaskManager.runTaskLater(flingTask, 20*60L + 20*i)
        val endingTask = {
            Bukkit.getOperators().forEach { if (it.isOnline) it.player?.sendMessage("[Погода] Циклоснежная метель закончилась.") }
            // remove lock
            pdc.set(
                NamespacedKey("rle", "weathering"),
                PersistentDataType.BOOLEAN,
                false,
            )
            Bukkit.getLogger().info("[Погода] Циклоснежная метель закончилась.")
        }
        RLEngineTaskManager.runTaskLater(endingTask, 20*182L)
    }
}