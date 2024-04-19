package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object IgniterItem : WeaponPart {
    override val id: String = "weapon_part_igniter"
    override val model = 44428
    override val name = Component.text("Фитиль").decoration(TextDecoration.ITALIC, false)

    init {
        this.createItem()
    }
}