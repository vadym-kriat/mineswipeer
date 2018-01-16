package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Minefield;
import model.util.Difficult;
import model.util.Param;
import model.util.Regex;

public class ParamStageController {

    @FXML
    private VBox vbCustomParam;
    @FXML
    private TextField tfHeight;
    @FXML
    private TextField tfWidth;
    @FXML
    private TextField tfNumOfMine;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;
    @FXML
    private ToggleGroup tgLevel;
    private Param param;
    private Stage stage;

    public void initialize() {
        initListeners();
        setDefVal();
        vbCustomParam.setDisable(true);
    }

    private void initListeners() {
        tgLevel.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Toggle selectedToggle = tgLevel.getSelectedToggle();
            if (selectedToggle != null) {
                if (selectedToggle.getUserData().equals("CUSTOM")) {
                    vbCustomParam.setDisable(false);
                } else {
                    vbCustomParam.setDisable(true);
                }
            }
        });
        tfHeight.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!tfHeight.getText().matches(Regex.SIZE_BOUND)) {
                    tfHeight.setText(String.valueOf(Minefield.MIN_SIZE));
                }
            }
        });
        tfWidth.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!tfWidth.getText().matches(Regex.SIZE_BOUND)) {
                    tfWidth.setText(String.valueOf(Minefield.MIN_SIZE));
                }
            }
        });
        tfNumOfMine.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                final int height = Integer.parseInt(tfHeight.getText());
                final int width = Integer.parseInt(tfWidth.getText());
                final int currValue = Integer.parseInt(tfNumOfMine.getText());
                if (height * width < currValue / 2) {
                    tfNumOfMine.setText(String.valueOf(Minefield.MIN_NUM_OF_MINES));
                }
            }
        });
        btnCancel.setOnMouseClicked(event -> {
            param = null;
            stage.hide();
        });
        btnOK.setOnMouseClicked(event -> {
            Toggle selectedToggle = tgLevel.getSelectedToggle();
            if (selectedToggle != null) {
                switch (selectedToggle.getUserData().toString()) {
                    case "EASY":
                        param = Difficult.EASY.getValue();
                        break;
                    case "NORMAL":
                        param = Difficult.NORMAL.getValue();
                        break;
                    case "HARD":
                        param = Difficult.HARD.getValue();
                        break;
                    case "CUSTOM":
                        param = buildParam();
                        break;
                }
                stage.hide();
            }
        });
    }

    private void setDefVal() {
        tfHeight.setText(String.valueOf(Minefield.MIN_SIZE));
        tfWidth.setText(String.valueOf(Minefield.MIN_SIZE));
        tfNumOfMine.setText(String.valueOf(Minefield.MIN_NUM_OF_MINES));
    }

    private Param buildParam() {
        final int mapHeight = Integer.parseInt(tfHeight.getText());
        final int mapWidth = Integer.parseInt(tfWidth.getText());
        final int numOfMine = Integer.parseInt(tfNumOfMine.getText());
        return new Param(numOfMine, mapHeight, mapWidth);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Param getParam() {
        return param;
    }
}
