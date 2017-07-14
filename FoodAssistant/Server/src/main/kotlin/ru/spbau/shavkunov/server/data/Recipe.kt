package ru.spbau.shavkunov.server.data

class Recipe(var name: String?, var ingredients: List<Ingredient>?, var description: String?) {
    override fun toString(): String {
        if (name == null || ingredients == null || description == null) {
            return ""
        }

        var recipeString = name + "\n"
        return recipeString + getRecipeDetailedDescription()
    }

    fun getRecipeDetailedDescription(): String {
        var detailedDescription = ""

        for (ingredient in ingredients!!) {
            detailedDescription += ingredient.toString() + "\n"
        }
        detailedDescription += description + "\n"

        return detailedDescription
    }
}