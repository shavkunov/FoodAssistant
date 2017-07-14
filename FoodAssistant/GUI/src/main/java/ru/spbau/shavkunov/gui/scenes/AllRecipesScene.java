package ru.spbau.shavkunov.gui.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.UserInterface;
import ru.spbau.shavkunov.server.DatabaseHelper;
import ru.spbau.shavkunov.server.data.Recipe;

import java.util.LinkedList;
import java.util.List;

public class AllRecipesScene extends SceneWithBackButton {
    private static final int SCENE_HEIGHT = 600;
    private static final int SCENE_WIDTH = 600;

    private static final int LABEL_WIDTH = 300;

    private @NotNull ListView<Pane> recipes;

    public AllRecipesScene(@NotNull Stage stage, @NotNull SceneWrap backScene) {
        this.backScene = backScene;
        this.stage = stage;

        initRecipesView();
        this.parent = recipes;
    }

    private void initRecipesView() {
        recipes = new ListView<>();
        List<String> recipeNames = DatabaseHelper.INSTANCE.getAllRecipeNames();

        List<Pane> cells = new LinkedList<>();
        for (String recipeName : recipeNames) {
            cells.add(getCustomCell(recipeName));
        }

        recipes.getItems().addAll(cells);

        recipes.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Label currentRecipe = (Label) recipes.getSelectionModel().getSelectedItem().getChildren().get(0);
                Recipe recipe = DatabaseHelper.INSTANCE.getRecipeByName(currentRecipe.getText());
                SceneWithBackButton newBackScene = new AllRecipesScene(stage, backScene);
                SceneWithBackButton recipeScene = new RecipeScene(stage, recipe, newBackScene);
                UserInterface.showScene(recipeScene);
            }
        });
    }

    private @NotNull Pane getCustomCell(String recipeName) {
        HBox hBox = new HBox();
        Label recipeLabel = new Label(recipeName);
        recipeLabel.setMinWidth(LABEL_WIDTH);
        Recipe recipe = DatabaseHelper.INSTANCE.getRecipeByName(recipeName);
        Button addToListButton = RecipeScene.initAddToPurchasesListButton(recipe);

        hBox.getChildren().addAll(recipeLabel, addToListButton);

        return hBox;
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