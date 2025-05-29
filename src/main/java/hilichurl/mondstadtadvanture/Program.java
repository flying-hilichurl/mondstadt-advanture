package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import hilichurl.mondstadtadvanture.scenes.GameSceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {
    @Override
    public void start(Stage stage) {
        PreLoader.getInstance().setOnImageLoaded(e-> {
            try {
                GameSceneManager.getInstance().init(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        MusicManager.getInstance().play();
        PreLoader.getInstance().preLoadOneImage(GameScenes.MAIN_MENU);
    }

    @Override
    public void stop() throws Exception {
        MusicManager.getInstance().stop();
        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }
}