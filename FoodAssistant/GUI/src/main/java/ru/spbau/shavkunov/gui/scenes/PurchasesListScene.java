package ru.spbau.shavkunov.gui.scenes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.server.data.Recipe;

import java.util.LinkedList;
import java.util.List;

import static ru.spbau.shavkunov.gui.InterfaceTextConstants.*;
import static ru.spbau.shavkunov.gui.scenes.AllRecipesScene.selectItemDoubleClick;

public class PurchasesListScene extends SceneWithBackButton {
    // TODO: List of strings. Because recipe might be too big.
    private static @NotNull List<Recipe> purchases = new LinkedList<>();

    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 600;

    private @NotNull ListView<Pane> recipes;

    public PurchasesListScene(@NotNull Stage stage, @NotNull SceneWrap backScene) {
        this.stage = stage;
        this.backScene = backScene;

        Label header = new Label(PURCHASES_HEADER);
        header.setAlignment(Pos.CENTER);
        recipes = initPurchasesView();
        Pane controlMenu = initControlMenu();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(header, recipes, controlMenu);

        this.parent = vBox;
    }

    private @NotNull Pane initControlMenu() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        Button generateToClipboardButton = initListInClipboardButton();
        Button createNoteAtNotes = initCreateNoteButton();
        hBox.getChildren().addAll(generateToClipboardButton, createNoteAtNotes);

        return hBox;
    }

    private @NotNull String createPurchasesList() {
        return "";
    }

    private @NotNull Button initCreateNoteButton() {
        Button createNoteButton = new Button(CREATE_TEXT_NOTE);

        createNoteButton.setOnAction(actionEvent -> {

        });

        return createNoteButton;
    }

    private @NotNull Button initListInClipboardButton() {
        Button copyToClipboardButton = new Button(COPY_TO_CLIPBOARD);

        return copyToClipboardButton;
    }

    private @NotNull ListView<Pane> initPurchasesView() {
        ListView<Pane> listView = new ListView<>();
        List<Pane> items = new LinkedList<>();
        for (Recipe recipe : purchases) {
            items.add(getCustomCell(recipe));
        }

        listView.getItems().addAll(items);

        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                SceneWithBackButton newBackScene = new PurchasesListScene(stage, backScene);
                selectItemDoubleClick(recipes, newBackScene);
            }
        });

        return listView;
    }

    private @NotNull Pane getCustomCell(@NotNull Recipe recipe) {
        HBox hBox = new HBox();
        Label recipeLabel = new Label(recipe.getName());
        recipeLabel.setMinWidth(AllRecipesScene.getLabelWidth());
        Button deleteFromListButton = initDeleteFromListButton();

        deleteFromListButton.setOnAction(actionEvent -> {
            ObservableList items = recipes.getItems();
            items.remove(hBox);
            recipes.setItems(items);
        });

        hBox.getChildren().addAll(recipeLabel, deleteFromListButton);

        return hBox;
    }

    private @NotNull Button initDeleteFromListButton() {
        Button deleteButton = new Button(DELETE_FROM_PURCHASES);

        deleteButton.setOnAction(actionEvent -> {
            Pane selectedItem = recipes.getSelectionModel().getSelectedItem();
            ObservableList<Pane> items = recipes.getItems();
            items.remove(selectedItem);
            recipes.setItems(items);
        });

        return deleteButton;
    }

    public static List<Recipe> getPurchases() {
        return purchases;
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