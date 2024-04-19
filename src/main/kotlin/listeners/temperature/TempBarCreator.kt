package listeners.temperature

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

object TempBarCreator: Listener {
    @EventHandler
    fun createBarOnJoin(event: PlayerJoinEvent) {
        val namespacedKey = NamespacedKey("rle", event.player.uniqueId.toString().lowercase(Locale.getDefault()))

        // удалить старые боссбары
        val oldBossbar = Bukkit.getBossBar(namespacedKey)
        oldBossbar?.removeAll()
        Bukkit.removeBossBar(namespacedKey)

        val bossbar =
            Bukkit.createBossBar(
                namespacedKey,
                "рот болит и попе больно",
                BarColor.PINK,
                BarStyle.SEGMENTED_12,
            )
        bossbar.progress = 0.5

        bossbar.addPlayer(event.player)
    }
}