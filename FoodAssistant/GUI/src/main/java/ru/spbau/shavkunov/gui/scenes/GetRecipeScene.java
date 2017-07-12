package ru.spbau.shavkunov.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.server.DatabaseHelper;
import ru.spbau.shavkunov.server.data.Ingredient;
import ru.spbau.shavkunov.server.data.Recipe;

import static ru.spbau.shavkunov.gui.InterfaceTextConstants.*;

public class GetRecipeScene extends SceneWrap {
    private static final int SCENE_HEIGHT = 600;
    private static final int SCENE_WIDTH = 600;

    private static final int GENERAL_INGREDIENTS_WIDTH = 110;

    private @NotNull VBox recipeView;

    public GetRecipeScene(@NotNull Stage stage) {
        this.stage = stage;
        recipeView = new VBox();
        recipeView.setAlignment(Pos.CENTER);

        Recipe recipe = DatabaseHelper.INSTANCE.getRecipe();
        showRecipe(recipe);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(AddRecipeScene.initBackButton(this.stage), recipeView);
        scene = new Scene(vBox);
    }

    private void showRecipe(@Nullable Recipe recipe) {
        if (recipe == null) {
            Label errorLabel = new Label(NO_RECIPES);
            recipeView.getChildren().addAll(errorLabel);
            return;
        }

        Label header = new Label(recipe.getName());
        Label ingredientsHeader = new Label(INGREDIENTS);

        VBox ingredients = new VBox();
        ingredients.setAlignment(Pos.CENTER);
        for (Ingredient ingredient : recipe.getIngredients()) {
            String amount = "";
            if (ingredient.getAmount() == (int) ingredient.getAmount().doubleValue()) {
                amount += (int) ingredient.getAmount().doubleValue();
            } else {
                amount += ingredient.getAmount();
            }

            String information = MARK + " " + ingredient.getName() + " " +
                   amount + " " + ingredient.getKindOfAmount();

            Label newIngredient = new Label(information);
            //newIngredient.setAlignment(Pos.CENTER);
            newIngredient.setMaxWidth(GENERAL_INGREDIENTS_WIDTH);
            ingredients.getChildren().add(newIngredient);
        }

        Label descriptionHeader = new Label(DESCRIPTION);
        Label description = new Label(recipe.getDescription());

        recipeView.getChildren().addAll(header, ingredientsHeader, ingredients,
                                        descriptionHeader, description);
    }

    @Override
    public int getSceneHeight() {
        return SCENE_HEIGHT;
    }

    @Override
    public int getSceneWidth() {
        return SCENE_WIDTH;
    }
}