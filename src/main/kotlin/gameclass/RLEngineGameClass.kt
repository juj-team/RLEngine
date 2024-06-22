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
            return RLEngineGameClass.entries.firstOrNull { it.id == playerClass }
        }
        fun getClass(id: String) = RLEngineGameClass.entries.firstOrNull { it.id == id }
        fun setClass(player: Player, gameClass: RLEngineGameClass){
            player.persistentDataContainer.set(
                NamespacedKey("jujclasses", "playerclass"),
                PersistentDataType.STRING,
                gameClass.id
            )
        }
    }
}