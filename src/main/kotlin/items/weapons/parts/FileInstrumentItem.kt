package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object FileInstrumentItem : WeaponPart {
    override val id: String = "weapon_part_file"
    override val model = 44424
    override val name = Component.text("Напильник").decoration(TextDecoration.ITALIC, false)

    init {
        this.createItem()
    }
}