package hilichurl.mondstadtadvanture.scenes;

import com.fasterxml.jackson.databind.JsonNode;
import hilichurl.mondstadtadvanture.PlotManager;
import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.DialogueType;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.jsonpojo.JsonReader;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Dialogue;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//因与聊天界面相关的逻辑较多，于是单独用此类负责聊天界面的界面相关逻辑
public class ChatSceneManager extends SceneManager{
    private final static ChatSceneManager instance=new ChatSceneManager();
    private Label name;
    private Label text;
    private VBox center;
    private Label narration;
    private VBox bottom;
    private VBox options;
    private Background background;
    private ArrayList<Dialogue> currentDialogues;
    private Scene chatScene;
    private Dialogue currentDialogue;

    public static ChatSceneManager getInstance() {return instance;}

    private ChatSceneManager(){}

    //显示对话场景
    public void switchChatScene(ArrayList<Dialogue> dialogues) throws Exception {
        //变更到聊天界面
        chatScene=createScene(Program.class.getResource("markdown-language/Chat.fxml"));
        GameSceneManager.getInstance().stage.setScene(chatScene);

        //根据不同类型显示不同对话
        currentDialogue = dialogues.getFirst();
        if(currentDialogue.getType()== DialogueType.NARRATION)
            setNarration(currentDialogue);
        else if(currentDialogue.getType()==DialogueType.DIALOGUE)
            setDialog(currentDialogue);
        else
            setOptions(dialogues);
        this.currentDialogues=dialogues;

        //点击屏幕，到下一句话
        chatScene.setOnMouseClicked(this::plotClickedHandler);

        //刷新
        refresh(GameSceneManager.getInstance().stage);
    }

    //通过选项界面显示可以进行的对话
    public void switchChatScene(List<JsonNode> nodes, GameScenes gameScenes) throws Exception {
        //变更到聊天界面
        chatScene=createScene(Program.class.getResource("markdown-language/Chat.fxml"));
        GameSceneManager.getInstance().stage.setScene(chatScene);

        //显示选项
        showChatNode(options);

        options.getChildren().clear();
        nodes.forEach(action->{
            if(PlotManager.getInstance().checkChatTarget(action.get("condition").asText())) {
                String text = action.get("text").asText();
                Button button = new Button(text);
                options.getChildren().add(button);
                chatScene.setOnMouseClicked(null);

                //选项触发对应剧情
                button.setOnAction(event->{
                    try {
                        if(action.get("mainPlot").asBoolean()) {
                            PlotManager.getInstance().setCurrentPlot(action.get("plot").asText());
                            PlotManager.getInstance().play();
                        }
                        else{
                        ArrayList<Dialogue> dialogues = JsonReader.getInstance().getChatConnect(action.get("plot").asText(),gameScenes);
                        switchChatScene(dialogues);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

        //刷新
        refresh(GameSceneManager.getInstance().stage);
    }

    //退出聊天界面
    public void endChatScene() throws Exception {
        GameSceneManager.getInstance().switchGameScene(GameSceneManager.currentGameScene);
    }

    //控制显示的部分
    private void showChatNode(VBox show){
        center.setVisible(false);
        center.setManaged(false);
        options.setVisible(false);
        options.setManaged(false);
        bottom.setVisible(false);
        bottom.setManaged(false);
        show.setVisible(true);
        show.setManaged(true);
    }

    //显示文本对话框
    private void setDialog(Dialogue dialogue){
        showChatNode(bottom);
        name.setText(dialogue.getName());
        text.setText(dialogue.getText());
    }

    //设置旁白显示
    private void setNarration(Dialogue dialogue){
        showChatNode(center);
        narration.setText(dialogue.getText());
    }

    //处理剧情中的选项部分
    private void setOptions(ArrayList<Dialogue> dialogues){
        showChatNode(options);

        //创建剧情文件，创建按钮
        Button newButton = new Button(currentDialogue.getText());
        options.getChildren().add(newButton);
        int id = currentDialogue.getNext();
        newButton.setOnAction(event -> {
            currentDialogue=dialogues.get(id);
            if(currentDialogue.getType()==DialogueType.NARRATION)
                setNarration(currentDialogue);
            else
                setDialog(currentDialogue);

            chatScene.setOnMouseClicked(this::plotClickedHandler);
        });

        //如果还有选项要显示，则递归
        if(dialogues.get(currentDialogue.getId()+1).getType()==DialogueType.OPTION){
            currentDialogue=dialogues.get(currentDialogue.getId()+1);
            setOptions(dialogues);
        }
    }

    private void plotClickedHandler(MouseEvent event) {
        if(currentDialogue.getNext()!=-1){
            currentDialogue=currentDialogues.get(currentDialogue.getNext());
            //处理选项
            if(currentDialogue.getType()==DialogueType.OPTION){
                options.getChildren().clear();
                chatScene.setOnMouseClicked(null);
                setOptions(currentDialogues);
            }
            //处理旁白
            else if(currentDialogue.getType()==DialogueType.NARRATION)
                setNarration(currentDialogue);
            //处理选项
            else
                setDialog(currentDialogue);
        }
        else{
            try{
                endChatScene();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    Scene createScene(URL path) throws Exception {
        if(GameSceneManager.getInstance().stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        Scene scene;
        if(chatScene!=null){
            scene=chatScene;

            //获取加载好的背景图
            BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(GameSceneManager.currentGameScene);
            background = new Background(backImage);
        }
        else {
            //创建对话界面
            scene = super.createScene(path);
            Pane root =(Pane)scene.getRoot();
            GameSceneManager.getInstance().stage.setScene(chatScene);

            //获取对话界面的控件
            name = (Label) root.lookup("#name");
            text = (Label) root.lookup("#text");
            narration = (Label) root.lookup("#narration");
            center = (VBox) root.lookup("#narrationField");
            bottom = (VBox) root.lookup("#dialogBox");
            options = (VBox) root.lookup("#options");

            //获取加载好的背景图
            BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(GameSceneManager.currentGameScene);
            background = new Background(backImage);
            root.setBackground(background);
        }

        return scene;
    }
}