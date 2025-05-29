package hilichurl.mondstadtadvanture.fxmlcontroller;

import com.fasterxml.jackson.databind.JsonNode;
import hilichurl.mondstadtadvanture.PlotManager;
import hilichurl.mondstadtadvanture.jsonpojo.JsonReader;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import hilichurl.mondstadtadvanture.scenes.GameSceneManager;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class StartInterfaceController {
    @FXML
    public void onStart(ActionEvent event) {
        //场景加载完毕时，进入游戏场景
        PreLoader.getInstance().setOnImageLoaded(e-> {
            try {
                PlotManager.getInstance().startPlot();
                GameSceneManager.getInstance().switchGameScene(GameScenes.MAP);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //加载图片资源
        PreLoader.getInstance().preLoadAllImage();

        //监听scene改变事件
        listenSceneChanged(event);

        //显示加载界面
        showLoading(event);
    }



    @FXML
    public void onSetting(ActionEvent event) {

    }

    @FXML
    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void onContinuous(ActionEvent event) throws IOException {
        JsonNode data= JsonReader.getInstance().readPlayerData();
        PlotManager.getInstance().setCurrentPlot(data.get("plot").asText());
        PlotManager.getInstance().setCurrentGroup(data.get("group").asText());
        GameScenes gameScene=GameScenes.valueOf(data.get("scene").asText());

        //场景加载完毕时，进入游戏场景
        PreLoader.getInstance().setOnImageLoaded(e-> {
            try {
                GameSceneManager.getInstance().switchGameScene(gameScene);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //加载图片资源
        PreLoader.getInstance().preLoadAllImage();

        //监听scene改变事件
        listenSceneChanged(event);

        //显示加载界面
        showLoading(event);
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

    private void listenSceneChanged(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                try {
                    PlotManager.getInstance().tryTargetPlot(newScene);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}