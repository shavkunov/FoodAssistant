package ru.spbau.shavkunov.gui.scenes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.gui.UserInterface;

import static ru.spbau.shavkunov.gui.TextConstants.*;

public class AddRecipeScene extends SceneWrap {
    private static final int STAGE_HEIGHT = 700;
    private static final int STAGE_WIDTH = 700;

    private static final int SPACE = 5;
    private static final int AMOUNT_MAX_WIDTH = 50;
    private static final int AMOUNT_MAX_HEIGHT = 10;

    private static final int DESCRIPTION_MIN_SIZE = 200;

    private static final int LIST_VIEW_HEIGHT = 300;

    private static final int GENERAL_WIDTH = 350;
    private @NotNull GridPane gridPane;

    public AddRecipeScene(@NotNull Stage stage) {
        this.stage = stage;
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        // TODO : padding for gridPane. But simpliest way for somewhat doesn't work.

        initNameRecipeField();
        initIngredientsField();
        initDescriptionField();
        Button backButton = initBackButton();

        backButton.setAlignment(Pos.TOP_LEFT);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(backButton, gridPane);

        this.scene = new Scene(vBox);
    }

    private void initNameRecipeField() {
        Label enterName = new Label(ENTER_NAME_LABEL);
        TextField textField = new TextField();
        textField.setMinWidth(GENERAL_WIDTH);

        HBox hb = new HBox();
        hb.getChildren().addAll(enterName, textField);
        hb.setSpacing(SPACE);

        gridPane.addRow(1, hb);
    }

    private void initIngredientsField() {
        Label enterRecipeIngredients = new Label(ENTER_RECIPE_INGREDIENTS_LABEL);

        ListView<Pane> list = new ListView<>();
        list.setMinWidth(GENERAL_WIDTH);
        list.setMaxHeight(LIST_VIEW_HEIGHT);

        Button addNewLine = new Button(ADD_NEW_INGREDIENT_BUTTON);
        addNewLine.setOnAction(actionEvent ->  {
            ObservableList<Pane> items = list.getItems();
            items.add(getNewCell());
            list.setItems(items);
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(enterRecipeIngredients, list, addNewLine);
        hb.setSpacing(SPACE);

        gridPane.addRow(2, hb);
    }

    private Pane getNewCell() {
        TextField ingredientName = new TextField();
        TextField ingredientAmount = new TextField();
        ingredientAmount.setMaxSize(AMOUNT_MAX_WIDTH, AMOUNT_MAX_HEIGHT);
        ChoiceBox kindOfAmount = new ChoiceBox();
        kindOfAmount.setItems(FXCollections.observableArrayList("g", "piece(-s)", "kg", "ml", "tbsp"));

        HBox hb = new HBox();
        hb.getChildren().addAll(ingredientName, ingredientAmount, kindOfAmount);
        hb.setSpacing(SPACE);

        return hb;
    }

    private void initDescriptionField() {
        Label enterName = new Label(ENTER_DESCRIPTION_LABEL);
        TextArea textArea = new TextArea();

        textArea.setMinHeight(DESCRIPTION_MIN_SIZE);
        textArea.setMaxWidth(GENERAL_WIDTH);
        HBox hb = new HBox();
        hb.getChildren().addAll(enterName, textArea);
        hb.setSpacing(SPACE);

        gridPane.addRow(3, hb);
    }

    private @NotNull Button initBackButton() {
        Button backButton = new Button(BACK_BUTTON);
        backButton.setOnAction(actionEvent ->  {
            UserInterface.showScene(new MainScene(this.stage));
        });

        return backButton;
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