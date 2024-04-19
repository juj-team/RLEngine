package climate.calculation

import org.bukkit.entity.Player

object HeightWarmness : TemperatureCalculator {
    override fun calculate(player: Player): Double {
        val height = player.location.blockY
        return when (height) {
            in (-100..40) -> {
                3.0
            }
            in (41..120) -> {
                0.0
            }
            else -> {
                -3.0
            }
        }
    }
}
