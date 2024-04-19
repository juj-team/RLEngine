package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object ReturnSpringItem : WeaponPart {
    override val id: String = "weapon_part_return_spring"
    override val model = 44426
    override val name = Component.text("Возвратная пружина").decoration(TextDecoration.ITALIC, false)

    init {
        this.createItem()
    }
}