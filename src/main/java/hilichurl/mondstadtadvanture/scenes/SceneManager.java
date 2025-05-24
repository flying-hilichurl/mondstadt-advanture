package hilichurl.mondstadtadvanture.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public abstract class SceneManager {
    //创建一个场景
    Scene createScene(URL path) throws Exception {
        FXMLLoader loader = new FXMLLoader(path);
        Pane root = (Pane) loader.load();

        return new Scene(root);
    }

    //介于一个我找不出来的bug，于是使用了这种实在不优雅的方式刷新界面。黑猫白猫，能抓耗子就是好猫
    void refresh(Stage stage){
        stage.setMaximized(false);
        stage.setMaximized(true);
    }
}
