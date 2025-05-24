package hilichurl.mondstadtadvanture.scenes;

import com.fasterxml.jackson.databind.JsonNode;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.enums.Interacter;
import hilichurl.mondstadtadvanture.jsonpojo.JsonReader;
import hilichurl.mondstadtadvanture.jsonpojo.spots.Option;
import hilichurl.mondstadtadvanture.jsonpojo.spots.Spot;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SpotSetter {
    private final static SpotSetter instance = new SpotSetter();

    private SpotSetter(){}

    public static SpotSetter getInstance(){return instance;}

    public void setSpot(GameScenes gameScenes) throws IOException {
        Parent root = SceneManager.currentScene.getRoot();

        //查找spot
        Spot targetSpot=null;
        for(Spot spot : JsonReader.getInstance().getSpots().getSpots()){
            if(Objects.equals(spot.getName(), gameScenes.toString()))
                targetSpot = spot;
        }

        //错误处理及提醒
        if(targetSpot==null){
            System.out.println("spot "+gameScenes+" not found");
            return;
        }

        //显示选项
        VBox vBox =(VBox) root.lookup("#optionals");
        vBox.getChildren().clear();
        for(Option option:targetSpot.getOptionals()){
            Button button =new Button(option.getText());
            vBox.getChildren().add(button);

            if(option.getType()== Interacter.PERSON){
                button.setOnAction(event->{
                    try {
                        List<JsonNode> nodes=JsonReader.getInstance().getNPCChat(option.getText(),gameScenes);
                        ChatSceneManager.getInstance().switchChatScene(nodes,gameScenes);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            //绑定切换场景的事件
            else if(option.getType()==Interacter.SPOT){
                button.setOnAction(event->{
                    try {
                        SceneManager.getInstance().switchScene(option.getTarget());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        //显示文字描述
        Label label =(Label) root.lookup("#text");
        label.setText(targetSpot.getText());
    }
}