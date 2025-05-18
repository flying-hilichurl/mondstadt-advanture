package hilichurl.mondstadtadvanture;

import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.instance.init(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}