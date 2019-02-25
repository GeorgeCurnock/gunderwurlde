package client.gui.menucontrollers;

import client.gui.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlsSettingsController extends VBox implements MenuController {
    private Stage stage;
    private Settings settings;

    @FXML
    private Button upButton;

    @FXML
    private Button interactButton;

    @FXML
    private Button dropButton;

    @FXML
    private Button reloadButton;

    @FXML
    private Button rightButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button downButton;

    @FXML
    private Button item3Button;

    @FXML
    private Button item2Button;

    @FXML
    private Button item1Button;

    @FXML
    private Button escapeButton;

    @FXML
    private Button backButton;

    @FXML
    private Button defaultsButton;

    public ControlsSettingsController(Stage stage, Settings settings) {
        this.stage = stage;
        this.settings = settings;

        // Load FXML and set appropriate methods
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/gui/fxml/controls_menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        // Set text of all keyboard buttons to their current mappings
        upButton.setText(settings.getKey("up"));
        downButton.setText(settings.getKey("down"));
        leftButton.setText(settings.getKey("left"));
        rightButton.setText(settings.getKey("right"));
        interactButton.setText(settings.getKey("interact"));
        dropButton.setText(settings.getKey("drop"));
        reloadButton.setText(settings.getKey("reload"));
        item1Button.setText(settings.getKey("item1"));
        item2Button.setText(settings.getKey("item2"));
        item3Button.setText(settings.getKey("item3"));
        escapeButton.setText(settings.getKey("esc"));
    }

    @Override
    public void show() {
        this.stage.getScene().setRoot(this);
    }

    @FXML
    void backButtonPress(ActionEvent event) {
        // Save settings to file - not really necessary since the settings menu would do this too, but it's a nice
        // quality of life change in case the user quits the game without going back to the settings menu
        settings.saveToDisk();

        // Switch to settings menu and clear this object
        (new SettingsMenuController(stage, settings)).show();
        this.getChildren().clear();
    }

    @FXML
    void downButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        downButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            downButton.setText(pressed);
            settings.changeKey("down", pressed);
        });
    }

    @FXML
    void dropButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        dropButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            dropButton.setText(pressed);
            settings.changeKey("drop", pressed);
        });
    }

    @FXML
    void escapeButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        escapeButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            escapeButton.setText(pressed);
            settings.changeKey("esc", pressed);
        });
    }

    @FXML
    void interactButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        interactButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            interactButton.setText(pressed);
            settings.changeKey("interact", pressed);
        });
    }

    @FXML
    void item1ButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        item1Button.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            item1Button.setText(pressed);
            settings.changeKey("item1", pressed);
        });
    }

    @FXML
    void item2ButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        item2Button.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            item2Button.setText(pressed);
            settings.changeKey("item2", pressed);
        });
    }

    @FXML
    void item3ButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        item3Button.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            item3Button.setText(pressed);
            settings.changeKey("item3", pressed);
        });
    }

    @FXML
    void leftButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        leftButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            leftButton.setText(pressed);
            settings.changeKey("left", pressed);
        });
    }

    @FXML
    void reloadButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        reloadButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            reloadButton.setText(pressed);
            settings.changeKey("reload", pressed);
        });
    }

    @FXML
    void rightButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        rightButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            rightButton.setText(pressed);
            settings.changeKey("right", pressed);
        });
    }

    @FXML
    void upButtonPress(ActionEvent event) {
        // Change text to tell user how to reassign button
        upButton.setText("<Press Button>");

        // Wait for input from user and set label and modify settings accordingly
        stage.getScene().setOnKeyPressed(event1 -> {
            String pressed = event1.getCode().toString();
            upButton.setText(pressed);
            settings.changeKey("up", pressed);
        });
    }

    @FXML
    void defaultsButtonPress(ActionEvent event) {
        // Set defaults in settings
        settings.mapDefaultKeys();

        // Save settings
        settings.saveToDisk();

        // Redraw scene to show changes
        (new ControlsSettingsController(stage, settings)).show();
        this.getChildren().clear();
    }
}
