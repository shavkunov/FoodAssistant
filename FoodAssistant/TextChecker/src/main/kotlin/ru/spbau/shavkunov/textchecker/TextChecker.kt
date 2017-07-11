package ru.spbau.shavkunov.textchecker

import ru.spbau.shavkunov.server.data.Recipe

object TextChecker {
    fun checkRawData(recipe: Recipe) : Any {
        if (recipe.name == null) {
            return INVALID_RECIPE_NAME
        }

        return recipe
    }
}