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
                   SceneManager.getInstance().switchScene(GameScenes.ADVENTURE_GUIDE);
                   SpotSetter.getInstance().setSpot("冒险家协会");
                   break;
               case "西风骑士团":
                   SceneManager.getInstance().switchScene(GameScenes.KNIGHTLY_ORDER);
                   SpotSetter.getInstance().setSpot("西风骑士团");
                   break;
               case "喷泉广场":
                   SceneManager.getInstance().switchScene(GameScenes.SQUARE);
                   SpotSetter.getInstance().setSpot("喷泉广场");
                   break;
               case "天使的馈赠":
                   SceneManager.getInstance().switchScene(GameScenes.ANGEL_BOUNTY);
                   SpotSetter.getInstance().setSpot("天使的馈赠");
                   break;
               case "西风大教堂":
                   SceneManager.getInstance().switchScene(GameScenes.CATHEDRAL);
                   SpotSetter.getInstance().setSpot("西风大教堂");
                   break;
           }
       }

   }
}
