package hilichurl.mondstadtadvanture.fxmlcontroller;

import hilichurl.mondstadtadvanture.scenes.SceneManager;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.scenes.SpotSetter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class WorldMapController {
   public void onEnter(ActionEvent event) throws Exception {
       if(event.getSource() instanceof Button){
           Button source = (Button) event.getSource();
           switch (source.getText()){
               case "冒险家协会":
                   SceneManager.getInstance().switchScene(GameScenes.AdventureGuild);
                   break;
               case "西风骑士团":
                   SceneManager.getInstance().switchScene(GameScenes.KnightlyOrder);
                   break;
               case "喷泉广场":
                   SceneManager.getInstance().switchScene(GameScenes.Square);
                   SpotSetter.getInstance().setSpot("square");
                   break;
               case "天使的馈赠":
                   SceneManager.getInstance().switchScene(GameScenes.AngelBounty);
                   break;
               case "西风大教堂":
                   SceneManager.getInstance().switchScene(GameScenes.Cathedral);
                   break;
           }
       }

   }
}
