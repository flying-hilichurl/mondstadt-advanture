package hilichurl.mondstadtadvanture.scenes;

import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Objects;

//负责在不同地图的界面之间切换等逻辑
public class GameSceneManager extends SceneManager{
    Stage stage;    //游戏窗口
    private final HashMap<GameScenes,String> scenePath =new HashMap<>();    //场景的fxml文件路径
    private final HashMap<GameScenes,Scene> sortedScene =new HashMap<>();   //已加载的场景索引
    private final HashMap<Scene,GameScenes> reserveSortedScene =new HashMap<>();    //场景索引的反转哈希表
    private final static GameSceneManager instance =new GameSceneManager();
    static Scene currentScene;
    static GameScenes currentGameScene;

    public HashMap<Scene,GameScenes> getReserveSortedScene(){return reserveSortedScene;}

    //初始化的时候，将GameScenes和地址一一对应
    private GameSceneManager(){
        scenePath.put(GameScenes.MAIN_MENU,"markdown-language/StartInterface.fxml");
        scenePath.put(GameScenes.MAP,"markdown-language/WorldMap.fxml");
        scenePath.put(GameScenes.ADVENTURE_GUIDE,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.KNIGHTLY_ORDER,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.SQUARE,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.ANGEL_BOUNTY,"markdown-language/Spot.fxml");
        scenePath.put(GameScenes.CATHEDRAL,"markdown-language/Spot.fxml");
    }

    //获取窗口
    public void init(Stage stage) throws Exception {
        this.stage=stage;

        switchGameScene(GameScenes.MAIN_MENU);
        configStage(stage);
    }

    //切换到对应的场景
    public void switchGameScene(GameScenes gameScene) throws Exception {
        if(stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        if(sortedScene.containsKey(gameScene)){
            stage.setScene(sortedScene.get(gameScene));
            currentGameScene=gameScene;
            currentScene=sortedScene.get(gameScene);
        }
        else {
            Scene scene = createScene(Objects.requireNonNull(Program.class.getResource(scenePath.get(gameScene))));
            Pane root=(Pane) scene.getRoot();
            currentScene = scene;
            currentGameScene = gameScene;
            sortedScene.put(gameScene, scene);
            reserveSortedScene.put(scene,gameScene);
            stage.setScene(scene);

            //获取加载好的背景图
            BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(gameScene);
            Background background = new Background(backImage);
            root.setBackground(background);
        }

        //刷新
        refresh(GameSceneManager.getInstance().stage);
    }

    //配置窗口大小
    private void configStage(Stage stage){
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        //屏幕宽度
        double screenWidth = (int) visualBounds.getWidth();
        //屏幕高度
        double screenHeight = (int) visualBounds.getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setX(0);
        stage.setY(0);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.show();
    }

    public static GameSceneManager getInstance(){return instance;}
}