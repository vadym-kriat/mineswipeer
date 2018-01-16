package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Game;
import model.util.Difficult;
import model.util.ImageLoader;
import model.util.Param;
import model.util.Styles;
import model.util.image.Scale;
import view.elements.MinefieldGUI;
import view.elements.StatusBar;
import view.stages.ParamStage;

public class MainStageController {

    public VBox container;
    @FXML
    private Menu mGame;
    @FXML
    private Menu mZoom;
    @FXML
    private ScrollPane scrollPane;

    private MinefieldGUI minefieldGUI;
    private StatusBar statusBar;
    private Stage rootStage;
    private ParamStage paramStage;

    private final static int MAX_HEIGHT_SCENE = 600;
    private final static int MAX_WIDTH_SCENE = 800;
    private final static int PADDING = 7;

    public void initialize() {
        initMenuBar();
        paramStage = new ParamStage();
        startNewGame(Difficult.EASY.getValue());
        scrollPane.setStyle(Styles.BGC_TRANSPARENT);
    }

    private void initMenuBar() {
        initGameMenu();
        initZoomMenu();
    }

    private void initGameMenu() {
        MenuItem miNewGame = new MenuItem("New game...");
        MenuItem miClose = new MenuItem("Close");

        miNewGame.setOnAction(event -> {
            paramStage.showAndWait();
            final Param param = paramStage.getController().getParam();
            if (param != null) {
                startNewGame(param);
            }
        });
        miClose.setOnAction(event -> Platform.exit());
        mGame.getItems().addAll(miNewGame, miClose);
    }

    private void initZoomMenu() {
        ToggleGroup tg = new ToggleGroup();
        RadioMenuItem rmiSM = new RadioMenuItem("50%");
        RadioMenuItem rmiMD = new RadioMenuItem("100%");
        RadioMenuItem rmiLG = new RadioMenuItem("150%");
        rmiLG.setSelected(true);
        RadioMenuItem rmiXL = new RadioMenuItem("200%");
        tg.getToggles().addAll(rmiSM, rmiMD, rmiLG, rmiXL);

        rmiSM.setOnAction(event -> scaleTo(Scale.CELL_SM_LEN));
        rmiMD.setOnAction(event -> scaleTo(Scale.CELL_MD_LEN));
        rmiLG.setOnAction(event -> scaleTo(Scale.CELL_LG_LEN));
        rmiXL.setOnAction(event -> scaleTo(Scale.CELL_XL_LEN));
        mZoom.getItems().addAll(rmiSM, rmiMD, rmiLG, rmiXL);
    }

    private void scaleTo(Scale scale) {
        ImageLoader.getInstance().scaleTo(scale);
        minefieldGUI.rescale();
        statusBar.setWidth(minefieldGUI.getWidth());
        statusBar.rescale();
        if (minefieldGUI.getHeight() + statusBar.getHeight() < MAX_HEIGHT_SCENE
                && minefieldGUI.getWidth() < MAX_WIDTH_SCENE) {
            rootStage.sizeToScene();
        } else {
            rootStage.setHeight(MAX_HEIGHT_SCENE);
            rootStage.setWidth(MAX_WIDTH_SCENE);
        }
        rootStage.centerOnScreen();
    }

    private void startNewGame(Param param) {
        final Game game = new Game(param);
        minefieldGUI = new MinefieldGUI(game);
        statusBar = new StatusBar(minefieldGUI.getWidth());
        statusBar.setWidth(minefieldGUI.getWidth());
        game.setGameActionListener(minefieldGUI);
        game.setTimeListener(statusBar);
        game.setFlagListener(statusBar);
        minefieldGUI.setOnClickListener(game);

        VBox vBox = new VBox();
        vBox.setSpacing(PADDING);
        vBox.setPadding(new Insets(PADDING));
        vBox.setStyle(Styles.BGC_LIGHTGRAY);
        vBox.getChildren().addAll(statusBar, minefieldGUI);
        scrollPane.setContent(vBox);
        if (rootStage != null) {
            scaleTo(Scale.CELL_LG_LEN);
        }
    }

    public void setRootStage(Stage rootStage) {
        this.rootStage = rootStage;
        this.rootStage.sizeToScene();
    }
}
