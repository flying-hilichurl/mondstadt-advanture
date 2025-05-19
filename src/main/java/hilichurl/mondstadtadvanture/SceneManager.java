package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.enums.GameScenes;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

public class SceneManager {
    private Stage stage;    //游戏窗口
    private double screenWidth;     //屏幕宽度
    private double screenHeight;    //屏幕高度
    private final HashMap<GameScenes,String> scenePath =new HashMap<>();
    public static SceneManager instance =new SceneManager();

    //初始化的时候，将GameScenes和地址一一对应
    private SceneManager(){
        scenePath.put(GameScenes.MainMenu,"markdown-language/StartInterface.fxml");
        scenePath.put(GameScenes.Map,"markdown-language/WorldMap.fxml");
        scenePath.put(GameScenes.AdventureGuild,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.KnightlyOrder,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.Square,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.AngelBounty,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.Cathedral,"markdown-language/Spot.fxml");
    }

    //获取窗口
    public void init(Stage stage) throws Exception {
        this.stage=stage;
        switchScene(GameScenes.MainMenu);
        configStage(stage);
    }

    //切换到对应的场景
    public void switchScene(GameScenes gameScene) throws Exception {
        if(stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        FXMLLoader loader =new FXMLLoader(Program.class.getResource(scenePath.get(gameScene)));
        Scene scene =new Scene(loader.load(),screenWidth,screenHeight);
        stage.setScene(scene);
    }

    //配置窗口大小
    private void configStage(Stage stage){
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = (int) visualBounds.getWidth();
        screenHeight = (int) visualBounds.getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setX(0);
        stage.setY(0);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
