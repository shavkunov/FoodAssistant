package ru.spbau.shavkunov.textchecker

import ru.spbau.shavkunov.server.data.Ingredient
import ru.spbau.shavkunov.server.data.Recipe

object TextChecker {
    fun checkRawData(recipe: Recipe?): Any? {
        if (recipe == null) {
            return null
        }

        if (recipe.name == null || recipe.name == "") {
            return INVALID_RECIPE_NAME
        }

        if (recipe.ingredients == null || (recipe.ingredients as List<Ingredient>).isEmpty()) {
            return NO_INGREDIENTS
        }

        // not very good decision. Maybe, I want to highlight the field with error.
        // For this purpose I need(healing) a class, which contains more useful information.
        // but I don't think, that I need this.
        val ingredients = recipe.ingredients
        for (ingredient in ingredients!!) {
            if (ingredient.name == null || ingredient.name == "") {
                return INVALID_INGREDIENT_NAME
            }

            if (ingredient.amount == null || ingredient.amount == 0) {
                return INVALID_INGREDIENT_AMOUNT
            }

            if (ingredient.kindOfAmount == null) {
                return INVALID_AMOUNT_TYPE
            }
        }

        if (recipe.description == null || recipe.description == "") {
            return INVALID_RECIPE_DESCRIPTION
        }

        return recipe
    }
}