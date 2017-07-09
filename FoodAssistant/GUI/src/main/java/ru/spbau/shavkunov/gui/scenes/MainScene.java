package ru.spbau.shavkunov.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.UserInterface;

import static ru.spbau.shavkunov.gui.TextConstants.*;

public class MainScene extends SceneWrap {
    private static final int STAGE_HEIGHT = 500;
    private static final int STAGE_WIDTH = 300;
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
        initGetRecipeButton(vbox);
        initAddRecipeButton(vbox);
        // TODO : list purchases
        initExitButton(vbox, stage);

        this.scene = new Scene(vbox);
    }

    /**
     * Initializing get recipe button.
     * @param pane pane, where button will be added.
     */
    private void initGetRecipeButton(@NotNull Pane pane) {
        Button getRecipeButton = new Button(GET_RECIPE_LABEL);
        getRecipeButton.setMaxWidth(BUTTON_WIDTH);

        getRecipeButton.setOnAction(actionEvent ->  {
            // TODO
        });

        pane.getChildren().add(getRecipeButton);
    }

    /**
     * Initializing add recipe button.
     * @param pane pane, where button will be added.
     */
    private void initAddRecipeButton(@NotNull Pane pane) {
        Button addRecipeButton = new Button(ADD_RECIPE_LABEL);
        addRecipeButton.setMaxWidth(BUTTON_WIDTH);

        addRecipeButton.setOnAction(actionEvent ->  {
            UserInterface.showScene(new AddRecipeScene(stage));
        });

        pane.getChildren().add(addRecipeButton);
    }

    /**
     * Initializing exit button.
     * @param pane pane, where button will be added.
     * @param stage main stage.
     */
    private void initExitButton(@NotNull Pane pane, @NotNull Stage stage) {
        Button exitButton = new Button(EXIT_LABEL);
        exitButton.setMaxWidth(BUTTON_WIDTH);

        exitButton.setOnAction(actionEvent -> stage.close());

        pane.getChildren().add(exitButton);
    }

    @Override
    public int getStageHeight() {
        return STAGE_HEIGHT;
    }

    @Override
    public int getStageWidth() {
        return STAGE_WIDTH;
    }
}