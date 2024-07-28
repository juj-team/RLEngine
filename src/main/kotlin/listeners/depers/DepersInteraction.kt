package listeners.depers

import listeners.depers.DepersGrowth.resetDepersTimer
import listeners.depers.DepersGrowth.setDepersTimer
import listeners.depers.DepersUtils.isNotImmune
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object DepersInteraction : Listener {
    @EventHandler
    private fun onDepersBreak(event: BlockBreakEvent) {
        if (event.block.type != Material.WARPED_WART_BLOCK) return
        if (!isNotImmune(event.player)) return

        event.player.spawnParticle(
            Particle.SQUID_INK,
            event.player.location.x,
            event.player.location.y + 1.6,
            event.player.location.z,
            15,
        )

        val effects = listOf(
            PotionEffect(
                PotionEffectType.WITHER,
                20,
                2,
                false,
                false,
            ),
            PotionEffect(
                PotionEffectType.BLINDNESS,
                20,
                1,
                false,
                false,
            ),
        )
        event.player.addPotionEffects(effects)

        event.player.world.playSound(
            event.block.location,
            Sound.ENTITY_ZOMBIE_INFECT,
            SoundCategory.BLOCKS,
            0.7F,
            0.6F,
        )

        changeDepersDrops(event)
    }

    private fun changeDepersDrops(event: BlockBreakEvent) {
        val drop: ItemStack

        val tool = event.player.inventory.itemInMainHand
        if (tool.itemMeta.hasEnchant(Enchantment.SILK_TOUCH)) {
            drop = ItemStack(Material.WARPED_WART_BLOCK)
        } else {
            val materials = listOf(Material.WARPED_ROOTS, Material.TWISTING_VINES)
            val index = (0..1).random()
            val material = materials[index]
            drop = ItemStack(material)
        }

        event.block.type = Material.AIR
        event.isDropItems = false
        event.block.world.dropItem(
            event.block.location.toCenterLocation(),
            drop,
            )
    }

    @EventHandler
    private fun onHivePlace(event: BlockPlaceEvent) {
        if (event.block.type != Material.BEE_NEST) return
        if (!isNotImmune(event.player)) return

        resetDepersTimer(event.player)
    }

    @EventHandler
    private fun onPlayerRespawn(event: PlayerRespawnEvent) {
        setDepersTimer(event.player, -1)
    }
}