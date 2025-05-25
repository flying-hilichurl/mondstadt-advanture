package hilichurl.mondstadtadvanture.scenes;

import hilichurl.mondstadtadvanture.MusicManager;
import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private VBox menu;      //菜单
    private Pane overlay;   //遮罩
    static Scene currentScene;
    static GameScenes currentGameScene;

    public HashMap<Scene,GameScenes> getReserveSortedScene(){return reserveSortedScene;}

    public static GameSceneManager getInstance(){return instance;}

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

        //切换至第一个场景
        switchGameScene(GameScenes.MAIN_MENU);
        configStage(stage);

        //创建菜单面板和遮罩层
        menu=createMenu();
        overlay=createOverlay();
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

            if(currentGameScene!=GameScenes.MAIN_MENU){
                root.getChildren().add(overlay);
                root.getChildren().add(menu);
            }

            //获取加载好的背景图
            BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(gameScene);
            Background background = new Background(backImage);
            root.setBackground(background);
        }

        currentScene.setOnKeyPressed(event->{
            //如果按下Esc键，呼出菜单
            if(event.getCode()== KeyCode.ESCAPE){
                if(!menu.isVisible()){
                    overlay.setVisible(true);
                    menu.setVisible(true);
                }
                else {
                    overlay.setVisible(false);
                    menu.setVisible(false);
                }
            }
        });

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

    //创建小菜单
    private VBox createMenu(){
        //音乐开关
        CheckBox musicBox=new CheckBox("音乐");
        musicBox.setSelected(true);
        musicBox.selectedProperty().addListener((observable,oldValue,newValue)->{
            if(newValue)
                MusicManager.getInstance().continuePlay();
            else
                MusicManager.getInstance().pausePlay();
        });

        //音量条
        Slider musicVolume=new Slider(0,100,50);
        musicVolume.setMaxWidth(150);
        musicVolume.valueProperty().addListener((obs,oldValue,newValue)->{
            MusicManager.getInstance().setMusicVolume(newValue.floatValue()/100);
        });

        //退出按钮
        Button exitButton=new Button("回到主菜单");
        exitButton.setOnAction(event -> {
            try {
                switchGameScene(GameScenes.MAIN_MENU);
                overlay.setVisible(false);
                menu.setVisible(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox menu=new VBox(20);
        menu.setMaxWidth(400);
        menu.setMaxHeight(300);
        menu.setBackground(new Background(new BackgroundFill(Color.web("#FFF"), new CornerRadii(20), null)));
        menu.setBorder(new Border(new BorderStroke(Color.web("#3498db"), BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2))));
        menu.setPadding(new Insets(50,20,50,20));
        menu.getChildren().addAll(musicBox,new Label("音量："),musicVolume,exitButton);
        menu.setVisible(false);

        return menu;
    }

    private Pane createOverlay(){
        Pane overlay=new Pane();
        overlay.setBackground(new Background(new BackgroundFill(new Color(0,0,0,0.4), null, null)));
        overlay.setVisible(false);

        return overlay;
    }
}