package climate.calculation

import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.entity.Player

@Suppress("UnstableApiUsage")
object BiomeWarmness : TemperatureCalculator {
    private val temperatures: Map<Biome, Double> =
        mapOf(
            // safe biomes
            Biome.MUSHROOM_FIELDS to 10.0,
            Biome.CHERRY_GROVE to 30.0,
            // jule biomes
            Biome.PLAINS to 15.0,
            Biome.RIVER to 15.0,
            // cursed biomes
            Biome.BADLANDS to 35.0,
            Biome.ERODED_BADLANDS to 42.0,
            Biome.COLD_OCEAN to -65.0,
            Biome.DARK_FOREST to 20.0,
            Biome.DEEP_OCEAN to -20.0,
            Biome.DESERT to 40.0,
            Biome.FROZEN_RIVER to -65.0,
            Biome.JUNGLE to 37.0,
            Biome.OCEAN to 10.0,
            Biome.SNOWY_BEACH to  -63.0,
            Biome.SNOWY_PLAINS to -63.0,
            Biome.SNOWY_SLOPES to -63.0,
            Biome.SNOWY_TAIGA to  -63.0,
            Biome.SWAMP to 15.0,
            Biome.TAIGA to -58.0,
            )

    override fun calculate(player: Player): Double {
        if (player.world.environment == World.Environment.NETHER) return 44.0
        if (player.world.environment == World.Environment.THE_END) return -99.0

        return temperatures[player.location.block.biome] ?: 15.0
    }
}
