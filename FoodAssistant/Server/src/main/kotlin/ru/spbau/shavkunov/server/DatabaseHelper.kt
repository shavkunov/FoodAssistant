package ru.spbau.shavkunov.server

import ru.spbau.shavkunov.server.data.AmountType
import ru.spbau.shavkunov.server.data.Ingredient
import ru.spbau.shavkunov.server.data.Recipe
import java.io.File
import java.sql.DriverManager
import java.sql.Statement
import java.util.*


object DatabaseHelper {
    private val dbName = "food_assistant_database.db"

    private fun createRecipeTable(stmt: Statement) {
        val createRecipeTable = "CREATE TABLE Recipe (" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL)"

        stmt.executeUpdate(createRecipeTable)
    }

    private fun createIngredientTable(stmt: Statement) {
        val createIngredientTable = "CREATE TABLE Ingredient (" +
                "name TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "amount_type TEXT NOT NULL," +
                "recipe_ID INTEGER NOT NULL, " +
                "FOREIGN KEY (recipe_ID) REFERENCES Recipe(ID))"

        stmt.executeUpdate(createIngredientTable)
    }

    fun createDatabase() {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        connection.createStatement().execute("PRAGMA foreign_keys = ON")

        connection.autoCommit = false
        val stmt = connection.createStatement()
        createRecipeTable(stmt)
        createIngredientTable(stmt)

        stmt.close()
        connection.commit()
        connection.close()
    }

    fun isDatabaseExists(): Boolean {
        val dbFile = File(dbName)
        return dbFile.exists()
    }

    private fun insertRecipeName(recipe: Recipe, stmt: Statement): Int {
        val recipeName = recipe.name
        val recipeDesc = recipe.description
        val insertRecipeName = "INSERT INTO Recipe(name, description) VALUES ('$recipeName', '$recipeDesc')"

        return stmt.executeUpdate(insertRecipeName)
    }

    private fun insertRecipeIngredients(recipeID: Int, recipe: Recipe, stmt: Statement) {
        for (ingredient in recipe.ingredients!!) {
            val name = ingredient.name
            val amount = ingredient.amount
            val amountType = ingredient.kindOfAmount.toString()
            val insertIngredient = "INSERT INTO Ingredient(name, amount, amount_type, recipe_ID) " +
                                   "VALUES ('$name', $amount, '$amountType', $recipeID)"

            stmt.executeUpdate(insertIngredient)
        }
    }

    fun addRecipe(recipe: Recipe) {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        connection.autoCommit = false
        val stmt = connection.createStatement()

        val recipeID = insertRecipeName(recipe, stmt)
        insertRecipeIngredients(recipeID, recipe, stmt)

        stmt.close()
        connection.commit()
        connection.close()
    }

    private fun getRecipeByID(recipeID: Int) : Recipe {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        connection.autoCommit = false
        val stmt = connection.createStatement()

        val recipeQuery = "SELECT name, description FROM Recipe WHERE ID = " + recipeID
        val mainData = stmt.executeQuery(recipeQuery)
        var recipeName: String? = null
        var recipeDescription: String? = null
        if (mainData.next()) {
            recipeName = mainData.getString("name")
            recipeDescription = mainData.getString("description")
        }

        val ingredientsQuery = "SELECT name, amount, amount_type FROM Ingredient WHERE recipe_ID = " + recipeID
        val ingredientsDB = stmt.executeQuery(ingredientsQuery)
        val ingredients: MutableList<Ingredient> = LinkedList()
        while (ingredientsDB.next()) {
            val name = ingredientsDB.getString("name")
            val amount = ingredientsDB.getString("amount").toDouble()
            val amountType = AmountType.getEnum(ingredientsDB.getString("amount_type"))
            val ingredient = Ingredient(name, amount, amountType)
            ingredients.add(ingredient)
        }

        val recipe = Recipe(recipeName, ingredients, recipeDescription)


        stmt.close()
        connection.close()

        return recipe
    }

    fun getRandomRecipe(): Recipe? {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        connection.autoCommit = false
        val stmt = connection.createStatement()
        val getQuery = "SELECT ID FROM Recipe ORDER BY RANDOM() LIMIT 1"
        val rs = stmt.executeQuery(getQuery)

        var recipeID = -1
        if (rs.next()) {
            recipeID = rs.getInt("ID")
        }

        stmt.close()
        connection.close()

        if (recipeID == -1) {
            return null
        }

        return getRecipeByID(recipeID)
    }

    fun getRecipeByName(recipeName: String): Recipe? {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        val stmt = connection.createStatement()
        val getQuery = "SELECT ID FROM Recipe WHERE name = '" + recipeName + "'"
        val rs = stmt.executeQuery(getQuery)

        var recipeID = -1
        if (rs.next()) {
            recipeID = rs.getInt("ID")
        }

        stmt.close()
        connection.close()

        if (recipeID == -1) {
            return null
        }

        return getRecipeByID(recipeID)
    }

    fun getAllRecipeNames(): List<String> {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        val stmt = connection.createStatement()
        val getQuery = "SELECT name FROM Recipe"
        val rs = stmt.executeQuery(getQuery)

        var recipeNames: MutableList<String> = LinkedList()
        while (rs.next()) {
            recipeNames.add(rs.getString("name"))
        }

        stmt.close()
        connection.close()

        return recipeNames
    }
}