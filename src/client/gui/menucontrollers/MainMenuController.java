package client.gui.menucontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController extends VBox implements MenuController {
    private Stage stage;

    @FXML
    private Button playButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button quitButton;

    public MainMenuController(Stage stage) {
        this.stage = stage;

        // Load FXML and set appropriate methods
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/gui/fxml/main_menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void show() {
        if (stage.getScene() == null) {
            MenuController.setRootToStage(stage, this);
        } else {
            this.stage.getScene().setRoot(this);
        }
    }

    @FXML
    void playButtonPress(ActionEvent event) {
        // Switch to play menu and clear this object
        (new PlayMenuController(stage)).show();
        this.getChildren().clear();
    }

    @FXML
    void settingsButtonPress(ActionEvent event) {
        // Switch to settings menu and clear this object
        (new SettingsMenuController(stage)).show();
        this.getChildren().clear();
    }

    @FXML
    void helpButtonPress(ActionEvent event) {
        // Switch to help menu and clear this object
        (new HelpMenuController(stage)).show();
        this.getChildren().clear();
    }

    @FXML
    void quitButtonPress(ActionEvent event) {
        stage.close();
    }
}