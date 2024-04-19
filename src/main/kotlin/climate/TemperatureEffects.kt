package climate

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object TemperatureEffects {
    private var effects: Map<ClosedFloatingPointRange<Double>, List<PotionEffect>> =
        mapOf(
            Double.NEGATIVE_INFINITY..-50.0 to
                    listOf(
                        PotionEffect(PotionEffectType.HARM, 2 * 20, 0, true, false, false),
                    ),
            Double.NEGATIVE_INFINITY..-48.0 to
                    listOf(
                        PotionEffect(PotionEffectType.SLOW, 2 * 20, 1, true, false, false),
                    ),
            Double.NEGATIVE_INFINITY..-45.0 to
                    listOf(
                        PotionEffect(PotionEffectType.WEAKNESS, 2 * 20, 0, true, false, false),
                    ),
            45.0..Double.POSITIVE_INFINITY to
                    listOf(
                        PotionEffect(PotionEffectType.SLOW, 2 * 20, 1, true, false, false),
                    ),
            48.0..Double.POSITIVE_INFINITY to
                    listOf(
                        PotionEffect(PotionEffectType.CONFUSION, 9 * 20, 0, true, false, false),
                    ),
            50.0..Double.POSITIVE_INFINITY to
                    listOf(
                        PotionEffect(PotionEffectType.WITHER, 2 * 20, 0, true, false, false),
                    ),
        )

    fun getEffects(temperature: Double): List<PotionEffect> {
        val resultEffects = mutableListOf<PotionEffect>()
        for ((range, effects) in effects) {
            if (temperature in range) resultEffects += effects
        }

        return resultEffects
    }
}