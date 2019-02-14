package client.gui.menucontrollers;

import client.gui.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsMenuController extends VBox implements MenuController {
    private Stage stage;
    private Settings settings;

    @FXML
    private Slider soundVolumeSlider;

    @FXML
    private Button soundOnButton;

    @FXML
    private Button soundOffButton;

    @FXML
    private Slider musicVolumeSlider;

    @FXML
    private Button musicOnButton;

    @FXML
    private Button musicOffButton;

    @FXML
    private Button displayWindowedButton;

    @FXML
    private Button displayFullscreenButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<?> resolutionComboBox;

    @FXML
    private Button applyButton;

    public SettingsMenuController(Stage stage, Settings settings) {
        this.stage = stage;
        this.settings = settings;

        // Load FXML and set appropriate methods
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/gui/fxml/settings_menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        // Set up window according to settings object
        soundVolumeSlider.setValue(settings.getSoundVolume());
        musicVolumeSlider.setValue(settings.getMusicVolume());

        if (settings.isMusicMute()) {
            musicOffButton.setDefaultButton(true);
            musicOnButton.setDefaultButton(false);
        } else {
            musicOffButton.setDefaultButton(false);
            musicOnButton.setDefaultButton(true);
        }

        if (settings.isSoundMute()) {
            soundOffButton.setDefaultButton(true);
            soundOnButton.setDefaultButton(false);
        } else {
            soundOffButton.setDefaultButton(false);
            soundOnButton.setDefaultButton(true);
        }

        if (settings.isFullScreen()) {
            displayFullscreenButton.setDefaultButton(true);
            displayWindowedButton.setDefaultButton(false);
        } else {
            displayFullscreenButton.setDefaultButton(false);
            displayWindowedButton.setDefaultButton(true);
        }

        // Disable apply button
        applyButton.setDisable(true);

        // Populate resolution box with resolutions
    }

    @Override
    public void show() {
        this.stage.getScene().setRoot(this);
    }

    @FXML
    void backButtonPress(ActionEvent event) {
        // Switch to main menu and clear this object
        (new MainMenuController(stage, settings)).show();
        this.getChildren().clear();
    }

    @FXML
    void displayFullscreenButtonPress(ActionEvent event) {
        settings.setFullScreen(true);
        displayFullscreenButton.setDefaultButton(true);
        displayWindowedButton.setDefaultButton(false);
        applyButton.setDisable(false);
    }

    @FXML
    void displayWindowedButtonPress(ActionEvent event) {
        settings.setFullScreen(false);
        displayFullscreenButton.setDefaultButton(false);
        displayWindowedButton.setDefaultButton(true);
        applyButton.setDisable(false);
    }

    @FXML
    void musicOffButtonPress(ActionEvent event) {
        settings.setMusicMute(true);
        musicOffButton.setDefaultButton(true);
        musicOnButton.setDefaultButton(false);
        applyButton.setDisable(false);
    }

    @FXML
    void musicOnButtonPress(ActionEvent event) {
        settings.setMusicMute(false);
        musicOffButton.setDefaultButton(false);
        musicOnButton.setDefaultButton(true);
        applyButton.setDisable(false);
    }

    @FXML
    void soundOffButtonPress(ActionEvent event) {
        settings.setSoundMute(true);
        soundOffButton.setDefaultButton(true);
        soundOnButton.setDefaultButton(false);
        applyButton.setDisable(false);
    }

    @FXML
    void soundOnButtonPress(ActionEvent event) {
        settings.setSoundMute(false);
        soundOffButton.setDefaultButton(false);
        soundOnButton.setDefaultButton(true);
        applyButton.setDisable(false);
    }

    @FXML
    void musicVolumeSliderDragged(MouseEvent event) {
        settings.setMusicVolume((int) musicVolumeSlider.getValue());
        applyButton.setDisable(false);
    }

    @FXML
    void soundVolumeSliderDragged(MouseEvent event) {
        settings.setSoundVolume((int) soundVolumeSlider.getValue());
        applyButton.setDisable(false);
    }

    @FXML
    void resolutionComboBoxChanged(ActionEvent event) {

    }

    @FXML
    void applyButtonPress(ActionEvent event) {
        // Apply graphics settings
    }
}
