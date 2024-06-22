package quests

import RadioLampEngine
import com.bladecoder.ink.runtime.Story
import java.io.File

object QuestLoader {
    private val questDirectory = File(RadioLampEngine.instance.dataFolder.absolutePath + "/quests")
    private val quests = mutableMapOf<String, String>()
    init{
        loadQuests()
    }

    private fun loadQuests() {
        quests.clear()
        RadioLampEngine.instance.logger.info(questDirectory.absolutePath)
        val files = questDirectory.listFiles { pathname ->
            pathname.isFile && pathname.absolutePath.endsWith(".json")
        }
        files?.forEach {
            quests[it.name] = it.readText()
            RadioLampEngine.instance.logger.info("Quest loaded: ${it.name}")
        }
    }
    fun getAvailableQuests(): List<String>{
        return quests.keys.toList()
    }
    fun loadStory(name: String): Story? {
        return Story(quests[name]?:return null)
    }
}