package ru.spbau.shavkunov.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.UserInterface;
import ru.spbau.shavkunov.server.DatabaseHelper;
import ru.spbau.shavkunov.server.data.Recipe;

import static ru.spbau.shavkunov.gui.InterfaceTextConstants.*;

public class MainScene extends SceneWrap {
    private static final int SCENE_HEIGHT = 500;
    private static final int SCENE_WIDTH = 300;
    private static final int BUTTON_WIDTH = 150;

    public MainScene(@NotNull Stage stage) {
        this.stage = stage;
        initMainScene();
    }

    /**
     * Initializing of all objects on the scene.
     */
    private void initMainScene() {
        VBox vbox = new VBox();

        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(initAllRecipesButton(), initGetRecipeButton(), initAddRecipeButton(),
                                  initListPurchasesButton(), initExitButton());

        this.parent = vbox;
    }

    /**
     * Initializing get recipe button.
     */
    private @NotNull Button initGetRecipeButton() {
        Button getRecipeButton = new Button(GET_RECIPE_LABEL);
        getRecipeButton.setMaxWidth(BUTTON_WIDTH);

        getRecipeButton.setOnAction(actionEvent -> {
            Recipe recipe = DatabaseHelper.INSTANCE.getRandomRecipe();
            UserInterface.showScene(new RecipeScene(this.stage, recipe, new MainScene(this.stage)));
        });

        return getRecipeButton;
    }

    /**
     * Initializing add recipe button.
     */
    private @NotNull Button initAddRecipeButton() {
        Button addRecipeButton = new Button(ADD_RECIPE_LABEL);
        addRecipeButton.setMaxWidth(BUTTON_WIDTH);

        addRecipeButton.setOnAction(actionEvent ->
                UserInterface.showScene(new AddRecipeScene(stage, new MainScene(this.stage))));

        return addRecipeButton;
    }

    /**
     * Initializing exit button.
     */
    private @NotNull Button initExitButton() {
        Button exitButton = new Button(EXIT_LABEL);
        exitButton.setMaxWidth(BUTTON_WIDTH);

        exitButton.setOnAction(actionEvent -> stage.close());

        return exitButton;
    }

    private @NotNull Button initListPurchasesButton() {
        Button purchasesButton = new Button(PURCHASES_LIST);
        purchasesButton.setMaxWidth(BUTTON_WIDTH);

        purchasesButton.setOnAction(actionEvent -> {
            // TODO scene
        });

        return purchasesButton;
    }

    private @NotNull Button initAllRecipesButton() {
        Button allRecipesButton = new Button(ALL_RECIPES);
        allRecipesButton.setMaxWidth(BUTTON_WIDTH);

        allRecipesButton.setOnAction(actionEvent -> {
            SceneWithBackButton allRecipes = new AllRecipesScene(this.stage, new MainScene(this.stage));
            UserInterface.showScene(allRecipes);
        });

        return allRecipesButton;
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