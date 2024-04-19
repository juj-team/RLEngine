package listeners.gameclass

import gameclass.RLEngineGameClass
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.title.Title
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.RLEngineTaskManager

object NewPlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerClass = RLEngineGameClass.getClass(player)

        if (playerClass == null) {
            RLEngineTaskManager.runTaskLater({
                callPlayerClassChoice(player)
            }, 40L)
        }
    }

    private fun callPlayerClassChoice(player: Player) {
        player.persistentDataContainer.set(
            NamespacedKey("rle", "locked"),
            PersistentDataType.BOOLEAN,
            true,
        )

        player.teleport(Location(player.world, -1384.5, 62.0, 28.5, 127.5F, -24.0F))
        player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 9999999, 0, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 20 * 9999999, 0, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.HEAL, 20 * 9999999, 10, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20 * 9999999, 127, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 20 * 9999999, 127, false, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 20 * 9999999, 127, false, false, false))

        player.showTitle(Title.title(Component.text("Вы просыпаетесь в кустах..."), Component.text("")))
        RLEngineTaskManager.runTaskLater({
            player.sendMessage(
                Component.text(
                    "Ты просыпаешься в колючих кустах." +
                            " Листья уже присыпали тебя и только голова торчит и смотрит в ночное небо."
                ).appendNewline()
                    .append(
                        Component.text(
                            "[Продолжить]",
                            TextColor.color(200, 100, 0),
                        ).clickEvent(ClickEvent.runCommand("/classdialog 1")),
                    ),
            )
        },40L)
    }
}