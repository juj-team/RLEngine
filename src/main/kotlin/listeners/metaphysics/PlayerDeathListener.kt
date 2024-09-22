package listeners.metaphysics

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import util.metaphysics.Lives

object PlayerDeathListener: Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent){
        if(event.isCancelled) return
        val player = event.player
        if (player.gameMode == GameMode.SPECTATOR) return

        val fireDeaths = listOf(
            EntityDamageEvent.DamageCause.FIRE,
            EntityDamageEvent.DamageCause.LAVA,
            EntityDamageEvent.DamageCause.LIGHTNING,
        )

        Lives.applyDelta(player, -1)
        val playerLivesLimit = Lives.getLimit(player)
        // Check for special death clauses: VOID and FIRE
        val deathCause = player.lastDamageCause?.cause
        val diedInVoid = deathCause == EntityDamageEvent.DamageCause.VOID
        val diedInFire = deathCause in fireDeaths
        // check for over-/underflow
        val currentLives: Int

        if (Lives.get(player) > playerLivesLimit){
            Lives.set(player, playerLivesLimit)
            currentLives = playerLivesLimit
        }
        else if (Lives.get(player) < 1 || diedInVoid){
            Lives.set(player, 0)
            currentLives = 0
        }
        else {
            currentLives = Lives.get(player)
        }

        // set player to spectator
        player.gameMode = GameMode.SPECTATOR
        // start constructing the death message
        val deathMessage = Component.text().content("Похоже, вас немножечко убило.").appendNewline()
        // this branch is for huge fuck-ups
        if (currentLives == 0) {
            deathMessage.append(Component.text("Ваши вещи сгинули вместе с вами."))
        }
        // this is a normal death branch
        else {
            // prevent items from being dropped
            event.drops.clear()
            // If player has cursed items - process them
            player.inventory.forEachIndexed { _, item ->
                if (item != null) {
                    if (item.containsEnchantment(Enchantment.BINDING_CURSE)) {
                        player.world.dropItem(player.location, item).pickupDelay = 40
                        player.inventory.removeItemAnySlot(item)
                    }
                    if (item.containsEnchantment(Enchantment.VANISHING_CURSE)) {
                        player.inventory.removeItemAnySlot(item)
                    }
                }
            }
            event.keepInventory = true
            // spawn a corpse and finish the death message
            spawnCorpse(player, diedInFire)
            deathMessage.append(Component.text("Вы умерли по координатам ${player.location.blockX} ${player.location.blockY} ${player.location.blockZ}"))
        }
        deathMessage.decoration(TextDecoration.BOLD, true)
        player.sendMessage(deathMessage.build())
    }

    private fun spawnCorpse(
        player: Player,
        isCooked: Boolean,
    ) {
        val location = player.location

        // Spawn the armor stand
        val armorStand = player.world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand

        // Configure the armor stand
        armorStand.isInvisible = true
        armorStand.isInvulnerable = true
        armorStand.isCustomNameVisible = false

        // Give the armor stand a corpse in hand
        val itemInHand = ItemStack(Material.PAPER)
        val itemInHandMeta = itemInHand.itemMeta
        itemInHandMeta.setCustomModelData(44401)
        itemInHand.setItemMeta(itemInHandMeta)
        armorStand.equipment.setItemInMainHand(itemInHand)

        // Rename the armor stand
        armorStand.customName(Component.text("Труп ${player.name}"))

        // Associate the player with the armor stand
        armorStand.persistentDataContainer.set(
            NamespacedKey("rle", "corpse"),
            PersistentDataType.BOOLEAN,
            true,
        )
        armorStand.persistentDataContainer.set(
            NamespacedKey("rle", "corpse_owner"),
            PersistentDataType.STRING,
            player.uniqueId.toString(),
        )
        armorStand.persistentDataContainer.set(
            NamespacedKey("rle", "has_heart"),
            PersistentDataType.BOOLEAN,
            true,
        )
        armorStand.persistentDataContainer.set(
            NamespacedKey("rle", "corpse_cooked"),
            PersistentDataType.BOOLEAN,
            isCooked,
        )
    }
}