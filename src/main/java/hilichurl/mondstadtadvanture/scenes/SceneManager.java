package hilichurl.mondstadtadvanture.scenes;

import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Dialogue;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Plot;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SceneManager {
    private Stage stage;    //游戏窗口
    private final HashMap<GameScenes,String> scenePath =new HashMap<>();
    private final static SceneManager instance =new SceneManager();
    static Scene currentScene;
    static GameScenes currentGameScene;
    int i=1;    //文本点击次数

    //初始化的时候，将GameScenes和地址一一对应
    private SceneManager(){
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

        switchScene(GameScenes.MAIN_MENU);
        configStage(stage);
    }

    //切换到对应的场景
    public void switchScene(GameScenes gameScene) throws Exception {
        if(stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        FXMLLoader loader =new FXMLLoader(Program.class.getResource(scenePath.get(gameScene)));
        Pane root = (Pane)loader.load();
        Scene scene =new Scene(root);
        currentScene = scene;
        currentGameScene=gameScene;
        stage.setScene(scene);

        //获取加载好的背景图
        BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(gameScene);
        Background background = new Background(backImage);
        root.setBackground(background);

        //神秘的bug消除方式
        stage.setMaximized(false);
        stage.setMaximized(true);
    }

    //显示对话场景
    public void switchChatScene(Plot plot) throws Exception {
        if(stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        FXMLLoader loader =new FXMLLoader(Program.class.getResource("markdown-language/Chat.fxml"));
        Pane root = (Pane)loader.load();
        Scene scene =new Scene(root);
        stage.setScene(scene);

        //显示剧情文件中的文本信息
        ArrayList<Dialogue> dialogues = plot.getDialogue();
        Label name =(Label)root.lookup("#name");
        Label text =(Label)root.lookup("#text");
        Label narration =(Label)root.lookup("#narration");
        VBox center= (VBox)root.lookup("#narrationField");
        VBox bottom =(VBox)root.lookup("#dialogBox");
        showDialogue(dialogues.getFirst(),center,bottom,name,text,narration);
        i=1;
        scene.setOnMouseClicked(e->{
            if(i<dialogues.size()){
                showDialogue(dialogues.get(i),center,bottom,name,text,narration);
                i++;
            }
            else{
                endChatScene();
            }
        });

        //获取加载好的背景图
        BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(currentGameScene);
        Background background = new Background(backImage);
        root.setBackground(background);

        //神秘的bug消除方式
        stage.setMaximized(false);
        stage.setMaximized(true);
    }

    public void endChatScene(){
        stage.setScene(currentScene);
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

    private void showDialogue(Dialogue dialogue,VBox center,VBox bottom,Label name,Label text,Label narration){
        if(Objects.equals(dialogue.getName(), "旁白")){
            bottom.setVisible(false);
            bottom.setManaged(false);
            center.setVisible(true);
            narration.setText(dialogue.getText());
        }
        else{
            center.setVisible(false);
            bottom.setVisible(true);
            bottom.setManaged(true);
            name.setText(dialogue.getName());
            text.setText(dialogue.getText());
        }
    }

    public static SceneManager getInstance(){return instance;}
}