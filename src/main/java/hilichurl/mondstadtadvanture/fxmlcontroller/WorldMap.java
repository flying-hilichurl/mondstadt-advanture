package hilichurl.mondstadtadvanture.fxmlcontroller;

import hilichurl.mondstadtadvanture.SceneManager;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class WorldMap {
   public void onEnter(ActionEvent event) throws Exception {
       if(event.getSource() instanceof Button){
           Button source = (Button) event.getSource();
           switch (source.getText()){
               case "冒险家协会":
                   SceneManager.instance.switchScene(GameScenes.AdventureGuild);
                   break;
               case "西风骑士团":
                   SceneManager.instance.switchScene(GameScenes.KnightlyOrder);
                   break;
               case "喷泉广场":
                   SceneManager.instance.switchScene(GameScenes.Square);
                   break;
               case "天使的馈赠":
                   SceneManager.instance.switchScene(GameScenes.AngelBounty);
                   break;
               case "西风大教堂":
                   SceneManager.instance.switchScene(GameScenes.Cathedral);
                   break;
           }
       }

   }
}
