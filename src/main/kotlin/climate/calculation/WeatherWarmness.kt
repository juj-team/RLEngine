package climate.calculation

import org.bukkit.entity.Player

object WeatherWarmness : TemperatureCalculator {
    override fun calculate(player: Player): Double {
        val world = player.world
        return if (world.isClearWeather) {
            5.0
        } else {
            -5.0
        }
    }
}
