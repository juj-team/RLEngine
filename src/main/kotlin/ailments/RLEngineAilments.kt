package ailments

import RadioLampEngine
import util.RLEngineTaskManager
import kotlin.random.Random

object RLEngineAilments {
    private val ailments = mutableMapOf<String, RLEngineAilment>()

    init{
        MoonInfection
    }

    fun register(id: String, ailment: RLEngineAilment){
        ailments[id] = ailment
        RLEngineTaskManager.runTask(ailment.processor, Random.nextLong(20, 400), Random.nextLong(18, 30))
        RadioLampEngine.instance.logger.info("Registered ailment: $id")
    }

    fun getAilments() = ailments.keys.toList()
    fun fetchAilment(id: String) = ailments[id]
}