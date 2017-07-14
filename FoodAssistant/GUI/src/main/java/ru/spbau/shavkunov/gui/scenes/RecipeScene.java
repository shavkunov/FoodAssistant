package ru.spbau.shavkunov.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.gui.UserInterface;
import ru.spbau.shavkunov.server.Bullet;
import ru.spbau.shavkunov.server.data.Ingredient;
import ru.spbau.shavkunov.server.data.Recipe;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import static ru.spbau.shavkunov.gui.InterfaceTextConstants.*;

public class RecipeScene extends SceneWithBackButton {
    private static final int SCENE_HEIGHT = 600;
    private static final int SCENE_WIDTH = 600;

    private static final int GENERAL_INGREDIENTS_WIDTH = 150;

    private @NotNull VBox recipeView;
    private @Nullable Recipe recipe;

    public RecipeScene(@NotNull Stage stage, @Nullable Recipe recipe, @NotNull SceneWrap backScene) {
        this.backScene = backScene;
        this.stage = stage;
        recipeView = new VBox();
        recipeView.setAlignment(Pos.CENTER);
        this.recipe = recipe;

        showRecipe(recipe);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(recipeView, initControlMenu());
        this.parent = vBox;
    }

    private void showRecipe(@Nullable Recipe recipe) {
        if (recipe == null) {
            Label errorLabel = new Label(NO_RECIPES);
            recipeView.getChildren().addAll(errorLabel);
            return;
        }

        Label header = new Label(recipe.getName());
        Label ingredientsHeader = new Label(INGREDIENTS);

        VBox ingredients = new VBox();
        ingredients.setAlignment(Pos.CENTER);
        for (Ingredient ingredient : recipe.getIngredients()) {
            String amount = "";
            if (ingredient.getAmount() == (int) ingredient.getAmount().doubleValue()) {
                amount += (int) ingredient.getAmount().doubleValue();
            } else {
                amount += ingredient.getAmount();
            }

            String information = Bullet.MARK + " " + ingredient.getName() + " " +
                   amount + " " + ingredient.getKindOfAmount();

            Label newIngredient = new Label(information);
            newIngredient.setMaxWidth(GENERAL_INGREDIENTS_WIDTH);
            ingredients.getChildren().add(newIngredient);
        }

        Label descriptionHeader = new Label(DESCRIPTION);
        Label description = new Label(recipe.getDescription());

        recipeView.getChildren().addAll(header, ingredientsHeader, ingredients,
                                        descriptionHeader, description);
    }

    private @NotNull Button initCopyToClipboardButton() {
        Button copyButton = new Button(COPY_TO_CLIPBOARD);

        copyButton.setOnAction(actionEvent -> copyToClipboard());
        return copyButton;
    }

    // TODO : fix, it doesn't work
    private @NotNull Button initCreateNoteButton() {
        Button createNoteButton = new Button(CREATE_TEXT_NOTE);

        createNoteButton.setOnAction(actionEvent ->  {
            String appleScript = getAppleScriptCode();
            Runtime runtime = Runtime.getRuntime();
            String[] args = { "osascript", "-e", appleScript };
            try {
                Process process = runtime.exec(args);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return createNoteButton;
    }

    public static @NotNull Button initAddToPurchasesListButton(@NotNull Recipe recipe) {
        Button createAddToListButton = new Button(ADD_TO_LIST);

        createAddToListButton.setOnAction(actionEvent ->  {
            UserInterface.getPurchases().add(recipe);
        });

        return createAddToListButton;
    }

    private @NotNull Pane initControlMenu() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(initCopyToClipboardButton(), initCreateNoteButton(),
                                initAddToPurchasesListButton(recipe));

        return hBox;
    }

    private @NotNull String getAppleScriptCode() {
        if (recipe == null) {
            return "";
        }

        String recipeDescription = recipe.getRecipeDetailedDescription();

        String appleScript =
                "set noteHTMLText to \"<pre style=\\\"font-family:Helvetica,sans-serif;\\\">\" &" +
                recipeDescription + "& \"</pre>\"\n\n" +
                "tell application \"Notes\"\n" + "\tactivate\n" +
                "\tset thisAccountName to \"iCloud\"\n" +
                "\tset the noteTitle to \"" +
                recipe.getName() + "\"\n" +
                "\ttell account thisAccountName\n" +
                "\t\tmake new note at folder \"Notes\" with properties {name:noteTitle, body:noteHTMLText}\n" +
                "\tend tell\n" +
                "end tell";


        return appleScript;
    }

    private void copyToClipboard() {
        String stringToCopy = recipe.toString();

        StringSelection stringSelection = new StringSelection(stringToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
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