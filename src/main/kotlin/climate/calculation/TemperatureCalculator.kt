package climate.calculation

import org.bukkit.entity.Player

interface TemperatureCalculator {
    fun calculate(player: Player): Double
}
