package items

import RadioLampEngine
import items.depers.*
import items.extra.*
import items.hearts.CookedHeart
import items.hearts.Heart
import items.metaphysics.*
import items.misc.*
import items.weapons.WeaponTableSummonerItem
import items.weapons.guns.*
import items.weapons.parts.*

object RLEngineItems {
    private val RLEItems = mutableMapOf<String, AbstractRLItem>()
    private val registryElements = listOf(
        // And now, the list of ALL items for registration. Cuz kotlin can't have self-instantiating singletons
        // & I don't have time to fuck around with reflections
        // depers
        BottleTotemItem,
        BreezeTotemItem,
        CacadooTotemItem,
        DuloTotemItem,
        DurilkaTotemItem,
        FanTotemItem,
        FridayTotemItem,
        PlitkaTotemItem,
        ShieldTotemItem,
        ShockerTotemItem,
        ThermopotTotemItem,
        ToasterTotemItem,
        ZhirikTotemItem,
        // misc,
        VeryHotPhoneItem,
        BackpackItem,
        GliderItem,
        WheelchairItem,
        KalykItem,
        MoonDustItem,
        HandDrill,
        SmeltingPickaxe,
        // metaphysics,
        ColdPhoneItem,
        Heart,
        CookedHeart,
        // anti-depers armor
        AntiDepersHelmetItem,
        AntiDepersChestplateItem,
        AntiDepersLeggingsItem,
        AntiDepersBootsItem,
        // weapons,
        TestingCrossbowItem,
        RocketGunItem,
        MachineGunItem,
        RevolverGunItem,
        ShotgunGunItem,
        SawedOffGunItem,
        HeavyRifleWeaponItem,
        LightRifleGunItem,
        CollapseRifleGunItem,
        WeaponTableSummonerItem,
        // weapon parts,
        WeaponNetItem,
        WeaponLightningItem,
        SmoothBarrelItem,
        ShotgunMagItem,
        ShotgunFastUpgradeItem,
        RifledBarrelItem,
        ReturnSpringItem,
        IgniterItem,
        HookItem,
        FileInstrumentItem,
        DoubleBarrelItem,
        BuldygaItem,
        BayonetItem,
        BarrelGrenadeItem,
        // extras
        BitardHelmet,
        BlackHat,
        BrownHat,
        CactusVodka,
        CoffeeBottle,
        CoolGoggles,
        EcononyMapArt,
        EnchantedGrapefruit,
        EnchantedOrange,
        GurocratHat,
        HeadLight,
        KupanieHelmet,
        OtoBoots,
        OtoHelmet,
        RiceBowl,
        RumBottle,
        WhiskeyBottle,
        WorkCap,
        ZippoLighter,
    )
    init{
        registryElements.forEach { it.createItem() }
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