package hilichurl.mondstadtadvanture.fxmlcontroller;

import hilichurl.mondstadtadvanture.scenes.GameSceneManager;
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
                   GameSceneManager.getInstance().switchGameScene(GameScenes.ADVENTURE_GUIDE);
                   SpotSetter.getInstance().setSpot(GameScenes.ADVENTURE_GUIDE);
                   break;
               case "西风骑士团":
                   GameSceneManager.getInstance().switchGameScene(GameScenes.KNIGHTLY_ORDER);
                   SpotSetter.getInstance().setSpot(GameScenes.KNIGHTLY_ORDER);
                   break;
               case "喷泉广场":
                   GameSceneManager.getInstance().switchGameScene(GameScenes.SQUARE);
                   SpotSetter.getInstance().setSpot(GameScenes.SQUARE);
                   break;
               case "天使的馈赠":
                   GameSceneManager.getInstance().switchGameScene(GameScenes.ANGEL_BOUNTY);
                   SpotSetter.getInstance().setSpot(GameScenes.ANGEL_BOUNTY);
                   break;
               case "西风大教堂":
                   GameSceneManager.getInstance().switchGameScene(GameScenes.CATHEDRAL);
                   SpotSetter.getInstance().setSpot(GameScenes.CATHEDRAL);
                   break;
           }
       }

   }
}
