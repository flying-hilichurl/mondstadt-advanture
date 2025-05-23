package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.preload.PreLoader;
import hilichurl.mondstadtadvanture.scenes.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {
    @Override
    public void start(Stage stage) {
        PreLoader.getInstance().setOnImageLoaded(e-> {
            try {
                SceneManager.getInstance().init(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        PreLoader.getInstance().preLoadImage(true);
    }

    public static void main(String[] args) {
        launch();
    }
}