package quests

import RadioLampEngine
import gameclass.RLEngineGameClass
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType

/*
- блок/анблок игрока # lock
	- true
	- false
- выдать предмет # give
- операции с сатчелом # satchel
	- add
	- give
- телепорт игрока # tp
	- x:y:z
	- x:y:z:yaw:pitch
- поменять класс игрока # setclass
	- citizen
	- mage
	- engineer
	- businessman
	- headhunter
- очистить чат # clear

- формат инструкций \[инструкция]:<аргументы>
- разделитель ;
 */
@Suppress("UnstableApiUsage")
object JulyInkInstructionParser {
    private val executors = mapOf(
        "lock" to ::lockCommand,
        "give" to ::giveCommand,
        "tp" to ::teleportCommand,
        "setclass" to ::setClassCommand,
        "clear" to ::clearCommand,
        "satchel" to ::satchelCommand,
        "advancement" to ::advancementCommand
    )

    data class JulyInkInstruction(val i: String, val args: List<String>)
    private val logger = RadioLampEngine.instance.logger
    fun parse(rawTags: List<String>, questInstance: QuestInstance){
        val rawInstructions = rawTags.map{it.split(" ")}

        val preparedCalls = mutableListOf<JulyInkInstruction>()
        for(i: List<String> in rawInstructions){
            if(executors[i[0]] == null){
                logger.warning("Illegal instruction ${i[0]}! (Caused by player ${questInstance.player.name}, line ${questInstance.story.currentText}))")
                continue
            }
            preparedCalls.add(JulyInkInstruction(i[0], i.subList(1,i.size)))
        }
        preparedCalls.forEach {
            val executor = executors[it.i]
            if(executor == null){
                logger.warning("Illegal instruction ${it.i}! (Caused by player ${questInstance.player.name}, line ${questInstance.story.currentText}))")
            }
            else{
                executor(it.args, questInstance)
            }
        }
    }
    private fun advancementCommand(args: List<String>, questInstance: QuestInstance){
        if(args.size < 2){
            logger.warning("Illegal instruction state: advancement! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
            return
        }
        val adv = questInstance.player.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey(args[0], args[1])) ?: return)
        for(criteria in adv.remainingCriteria){
            adv.awardCriteria(criteria)
        }
    }
    private fun lockCommand(args: List<String>, questInstance: QuestInstance){
        if(args.isEmpty()){
            logger.warning("Illegal instruction state: lock! (Caused by ${questInstance.player.name})")
            return
        }
        val newState = args[0].toBooleanStrictOrNull()
        if(newState == null){
            logger.warning("Illegal instruction state: lock:${args[0]}! (Caused by ${questInstance.player.name})")
            return
        }
        questInstance.player.persistentDataContainer.set(
            NamespacedKey("rle", "locked"),
            PersistentDataType.BOOLEAN,
            newState
        )
    }
    private fun giveCommand(args: List<String>, questInstance: QuestInstance){
        if(args.isEmpty()){
            logger.warning("Illegal instruction state: give! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
            return
        }
        val material = args[0]
        val amount = if(args.size > 1) args[1].toIntOrNull() else 1
        if(amount == null) {
            logger.warning("Illegal instruction state: give:${args[0]}:${args[1]}! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
            return
        }
        val item = ExtendedMaterial.retrieve(material)
        if (item == null){
            logger.warning("Illegal instruction state: give:${args[0]}:${amount}! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
            return
        }
        item.amount = amount
        val player = questInstance.player
        player.world.dropItemNaturally(player.location, item)
    }
    private fun teleportCommand(args: List<String>, questInstance: QuestInstance){
        if(args.isEmpty()){
            logger.warning("Illegal instruction state: tp! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
            return
        }
        if(args.size !in 3..5){
            logger.warning("Illegal instruction state: tp! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText})")
            return
        }
        val player = questInstance.player
        if(args.size == 3 || args.size == 4){
            val x = args[0].toDoubleOrNull()
            val y = args[1].toDoubleOrNull()
            val z = args[2].toDoubleOrNull()
            if(x == null || y == null || z == null){
                logger.warning("Illegal instruction state: tp! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText})")
                return
            }
            val newLocation = Location(player.world, x,y,z)
            player.teleport(newLocation)
        }
        if(args.size == 5){
            val x = args[0].toDoubleOrNull()
            val y = args[1].toDoubleOrNull()
            val z = args[2].toDoubleOrNull()
            val yaw = args[3].toFloatOrNull()
            val pitch = args[4].toFloatOrNull()
            if(null in listOf(x,y,z,pitch,yaw)){
                logger.warning("Illegal instruction state: tp! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText})")
                return
            }
            val newLocation = Location(player.world,x!!,y!!,z!!,yaw!!,pitch!!)
            player.teleport(newLocation)
        }
    }
    private fun setClassCommand(args: List<String>, questInstance: QuestInstance){
        if(args.isEmpty()){
            logger.warning("Illegal instruction state: setclass! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText}))")
            return
        }
        val newClass = RLEngineGameClass.getClass(args[0])
        if(newClass == null){
            logger.warning("Illegal instruction state: setclass! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText}))")
            return
        }
        RLEngineGameClass.setClass(questInstance.player, newClass)
    }
    private fun clearCommand(args: List<String>, questInstance: QuestInstance){
        if(args.isNotEmpty()) logger.warning("Dangerous instruction state: clear! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText}))")
        for(i in 1..100) questInstance.player.sendMessage(" ")
    }

    private fun satchelCommand(args: List<String>, questInstance: QuestInstance){
        if(args.isEmpty()){
            logger.warning("Illegal instruction state: satchel! (Caused by ${questInstance.player.name}, line ${questInstance.story.currentText}))")
            return
        }
        val operation = args[0]
        when(operation){
            "add" -> {
                if(args.size < 2){
                    logger.warning("Illegal instruction state: satchel! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
                    return
                }
                val material = args[1]
                val amount = if(args.size > 2) args[2].toIntOrNull() else 1
                if(amount == null) {
                    logger.warning("Illegal instruction state: satchel! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
                    return
                }
                val item = ExtendedMaterial.retrieve(material)
                if (item == null){
                    logger.warning("Illegal instruction state: satchel! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
                    return
                }
                item.amount = amount

                questInstance.bundleContents.addItem(item)
                Material.LAPIS_LAZULI
            }
            "give" -> {
                if(questInstance.bundleContents.items.isEmpty()){
                    logger.warning("Illegal instruction state: satchel! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
                    return
                }
                questInstance.giveBundle()
            }
            else -> {
                logger.warning("Illegal instruction state: satchel! (Caused by ${questInstance.player.name}), line ${questInstance.story.currentText})")
                return
            }
        }
    }
}