package gui

import RadioLampEngine
import de.themoep.inventorygui.GuiStorageElement
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class WeaponTableGui() {
    private val guiSetup: Array<String> = arrayOf(
        "rir",
        "igi",
        "rir",
    )
    private val backgroundElement = StaticGuiElement(
        'r',
        ItemStack(Material.RED_STAINED_GLASS_PANE)
    )
    private val storageElement = GuiStorageElement('i', Bukkit.createInventory(null, InventoryType.CHEST))
    private val buttonElement = StaticGuiElement('g', ItemStack(Material.LIME_STAINED_GLASS_PANE))
    val gui = InventoryGui(RadioLampEngine.instance, "Оружейный стол", guiSetup, backgroundElement, storageElement, buttonElement)
}