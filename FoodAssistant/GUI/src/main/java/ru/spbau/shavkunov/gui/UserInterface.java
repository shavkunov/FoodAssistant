package ru.spbau.shavkunov.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.scenes.MainScene;
import ru.spbau.shavkunov.gui.scenes.SceneWrap;
import ru.spbau.shavkunov.server.DatabaseHelper;
import ru.spbau.shavkunov.server.data.Recipe;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Main menu.
 */
public class UserInterface extends Application {
    // TODO: List of strings. Because recipe might be too big.
    private static @NotNull List<Recipe> purchases;

    @Override
    public void start(@NotNull Stage stage) throws Exception {
        // init scene with group objects
        stage.setTitle("Food Assistant");
        stage.setResizable(false);

        purchases = new LinkedList<>();

        MainScene mainScene = new MainScene(stage);
        initDatabase();

        showScene(mainScene);
    }

    private void initDatabase() {
        if (!DatabaseHelper.INSTANCE.isDatabaseExists()) {
            DatabaseHelper.INSTANCE.createDatabase();
        }
    }

    public static void showScene(@NotNull SceneWrap wrap) {
        Stage stage = wrap.getStage();
        stage.setHeight(wrap.getSceneHeight());
        stage.setWidth(wrap.getSceneWidth());

        stage.setScene(wrap.getScene());
        stage.show();
    }

    public static List<Recipe> getPurchases() {
        return purchases;
    }

    /**
     * Launching UI.
     */
    public static void main(@NotNull String[] args) throws IOException {
        Application.launch(args);
    }
}