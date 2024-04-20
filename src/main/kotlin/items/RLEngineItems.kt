package items

import RadioLampEngine
import items.depers.*
import items.hearts.CookedHeart
import items.hearts.Heart
import items.metaphysics.ColdPhoneItem
import items.misc.*
import items.weapons.WeaponTableSummonerItem
import items.weapons.guns.*
import items.weapons.parts.*

object RLEngineItems {
    private val RLEItems = mutableMapOf<String, AbstractRLItem>()
    init{
        // And now, the list of ALL items for registration. Cuz kotlin can't have self-instantiating singletons
        // & I don't have time to fuck around with reflections
        // depers
        BottleTotemItem
        BreezeTotemItem
        CacadooTotemItem
        DuloTotemItem
        DurilkaTotemItem
        FanTotemItem
        FridayTotemItem
        PlitkaTotemItem
        ShieldTotemItem
        ShockerTotemItem
        ThermopotTotemItem
        ToasterTotemItem
        ZhirikTotemItem
        // misc
        VeryHotPhoneItem
        BackpackItem
        GliderItem
        WheelchairItem
        KalykItem
        MoondustItem
        // metaphys
        ColdPhoneItem
        Heart
        CookedHeart
        // weapons
        TestingCrossbowItem
        RocketGunItem
        MachineGunItem
        RevolverGunItem
        ShotgunGunItem
        SawedOffGunItem
        HeavyRifleWeapon
        LightRifleGunItem
        CollapseRifleGunItem
        WeaponTableSummonerItem
        // weapon parts
        WeaponNetItem
        WeaponLightningItem
        SmoothBarrelItem
        ShotgunMagItem
        ShotgunFastUpgradeItem
        RifledBarrelItem
        ReturnSpringItem
        IgniterItem
        HookItem
        FileInstrumentItem
        DoubleBarrelItem
        BuldygaItem
        BayonetItem
        BarrelGrenadeItem

    }

    fun registerItem(id:String, item: AbstractRLItem){
        RLEItems[id] = item
        RadioLampEngine.instance.logger.info("Registered item: $id")
    }

    fun fetchItem(id: String): AbstractRLItem?{
        return RLEItems[id]
    }

    fun getItems() = RLEItems.keys.toList()
}