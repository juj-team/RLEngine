package util

import RadioLampEngine
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.EOFException
import java.io.File
import java.io.IOException
import java.nio.file.Files

object InventoryDeserialiser {
    @Throws(IOException::class)
    fun loadFromFile(id: Long): Inventory {
        val backpackFile = File(RadioLampEngine.instance.dataFolder.absolutePath + "/backpacks/$id")
        val resultInv = Bukkit.createInventory(
            null,
            18,
            Component.text("Рюкзак", TextColor.color(0,170,0))
        )

        if(!backpackFile.exists()){
            backpackFile.createNewFile()
            return resultInv
        }

        var inputString: String? = null
        try{
            inputString = String(Files.readAllBytes(backpackFile.toPath()))
        } catch (e: EOFException){ /*do nothing*/ }

        if(inputString == null || inputString == "") return resultInv

        val buffer = decode(inputString)!!
        resultInv.contents = buffer.contents
        return resultInv
    }

    private fun decode(data: String?): Inventory? {
        return try {
            val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(data))
            val dataInput = BukkitObjectInputStream(inputStream)
            val hueta = dataInput.readInt()
            val inventory: Inventory = Bukkit.getServer().createInventory(null, 18)

            // Read the serialized inventory
            for (i in 0 until hueta) {
                val readObj = dataInput.readObject()
                if(readObj == null) inventory.setItem(i, ItemStack(Material.AIR))
                else inventory.setItem(i, readObj as ItemStack)
            }
            dataInput.close()
            inventory
        } catch (e: ClassNotFoundException) {
            throw IOException("Unable to decode class type.", e)
        }
    }
}