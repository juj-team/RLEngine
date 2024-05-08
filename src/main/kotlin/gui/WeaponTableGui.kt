package gui

import RadioLampEngine
import de.themoep.inventorygui.GuiElement
import de.themoep.inventorygui.GuiStorageElement
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Shulker
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import recipes.WeaponTableRecipes
import kotlin.random.Random

class WeaponTableGui(private val shulker: Shulker) {
    data class WeaponTableSlotContents(val weapon: ItemStack?, val metal: ItemStack?, val fuel: ItemStack?, val modifier: ItemStack?)
    private val guiSetup: Array<String> = arrayOf(
        "fWf",
        "IgR",
        "fMf",
    )
    private val fillerElement = StaticGuiElement(
        'f',
        ItemStack(Material.GRAY_STAINED_GLASS_PANE),
        "Краткая справка",
        "Сверху кладётся оружие,",
        "Справа - топливо",
        "слева обычно помещается металл,",
        "а снизу - дополнительный ингредиент"
    )
    private val weaponElement = GuiStorageElement('W', Bukkit.createInventory(null, InventoryType.CHEST))
    private val metalElement  = GuiStorageElement('I', Bukkit.createInventory(null, InventoryType.CHEST))
    private val rodElement    = GuiStorageElement('R', Bukkit.createInventory(null, InventoryType.CHEST))
    private val modfierElement = GuiStorageElement('M', Bukkit.createInventory(null, InventoryType.CHEST))
    private val buttonElement = StaticGuiElement(
        'g',
        ItemStack(Material.LIME_STAINED_GLASS_PANE),
        { click -> buttonClickHandler(click); return@StaticGuiElement true},
        "Создать предмет"
    )
    private fun getTableContents(player: HumanEntity): WeaponTableSlotContents{
        val craftingGUIElements = mapOf(
            "weapon" to   gui.elements.firstOrNull{it.slotChar == 'W'},
            "metal" to    gui.elements.firstOrNull{it.slotChar == 'I'},
            "rod" to      gui.elements.firstOrNull{it.slotChar == 'R'},
            "modifier" to gui.elements.firstOrNull{it.slotChar == 'M'},
        )
        val weaponSlotIndex = craftingGUIElements["weapon"]?.slots?.get(0) ?: throw IllegalStateException("Weapon slot not found!")
        val metalSlotIndex = craftingGUIElements["metal"]?.slots?.get(0) ?: throw IllegalStateException("Metals slot not found!")
        val rodsSlotIndex = craftingGUIElements["rod"]?.slots?.get(0) ?: throw IllegalStateException("Rods slot not found!")
        val modifierSlotIndex = craftingGUIElements["modifier"]?.slots?.get(0) ?: throw IllegalStateException("Modifier slot not found!")
        return WeaponTableSlotContents(
            weapon = craftingGUIElements["weapon"]?.getItem(player, weaponSlotIndex),
            metal = craftingGUIElements["metal"]?.getItem(player, metalSlotIndex),
            fuel = craftingGUIElements["rod"]?.getItem(player, rodsSlotIndex),
            modifier = craftingGUIElements["modifier"]?.getItem(player, modifierSlotIndex),
        )
    }
    private fun buttonClickHandler(click: GuiElement.Click) {
        // get all crafting elements
        val player = click.whoClicked
        val tableContents = getTableContents(player)
        // pass them to a function
        val resultItem = craftFromItems(tableContents)
        // if result is not null - replace the top element and play sound
        if(resultItem != null){
            player.world.dropItemNaturally(player.location, resultItem)
            player.world.playSound(
                player.location,
                Sound.BLOCK_ANVIL_DESTROY,
                SoundCategory.BLOCKS,
                1.0f,
                0.8f + (0.4f * Random.nextFloat())
            )
            dropRemainingItems(player)
            shulker.peek = 0.0f
            click.gui.close(player, true)
        }
    }
    private fun craftFromItems(tableContents: WeaponTableSlotContents): ItemStack? {
        return WeaponTableRecipes.getRecipeByIngredients(
        tableContents.weapon ?: return null,
        tableContents.metal ?: return null,
        tableContents.modifier ?: return null,
        tableContents.fuel ?: return null
        )
    }
    private fun dropRemainingItems(player: HumanEntity) {
        val thingsToDrop = getTableContents(player)
        player.world.dropItemNaturally(player.location, thingsToDrop.weapon ?: ItemStack(Material.AIR))
        player.world.dropItemNaturally(player.location, thingsToDrop.metal ?: ItemStack(Material.AIR))
        player.world.dropItemNaturally(player.location, thingsToDrop.modifier ?: ItemStack(Material.AIR))
        player.world.dropItemNaturally(player.location, thingsToDrop.fuel ?: ItemStack(Material.AIR))
    }

    val gui = InventoryGui(
        RadioLampEngine.instance,
        "Оружейный стол",
        guiSetup,
        weaponElement,
        metalElement,
        rodElement,
        modfierElement,
        buttonElement,
        fillerElement
    )

    init{
        shulker.peek = 1.0f
        gui.setCloseAction {
            shulker.peek = 0.0f
            dropRemainingItems(it.player)
            return@setCloseAction false
        }
    }
}