package ru.spbau.shavkunov.server.data

import ru.spbau.shavkunov.server.Bullet.MARK

class Ingredient(var name: String?, var amount: Double?, var kindOfAmount: AmountType?) {
    override fun toString(): String {
        if (name == null || amount == null || kindOfAmount == null) {
            return ""
        }

        val ingredientString = MARK + " " + name + " " + amount + " " + kindOfAmount.toString()

        return ingredientString
    }
}