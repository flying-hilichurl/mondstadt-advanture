package hilichurl.mondstadtadvanture.scenes;

import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.Interacter;
import hilichurl.mondstadtadvanture.json.JsonReader;
import hilichurl.mondstadtadvanture.json.Option;
import hilichurl.mondstadtadvanture.json.Spot;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class SpotSetter {
    private final static SpotSetter instance = new SpotSetter();
    private final JsonReader reader = new JsonReader();

    private SpotSetter(){}

    public static SpotSetter getInstance(){return instance;}

    public void setSpot(String name) throws IOException {
        Parent root = SceneManager.currentScene.getRoot();

        //查找spot
        Spot targetSpot=null;
        for(Spot spot : reader.getSpots().getSpots()){
            if(Objects.equals(spot.getName(), name))
                targetSpot = spot;
        }

        //错误处理及提醒
        if(targetSpot==null){
            System.out.println("spot "+name+" not found");
            return;
        }

        //设置背景图片
        String path= Program.class.getResource(targetSpot.getBackground()).toExternalForm();
        root.setStyle("-fx-background-image:url('"+path+"');"+"-fx-background-repeat:no-repeat;"+
                "-fx-background-size: cover;");

        //显示选项
        VBox vBox =(VBox) root.lookup("#optionals");
        vBox.getChildren().clear();
        for(Option option:targetSpot.getOptionals()){
            Button button =new Button(option.getText());
            vBox.getChildren().add(button);

            if(option.getType()== Interacter.Person){
                //对话未实现
            }
            //绑定切换场景的事件
            else if(option.getType()==Interacter.Spot){
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