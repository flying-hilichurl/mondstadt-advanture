package hilichurl.mondstadtadvanture.fxmlcontroller;

import hilichurl.mondstadtadvanture.SceneManager;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartInterface {
    @FXML
    public void onStart(ActionEvent event) throws Exception {
        SceneManager.instance.switchScene(GameScenes.Map);
    }

    @FXML
    public void onSetting(ActionEvent event) {

    }

    @FXML
    public void onExit(ActionEvent actionEvent) {
    }

    @FXML
    public void onContinous(ActionEvent actionEvent) {

    }
}