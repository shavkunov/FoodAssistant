package ru.spbau.shavkunov.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SceneWrap {
    protected @Nullable Scene scene;
    protected @Nullable Stage stage;

    public abstract int getStageHeight();
    public abstract int getStageWidth();

    public @NotNull Scene getScene() {
        return scene;
    }

    public @NotNull Stage getStage() {
        return stage;
    }
}