package climate

import climate.calculation.BiomeWarmness
import climate.calculation.EquipmentWarmness
import climate.calculation.LightWarmness
import climate.calculation.WeatherWarmness
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.boss.BarColor
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import util.RLEngineTaskManager
import java.util.*

@Suppress("SameParameterValue")
object TemperatureCalculator {
    private val temperatureCalculators =
        listOf(
            EquipmentWarmness,
            BiomeWarmness,
            LightWarmness,
            WeatherWarmness,
        )
    private val temperatureSettings = TemperatureSettings
    private val temperatureKey = NamespacedKey("rle", "climate")

    init {
        RLEngineTaskManager.runTask({
            Bukkit.getOnlinePlayers().forEach { processPlayer(it) }
        }, 1L, 1L)
    }

    private fun processPlayer(player: Player) {
        // process temperatures
        val temperature = calculateTemperatureWithLerp(player)
        // apply potion effects
        val effects = TemperatureEffects.getEffects(temperature)
        player.addPotionEffects(effects)
        // update bossbar
        val bossbar = Bukkit.getBossBar(
            NamespacedKey("rle", player.uniqueId.toString().lowercase(Locale.getDefault()))
        )!!
        bossbar.setTitle(getBossbarTitle(temperature, temperatureSettings))
        bossbar.progress = normalizeValue(temperature, temperatureSettings)
        bossbar.color = BarColor.PINK
    }

    private fun calculateTemperatureWithLerp(player: Player): Double {
        val pdc = player.persistentDataContainer
        val realTemperature = temperatureCalculators.sumOf { it.calculate(player) }

        val currentTemperature = pdc.get(temperatureKey, PersistentDataType.DOUBLE)
        val nextTemperature =
            if (currentTemperature == null) {
                lerp(0.0, realTemperature, temperatureSettings.INERTIA)
            } else {
                lerp(currentTemperature, realTemperature, temperatureSettings.INERTIA)
            }

        pdc.set(temperatureKey, PersistentDataType.DOUBLE, nextTemperature)
        return nextTemperature
    }

    private fun lerp(
        a: Double,
        b: Double,
        inertia: Double,
    ): Double = (1 - inertia) * a + inertia * b

    private fun normalizeValue(
        temperature: Double,
        temperatureSettings: TemperatureSettings,
    ): Double {
        val normalisedTemp = (temperature - temperatureSettings.LOWER_BOUND) / (temperatureSettings.UPPER_BOUND - temperatureSettings.LOWER_BOUND)
        return normalisedTemp.coerceIn(0.0, 1.0)
    }

    private fun getBossbarTitle(
        temperature: Double,
        temperatureSettings: TemperatureSettings,
    ): String {
        if (temperature < temperatureSettings.LOWER_BOUND) return "Слишком холодно"
        if (temperature > temperatureSettings.UPPER_BOUND) return "Слишком жарко"

        return "${temperature.toInt()}°C"
    }
}