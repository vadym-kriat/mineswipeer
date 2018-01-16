package view.stages;

import controllers.ParamStageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ParamStage extends Stage {

    private FXMLLoader loader;
    private final Logger logger = Logger.getLogger(ParamStage.class);

    public ParamStage() {
        super();
        final String title = "Game parameters";
        init();
        setTitle(title);
        initModality(Modality.APPLICATION_MODAL);
    }

    private void init() {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/param_stage.fxml"));
            Parent root = loader.load();
            ParamStageController psc = loader.getController();
            psc.setStage(this);
            setScene(new Scene(root));
            setResizable(false);
        } catch (IOException e) {
            logger.error("Can't load fxml file.", e);
        }
    }

    public ParamStageController getController() {
        return loader.getController();
    }
}
