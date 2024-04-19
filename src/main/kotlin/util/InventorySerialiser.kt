package util

import RadioLampEngine
import org.apache.commons.io.FileUtils
import org.bukkit.inventory.Inventory
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayOutputStream
import java.io.File

object InventorySerialiser {
    @Throws(IllegalStateException::class)
    fun saveToFile(id: Long, inv: Inventory){
        val backpackFile = File(RadioLampEngine.instance.dataFolder.absolutePath + "/backpacks/$id")
        if(!backpackFile.exists()) backpackFile.createNewFile()
        val encodedInv = encode(inv)!!

        FileUtils.writeByteArrayToFile(backpackFile, encodedInv.toByteArray())
    }

    private fun encode(inventory: Inventory): String? {
        return try {
            val outputStream = ByteArrayOutputStream()
            val dataOutput = BukkitObjectOutputStream(outputStream)

            // Write the size of the inventory
            dataOutput.writeInt(inventory.size)

            // Save every element in the list
            for (i in 0 until inventory.size) {
                dataOutput.writeObject(inventory.getItem(i))
            }

            // Serialize that array
            dataOutput.close()
            Base64Coder.encodeLines(outputStream.toByteArray())
        } catch (e: Exception) {
            throw IllegalStateException("Unable to save item stacks.", e)
        }
    }
}