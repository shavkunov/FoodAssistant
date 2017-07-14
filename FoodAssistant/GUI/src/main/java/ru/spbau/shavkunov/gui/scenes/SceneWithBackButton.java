package ru.spbau.shavkunov.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.UserInterface;

import static ru.spbau.shavkunov.gui.InterfaceTextConstants.BACK_BUTTON;

public abstract class SceneWithBackButton extends SceneWrap {
    protected @NotNull SceneWrap backScene;

    public abstract int getSceneHeight();
    public abstract int getSceneWidth();

    @Override
    public @NotNull Scene getScene() {
        Button backButton = initBackButton();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(backButton, parent);

        return new Scene(vBox);
    }

    private @NotNull Button initBackButton() {
        Button backButton = new Button(BACK_BUTTON);
        backButton.setOnAction(actionEvent ->
                UserInterface.showScene(backScene));

        backButton.setAlignment(Pos.TOP_LEFT);
        return backButton;
    }
}