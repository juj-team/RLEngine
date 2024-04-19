package gameclass

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

enum class RLEngineGameClass(val id: String) {
    CITIZEN("citizen"),
    ENGINEER("engineer"),
    MAGE("mage"),
    HEADHUNTER("headhunter"),
    BUSINESSMAN("businessman"), ;

    companion object {
        fun getClass(player: Player): RLEngineGameClass? {
            val playerClass =
                player.persistentDataContainer.get(
                    NamespacedKey("jujclasses", "playerclass"),
                    PersistentDataType.STRING,
                )

            return when (playerClass) {
                "citizen" -> {
                    CITIZEN
                }

                "engineer" -> {
                    ENGINEER
                }

                "mage" -> {
                    MAGE
                }

                "headhunter" -> {
                    HEADHUNTER
                }

                "businessman" -> {
                    BUSINESSMAN
                }

                else -> null
            }
        }
    }
}