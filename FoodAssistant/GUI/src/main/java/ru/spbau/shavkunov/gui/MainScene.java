package ru.spbau.shavkunov.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static ru.spbau.shavkunov.gui.TextConstants.*;

/**
 * Main menu.
 */
public class MainScene extends Application {
    private static double STAGE_HEIGHT = 500;
    private static double STAGE_WIDTH = 300;
    private static double BUTTON_WIDTH = 150;

    /**
     * Controlls the core.
     */
    //private static final controller = new Controller();

    @Override
    public void start(@NotNull Stage stage) throws Exception {
        // init scene with group objects
        stage.setTitle("Food Assistant");
        stage.setResizable(false);

        Scene startScene = initMainScene(stage);

        stage.setScene(startScene);
        stage.show();
    }

    /**
     * Initializing of all objects on the scene.
     * @param stage stage.
     */
    private @NotNull Scene initMainScene(@NotNull Stage stage) {
        stage.setHeight(STAGE_HEIGHT);
        stage.setWidth(STAGE_WIDTH);

        VBox vbox = new VBox();

        vbox.setAlignment(Pos.CENTER);
        initGetRecipeButton(vbox);
        initAddRecipeButton(vbox);
        // TODO : list purchases
        initExitButton(vbox, stage);

        Scene startingScene = new Scene(vbox);

        return startingScene;
    }

    /**
     * Initializing get recipe button.
     * @param pane pane, where button will be added.
     */
    private void initGetRecipeButton(@NotNull Pane pane) {
        Button getRecipeButton = new Button(getRecipeLabel);
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
        Button addRecipeButton = new Button(addRecipeLabel);
        addRecipeButton.setMaxWidth(BUTTON_WIDTH);

        addRecipeButton.setOnAction(actionEvent ->  {
            // TODO
        });

        pane.getChildren().add(addRecipeButton);
    }

    /**
     * Initializing exit button.
     * @param pane pane, where button will be added.
     * @param stage main stage.
     */
    private void initExitButton(@NotNull Pane pane, @NotNull Stage stage) {
        Button exitButton = new Button(exitLabel);
        exitButton.setMaxWidth(BUTTON_WIDTH);

        exitButton.setOnAction(actionEvent ->  {
            stage.close();
        });

        pane.getChildren().add(exitButton);
    }

    /**
     * Launching UI.
     */
    public static void main(@NotNull String[] args) throws IOException {
        Application.launch(args);
    }
}
