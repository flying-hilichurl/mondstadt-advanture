package hilichurl.mondstadtadvanture.fxmlcontroller;

import hilichurl.mondstadtadvanture.preload.PreLoader;
import hilichurl.mondstadtadvanture.scenes.SceneManager;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartInterfaceController {
    @FXML
    public void onStart(ActionEvent event) {
        //加载图片资源
        PreLoader.getInstance().setOnLoaded(e-> {
            try {
                SceneManager.getInstance().switchScene(GameScenes.MAP);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        PreLoader.getInstance().preLoad();

        //显示加载界面
        showLoading(event);
    }

    @FXML
    public void onSetting(ActionEvent event) {

    }

    @FXML
    public void onExit(ActionEvent actionEvent) {
    }

    @FXML
    public void onContinuous(ActionEvent actionEvent) {

    }

    private void showLoading(ActionEvent event){
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);

        //设置显示文字和字体大小
        Label label = new Label("加载中...");
        root.getChildren().add(label);
        label.setFont(new Font(30));

        //获取窗口，应用样式
        Node source =(Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        stage.setScene(scene);

        //实践中发现的神奇界面刷新方式（雾）
        stage.setMaximized(false);
        stage.setMaximized(true);
    }
}