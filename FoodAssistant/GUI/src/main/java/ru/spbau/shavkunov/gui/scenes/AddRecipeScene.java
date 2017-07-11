package ru.spbau.shavkunov.gui.scenes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.gui.UserInterface;
import ru.spbau.shavkunov.server.DatabaseHelper;
import ru.spbau.shavkunov.server.data.AmountType;
import ru.spbau.shavkunov.server.data.Ingredient;
import ru.spbau.shavkunov.server.data.Recipe;
import ru.spbau.shavkunov.textchecker.TextChecker;

import java.util.LinkedList;
import java.util.List;

import static ru.spbau.shavkunov.gui.InterfaceTextConstants.*;

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

    private @Nullable TextField nameField;
    private @Nullable ListView<Pane> ingredientList;
    private @Nullable TextArea descriptionField;
    private @Nullable Label infoLabel;

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
        nameField = new TextField();
        nameField.setMinWidth(GENERAL_WIDTH);

        HBox hb = new HBox();
        hb.getChildren().addAll(enterName, nameField);
        hb.setSpacing(SPACE);

        gridPane.addRow(1, hb);
    }

    private void initIngredientsField() {
        Label enterRecipeIngredients = new Label(ENTER_RECIPE_INGREDIENTS_LABEL);

        ingredientList = new ListView<>();
        ingredientList.setMinWidth(GENERAL_WIDTH);
        ingredientList.setMaxHeight(LIST_VIEW_HEIGHT);

        Button addNewLine = new Button(ADD_NEW_INGREDIENT_BUTTON);
        addNewLine.setOnAction(actionEvent ->  {
            ObservableList<Pane> items = ingredientList.getItems();
            items.add(getNewCell());
            ingredientList.setItems(items);
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(enterRecipeIngredients, ingredientList, addNewLine);
        hb.setSpacing(SPACE);

        gridPane.addRow(2, hb);
    }

    private Pane getNewCell() {
        TextField ingredientName = new TextField();
        TextField ingredientAmount = new TextField();
        ingredientAmount.setMaxSize(AMOUNT_MAX_WIDTH, AMOUNT_MAX_HEIGHT);
        ChoiceBox kindOfAmount = new ChoiceBox();
        kindOfAmount.setItems(FXCollections.observableArrayList(AmountType.values()));

        HBox hb = new HBox();
        hb.getChildren().addAll(ingredientName, ingredientAmount, kindOfAmount);
        hb.setSpacing(SPACE);

        return hb;
    }

    private void initDescriptionField() {
        Label enterName = new Label(ENTER_DESCRIPTION_LABEL);
        descriptionField = new TextArea();

        descriptionField.setMinHeight(DESCRIPTION_MIN_SIZE);
        descriptionField.setMaxWidth(GENERAL_WIDTH);
        HBox hb = new HBox();
        hb.getChildren().addAll(enterName, descriptionField);
        hb.setSpacing(SPACE);

        VBox vBox = new VBox();

        infoLabel = new Label();
        infoLabel.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hb, initSubmitButton(), infoLabel);
        vBox.setAlignment(Pos.CENTER);

        gridPane.addRow(3, vBox);
    }

    private @NotNull Button initBackButton() {
        Button backButton = new Button(BACK_BUTTON);
        backButton.setOnAction(actionEvent ->
                UserInterface.showScene(new MainScene(this.stage)));

        return backButton;
    }

    private @NotNull Button initSubmitButton() {
        Button submitButton = new Button(SUBMIT_BUTTON);
        submitButton.setOnAction(actionEvent ->  {
            Recipe rawData = collectRecipe();
            Object result = TextChecker.INSTANCE.checkRawData(rawData);

            if (result instanceof String) {
                infoLabel.setText((String) result);
            } else {
                DatabaseHelper helper = DatabaseHelper.INSTANCE;
                helper.addRecipe((Recipe) result);

                infoLabel.setText(SUCCESSFUL_ADDED);
                // TODO : maybe fading text?
            }
        });

        submitButton.setAlignment(Pos.CENTER);
        return submitButton;
    }

    private @Nullable Recipe collectRecipe() {
        String name = nameField.getText();
        String description = descriptionField.getText();

        List<Ingredient> ingredients = new LinkedList<>();
        for (Pane ingredientPane : ingredientList.getItems()) {
            List<Node> children = ingredientPane.getChildren();

            // bad code...
            String ingredientName = ((TextField) children.get(0)).getText();
            int amount = Integer.parseInt(((TextField) children.get(1)).getText());
            String amountType = (String) ((ChoiceBox) children.get(2)).getSelectionModel().getSelectedItem();
            AmountType castedAmount = AmountType.Companion.getEnum(amountType);

            Ingredient ingredient = new Ingredient(ingredientName, amount, castedAmount);
            ingredients.add(ingredient);
        }

        Recipe newRecipe = new Recipe(name, ingredients, description);

        return newRecipe;
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