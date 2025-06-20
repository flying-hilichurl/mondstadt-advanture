package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.jsonpojo.JsonReader;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Plot;
import hilichurl.mondstadtadvanture.scenes.ChatSceneManager;
import hilichurl.mondstadtadvanture.scenes.GameSceneManager;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.Objects;

public class PlotManager {
    private final static PlotManager instance = new PlotManager();
    private String currentPlot;
    private String currentGroup;

    public void setCurrentPlot(String plot){currentPlot=plot;}
    public void setCurrentGroup(String group){currentGroup=group;}

    public static PlotManager getInstance(){return instance;}

    private PlotManager(){}

    //重新开始游戏时触发，进入第一个剧情节点
    public void startPlot(){
        currentPlot = "";
        currentGroup="";
    }

    public void play() throws Exception {
        Plot plot = JsonReader.getInstance().readPlots(currentPlot);
        ChatSceneManager.getInstance().switchChatScene(plot.getDialogue());
    }

    //检测此场景有无可触发剧情
    public void tryTargetPlot(Scene scene) throws Exception {
        HashMap<Scene,GameScenes> map = GameSceneManager.getInstance().getReserveSortedScene();
        if(map.containsKey(scene)&&map.get(scene)!=GameScenes.MAIN_MENU){
            String nextPlot = JsonReader.getInstance().getPlotTarget(map.get(scene),currentPlot);
            if(nextPlot!=null){
                currentPlot=nextPlot;
                play();
            }
        }
    }

    public boolean checkChatTarget(String condition){
        if(Objects.equals(condition, ""))
            return true;
        else if(Objects.equals(condition,currentGroup))
            return true;
        else if(Objects.equals(condition,currentPlot))
            return true;
        else
            return false;
    }


}
