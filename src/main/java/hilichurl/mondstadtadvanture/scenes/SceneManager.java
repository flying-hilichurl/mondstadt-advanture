package hilichurl.mondstadtadvanture.scenes;

import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.DialogueType;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Dialogue;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class SceneManager {
    private Stage stage;    //游戏窗口
    private final HashMap<GameScenes,String> scenePath =new HashMap<>();    //场景的fxml文件路径
    private final HashMap<GameScenes,Scene> sortedScene =new HashMap<>();   //已加载的场景索引
    private final HashMap<Scene,GameScenes> reserveSortedScene =new HashMap<>();    //场景索引的反转哈希表
    private final static SceneManager instance =new SceneManager();
    static Scene currentScene;
    static Dialogue currentDialogue;
    static GameScenes currentGameScene;

    public HashMap<Scene,GameScenes> getReserveSortedScene(){return reserveSortedScene;}

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

        if(sortedScene.containsKey(gameScene)){
            stage.setScene(sortedScene.get(gameScene));
            currentGameScene=gameScene;
            currentScene=sortedScene.get(gameScene);
        }
        else {
            FXMLLoader loader = new FXMLLoader(Program.class.getResource(scenePath.get(gameScene)));
            Pane root = (Pane) loader.load();
            Scene scene = new Scene(root);
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

        //神秘的bug消除方式
        stage.setMaximized(false);
        stage.setMaximized(true);
    }

    //显示对话场景
    public void switchChatScene(ArrayList<Dialogue> dialogues) throws Exception {
        if(stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        FXMLLoader loader =new FXMLLoader(Program.class.getResource("markdown-language/Chat.fxml"));
        Pane root = (Pane)loader.load();
        Scene scene =new Scene(root);
        stage.setScene(scene);

        //获取对话界面的控件
        Label name =(Label)root.lookup("#name");
        Label text =(Label)root.lookup("#text");
        Label narration =(Label)root.lookup("#narration");
        VBox center= (VBox)root.lookup("#narrationField");
        VBox bottom =(VBox)root.lookup("#dialogBox");
        VBox options =(VBox)root.lookup("#options");
        currentDialogue= dialogues.getFirst();
        if(currentDialogue.getType()==DialogueType.NARRATION)
            showChatNode(center,bottom,options);
        else
            showChatNode(bottom,center,options);
        setDialogBox(currentDialogue,name,text,narration);

        //点击屏幕，到下一句话
        scene.setOnMouseClicked(e->{
            if(currentDialogue.getNext()!=-1){
                currentDialogue=dialogues.get(currentDialogue.getNext());
                //处理选项
                if(currentDialogue.getType()==DialogueType.OPTION){
                    showChatNode(options,center,bottom);
                    scene.setOnMouseClicked(null);
                    setOptions(dialogues,options,center,bottom,narration,name,text);
                }
                //处理旁白
                else if(currentDialogue.getType()==DialogueType.NARRATION){
                    showChatNode(center,options,bottom);
                    narration.setText(currentDialogue.getText());
                }
                //处理选项
                else{
                    showChatNode(bottom,center,options);
                    name.setText(currentDialogue.getName());
                    text.setText(currentDialogue.getText());
                }
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

    //控制显示的部分
    private void showChatNode(VBox show,VBox hide1, VBox hide2){
        hide1.setVisible(false);
        hide1.setManaged(false);
        hide2.setVisible(false);
        hide2.setManaged(false);
        show.setVisible(true);
        show.setManaged(true);
    }

    //显示文本对话框
    private void setDialogBox(Dialogue dialogue, Label name, Label text, Label narration){
        if(dialogue.getType()==DialogueType.NARRATION){
            narration.setText(dialogue.getText());
        }
        else if(dialogue.getType()==DialogueType.DIALOGUE){
            name.setText(dialogue.getName());
            text.setText(dialogue.getText());
        }
    }

    //处理剧情中的选型部分
    private void setOptions(ArrayList<Dialogue> dialogues,VBox options,VBox center,VBox bottom,Label narration,Label name,Label text){
        //创建剧情文件，创建按钮
        Button newButton = new Button(currentDialogue.getText());
        options.getChildren().add(newButton);
        newButton.setOnAction(event -> {
            currentDialogue=dialogues.get(currentDialogue.getNext());
            if(currentDialogue.getType()==DialogueType.NARRATION)
                showChatNode(center,bottom,options);
            else
                showChatNode(bottom,center,options);
            setDialogBox(currentDialogue,name,text,narration);
        });

        //如果会有选项为显示，则递归
        if(dialogues.get(currentDialogue.getId()+1).getType()==DialogueType.OPTION){
            currentDialogue=dialogues.get(currentDialogue.getId()+1);
            setOptions(dialogues,options,center,bottom,narration,name,text);
        }
    }

    public static SceneManager getInstance(){return instance;}
}