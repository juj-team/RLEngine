package climate.calculation

import org.bukkit.entity.Player

object LightWarmness : TemperatureCalculator {
    override fun calculate(player: Player): Double {
        if (player.location.block.lightLevel in 1..10) return -4.0

        return 0.0
    }
}
