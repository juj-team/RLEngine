package listeners.metaphysics

import items.hearts.CookedHeart
import items.hearts.Heart
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.*
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import util.metaphysics.Lives
import java.util.*

object CorpseInteractionListener  : Listener {
    private val hollowCorpseMessage =
        Component.text(
            "Кажется, этот труп давно покинут. Вернуть его к жизни не получится...",
            TextColor.color(150, 150, 150),
        )
    private val playerNotOnlineMessage =
        Component.text(
            "Вы звоните, но на той стороне вам никто не ответил.",
            TextColor.color(200, 200, 0),
        )
    @EventHandler
    fun onPlayerCorpseInteraction(event: PlayerInteractAtEntityEvent) {
        if(event.player.gameMode == GameMode.SPECTATOR) return
        val player = event.player
        val corpse = event.rightClicked
        val corpsePDC = corpse.persistentDataContainer
        // Check if we're dealing with a corpse
        val isCorpse = corpsePDC.has(NamespacedKey("rle","corpse"))
        if (!isCorpse) return
        // Cancel the event to prevent visual glitches
        event.isCancelled = true
        // Check if any player is associated with this corpse. If not - notify the player and log it
        val associatedPlayerUUID =
            corpsePDC.get(NamespacedKey("rle", "corpse_owner"), PersistentDataType.STRING)
        if (associatedPlayerUUID == null) {
            player.sendMessage(hollowCorpseMessage)
            Bukkit.getLogger()
                .warning("Hollow corpse (corpse with a wrong player association) detected! Please report to Somi.")
            Bukkit.getLogger()
                .warning("Position: ${player.location.blockX} ${player.location.blockY} ${player.location.blockZ}")
        }
        // Check if player wants to resurrect this corpse.
        // If they do - check if corpse player is online and resurrect them.
        // Return from function after resurrection
        val isResurrecting = player.inventory.getItem(event.hand).type == Material.TOTEM_OF_UNDYING


        if (isResurrecting && associatedPlayerUUID != null) {
            val associatedPlayer = getPlayerByUUIDString(associatedPlayerUUID)

            if (associatedPlayer == null) {
                player.sendMessage(playerNotOnlineMessage)
                return
            }

            if (Lives.get(associatedPlayer) > 0) {
                resurrectPlayer(associatedPlayer, corpse, player.inventory.getItem(event.hand))
            }
            else {
                player.sendMessage("С того конца лишь вечные гудки.")
            }

            return
        }
        // Drop a heart or cooked heart if corpse has one.
        // Player is not resurrected in this path.
        if (corpseHasHeart(corpse)) {
            if (isCorpseCooked(corpse)) {
                corpse.location.world.dropItemNaturally(
                    corpse.location,
                    CookedHeart.getItem()
                )
            } else {
                corpse.location.world.dropItemNaturally(
                    corpse.location,
                    Heart.getItem()
                )
            }
            // Remove heart from corpse
            corpsePDC.set(
                NamespacedKey("rle", "has_heart"),
                PersistentDataType.BOOLEAN,
                false,
            )
            return
        }
        openGraveInventory(event, associatedPlayerUUID)
    }

    private fun openGraveInventory(event: PlayerInteractAtEntityEvent, associatedPlayerUUID: String?) {
        if (!event.player.hasPermission("rl_engine.open_corpses")) return
        if (associatedPlayerUUID == null) return

        val associatedPlayer = getPlayerByUUIDString(associatedPlayerUUID) ?: return

        val view = event.player.openInventory(associatedPlayer.inventory)
        view?.title = "Труп ${associatedPlayer.name}"
    }

    private fun resurrectPlayer(
        resurrected: Player,
        corpse: Entity,
        totem: ItemStack,
    ) {
        // Teleport player to corpse and set game mode to survival
        resurrected.teleport(corpse)
        resurrected.gameMode = GameMode.SURVIVAL
        resurrected.allowFlight = false
        resurrected.isFlying = false
        // Play totem of undying sounds and remove totem
        playTotemOfUndyingEffects(resurrected.location)
        totem.amount -= 1
        // Drop a heart or cooked heart if corpse has one
        if (corpseHasHeart(corpse)) {
            if (isCorpseCooked(corpse)) {
                corpse.location.world.dropItemNaturally(
                    corpse.location,
                    CookedHeart.getItem()
                )
            } else {
                corpse.location.world.dropItemNaturally(
                    corpse.location,
                    Heart.getItem()
                )
            }
        }
        // Destroy the corpse
        corpse.remove()
    }

    private fun playTotemOfUndyingEffects(location: Location) {
        val world = location.world

        // Play Totem of Undying sound at the location
        world.playSound(location, Sound.ITEM_TOTEM_USE, 1.0f, 1.0f)

        // Spawn Totem of Undying particles at the location
        world.spawnParticle(Particle.TOTEM, location, 30, 0.5, 0.5, 0.5)
    }

    private fun isCorpseCooked(corpse: Entity): Boolean {
        val result =
            corpse.persistentDataContainer.get(
                NamespacedKey("rle", "corpse_cooked"),
                PersistentDataType.BOOLEAN,
            )
        return result ?: true
    }

    private fun corpseHasHeart(corpse: Entity): Boolean {
        val result =
            corpse.persistentDataContainer.get(
                NamespacedKey("rle", "has_heart"),
                PersistentDataType.BOOLEAN,
            )
        return result ?: false
    }

    private fun getPlayerByUUIDString(uuidString: String): Player? {
        val uuid: UUID =
            try {
                UUID.fromString(uuidString)
            } catch (e: IllegalArgumentException) {
                return null // Invalid UUID string provided
            }

        // Check if player with provided UUID is online
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.uniqueId == uuid) {
                return player
            }
        }

        return null // Player with provided UUID is not online
    }

}
