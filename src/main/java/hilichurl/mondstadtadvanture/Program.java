package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.scenes.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.getInstance().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}