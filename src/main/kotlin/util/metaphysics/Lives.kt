package util.metaphysics

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scoreboard.DisplaySlot

object Lives {
    const val MAX_LIVES = 20
    val livesKey = NamespacedKey("jujmetaphysics", "lives")
    val livesLimitKey = NamespacedKey("jujmetaphysics", "liveslimit")

    fun get(player: Player): Int {
        val pdc = player.persistentDataContainer
        if (!pdc.has(livesKey)) {
            pdc.set(
                livesKey,
                PersistentDataType.INTEGER,
                MAX_LIVES,
            )
        }
        val result = pdc.get(livesKey, PersistentDataType.INTEGER)!!
        return result
    }

    fun set(
        player: Player,
        amount: Int,
    ) {
        val playerLimit = getLimit(player)
        val livesToSet = if (amount <= playerLimit) amount else playerLimit

        player.persistentDataContainer.set(
            livesKey,
            PersistentDataType.INTEGER,
            livesToSet,
        )
        updateScoreboardView(player, amount)
    }

    fun updateScoreboardView(player: Player, amount: Int) {
        player.scoreboard.getObjective(DisplaySlot.PLAYER_LIST)?.getScore(player)?.score = amount
    }

    fun applyDelta(
        player: Player,
        delta: Int,
    ) {
        var amount = get(player)
        amount += delta
        set(player, amount)
    }

    fun getLimit(player: Player): Int {
        val pdc = player.persistentDataContainer
        if (!pdc.has(livesLimitKey)) {
            pdc.set(
                livesLimitKey,
                PersistentDataType.INTEGER,
                20,
            )
        }
        return pdc.get(
            livesLimitKey,
            PersistentDataType.INTEGER,
        )!!
    }
}