package quests

import RadioLampEngine
import gameclass.RLEngineGameClass
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType


object JulyInkInstructionParser {
    fun parse(rawTags: List<String>): List<JulyInkInstruction> {
        val rawInstructions = rawTags.map{it.split(" ")}

        val preparedCalls = mutableListOf<JulyInkInstruction>()
        for (rawInstruction: List<String> in rawInstructions) {
            val preparedCall = JulyInkInstruction(rawInstruction[0], rawInstruction.subList(1, rawInstruction.size))
            preparedCalls.add(preparedCall)
        }

        return preparedCalls
    }

}