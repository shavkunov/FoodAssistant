package ru.spbau.shavkunov.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.scenes.MainScene;
import ru.spbau.shavkunov.gui.scenes.SceneWrap;

import java.io.IOException;

/**
 * Main menu.
 */
public class UserInterface extends Application {
    @Override
    public void start(@NotNull Stage stage) throws Exception {
        // init scene with group objects
        stage.setTitle("Food Assistant");
        stage.setResizable(false);

        MainScene mainScene = new MainScene(stage);

        showScene(mainScene);
    }

    public static void showScene(@NotNull SceneWrap wrap) {
        Stage stage = wrap.getStage();
        stage.setHeight(wrap.getStageHeight());
        stage.setWidth(wrap.getStageWidth());

        stage.setScene(wrap.getScene());
        stage.show();
    }

    /**
     * Launching UI.
     */
    public static void main(@NotNull String[] args) throws IOException {
        Application.launch(args);
    }
}