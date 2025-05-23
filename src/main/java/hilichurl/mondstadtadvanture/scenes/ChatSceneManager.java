package hilichurl.mondstadtadvanture.scenes;

import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.DialogueType;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Dialogue;
import hilichurl.mondstadtadvanture.preload.PreLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChatSceneManager {
    private final static ChatSceneManager instance=new ChatSceneManager();
    private Label name;
    private Label text;
    private VBox center;
    private Label narration;
    private VBox bottom;
    private VBox options;
    private ArrayList<Dialogue> currentDialogues;
    private Scene chatScene;
    private Dialogue currentDialogue;

    public static ChatSceneManager getInstance() {return instance;}

    private ChatSceneManager(){}

    //显示对话场景
    public void switchChatScene(ArrayList<Dialogue> dialogues) throws Exception {
        if(SceneManager.getInstance().stage==null){
            throw new Exception("无法获取到Stage窗口");
        }

        if(chatScene!=null){
            SceneManager.getInstance().stage.setScene(chatScene);
            Pane root=(Pane)chatScene.getRoot();

            //获取加载好的背景图
            BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(SceneManager.currentGameScene);
            Background background = new Background(backImage);
            root.setBackground(background);
        }
        else {
            //创建对话界面
            FXMLLoader loader = new FXMLLoader(Program.class.getResource("markdown-language/Chat.fxml"));
            Pane root = (Pane) loader.load();
            chatScene = new Scene(root);
            SceneManager.getInstance().stage.setScene(chatScene);

            //获取对话界面的控件
            name = (Label) root.lookup("#name");
            text = (Label) root.lookup("#text");
            narration = (Label) root.lookup("#narration");
            center = (VBox) root.lookup("#narrationField");
            bottom = (VBox) root.lookup("#dialogBox");
            options = (VBox) root.lookup("#options");

            //获取加载好的背景图
            BackgroundImage backImage = PreLoader.getInstance().getSceneImages().get(SceneManager.currentGameScene);
            Background background = new Background(backImage);
            root.setBackground(background);
        }
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

        //神秘的bug消除方式
        SceneManager.getInstance().stage.setMaximized(false);
        SceneManager.getInstance().stage.setMaximized(true);
    }

    public void endChatScene() throws Exception {
        SceneManager.getInstance().switchScene(SceneManager.currentGameScene);
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
}