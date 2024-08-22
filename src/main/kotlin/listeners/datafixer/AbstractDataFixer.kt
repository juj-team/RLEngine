package listeners.datafixer

import org.bukkit.inventory.ItemStack

interface AbstractDataFixer {
    fun checkItem(item: ItemStack) : Boolean
    fun fixItem(item: ItemStack)

    fun tryToFix(item: ItemStack) {
        if (!checkItem(item)) return

        fixItem(item)
    }
}