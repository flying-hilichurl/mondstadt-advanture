package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.jsonpojo.JsonReader;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Plot;
import hilichurl.mondstadtadvanture.scenes.SceneManager;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class PlotManager {
    private final static PlotManager instance = new PlotManager();
    private String currentPlot;

    public static PlotManager getInstance(){return instance;}

    private PlotManager(){}

    //重新开始游戏时触发，进入第一个剧情节点
    public void startPlot(){
        currentPlot = "";
    }

    public void play() throws Exception {
        Plot plot = JsonReader.getInstance().readPlots(currentPlot);
        SceneManager.getInstance().switchChatScene(plot.getDialogue());
    }

    //检测此场景有无可触发剧情
    public void tryTargetPlot(Scene scene) throws Exception {
        HashMap<Scene,GameScenes> map = SceneManager.getInstance().getReserveSortedScene();
        if(map.containsKey(scene)){
            String nextPlot = JsonReader.getInstance().getPlotTarget(map.get(scene),currentPlot);
            if(nextPlot!=null){
                currentPlot=nextPlot;
                play();
            }
        }
    }
}
