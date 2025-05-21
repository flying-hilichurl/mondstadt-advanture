package hilichurl.mondstadtadvanture;

import hilichurl.mondstadtadvanture.jsonpojo.JsonReader;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Plot;
import hilichurl.mondstadtadvanture.scenes.SceneManager;

import java.io.IOException;

public class PlotManager {
    private final static PlotManager instance = new PlotManager();
    private String currentPlot;

    public static PlotManager getInstance(){return instance;}

    private PlotManager(){}

    //重新开始游戏时触发，进入第一个剧情节点
    public void startPlot(){
        currentPlot = "plot1";
    }

    public void play() throws Exception {
        Plot plot = JsonReader.getInstance().readPlots(currentPlot);
        SceneManager.getInstance().switchChatScene(plot);

    }
}
