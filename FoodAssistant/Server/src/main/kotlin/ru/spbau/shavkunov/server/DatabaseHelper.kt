package ru.spbau.shavkunov.server

import ru.spbau.shavkunov.server.data.Recipe
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

object DatabaseHelper {
    private val dbName = "~/food_assistant_database.db"
    private var connection: Connection

    init {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName)
        connection.createStatement().execute("PRAGMA foreign_keys = ON")
    }

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
        connection.autoCommit = false
        val stmt = connection.createStatement()
        createRecipeTable(stmt)
        createIngredientTable(stmt)

        stmt.close()
        connection.commit()
        connection.close()
    }

    fun isDatabaseexists(): Boolean {
        val dbFile = File(dbName)
        return dbFile.exists()
    }

    private fun insertRecipeName(recipe: Recipe, stmt: Statement): Int {
        val recipeName = recipe.name
        val recipeDesc = recipe.description
        val insertRecipeName = "INSERT INTO Recipe VALUES ($recipeName, $recipeDesc)"

        return stmt.executeUpdate(insertRecipeName)
    }

    private fun insertRecipeIngredients(recipeID: Int, recipe: Recipe, stmt: Statement) {
        for (ingredient in recipe.ingredients) {
            val name = ingredient.name
            val amount = ingredient.amount
            val amountType = ingredient.kindOfAmount.toString()
            val insertIngredient = "INSERT INTO Ingredient VALUES " +
                                   "($name, $amount, $amountType, $recipeID)"

            stmt.executeUpdate(insertIngredient)
        }
    }

    fun addRecipe(recipe: Recipe) {
        val stmt = connection.createStatement()

        val recipeID = insertRecipeName(recipe, stmt)
        insertRecipeIngredients(recipeID, recipe, stmt)

        stmt.close()
        connection.commit()
        connection.close()
    }
}