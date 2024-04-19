package climate.calculation

import org.bukkit.entity.Player

object EnvironmentWarmness : TemperatureCalculator {
    override fun calculate(player: Player): Double {
        return 0.0
    }
}
