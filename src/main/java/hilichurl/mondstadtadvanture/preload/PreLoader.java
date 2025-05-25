package hilichurl.mondstadtadvanture.preload;

import hilichurl.mondstadtadvanture.enums.GameScenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class PreLoader{
    public static HashMap<GameScenes, BackgroundImage> sceneImages= new HashMap<>();
    private final HashMap<GameScenes,String> imagePaths =new HashMap<>();
    private final ArrayList<GameScenes> scenes =new ArrayList<>();
    private final static PreLoader instance = new PreLoader();
    private final ArrayList<EventHandler<ActionEvent>> imageHandlers =new ArrayList<>();  //事件响应者
    private int i=0;

    public static PreLoader getInstance(){return instance;}
    public HashMap<GameScenes, BackgroundImage> getSceneImages(){return sceneImages;}

    //初始化hashmap和list
    private PreLoader(){
        scenes.add(GameScenes.MAIN_MENU);
        imagePaths.put(GameScenes.MAIN_MENU,"images/start-background.png");
        scenes.add(GameScenes.MAP);
        imagePaths.put(GameScenes.MAP,"images/mondstadt.png");
        scenes.add(GameScenes.CATHEDRAL);
        imagePaths.put(GameScenes.CATHEDRAL,"images/cathedral.png");
        scenes.add(GameScenes.KNIGHTLY_ORDER);
        imagePaths.put(GameScenes.KNIGHTLY_ORDER,"images/knightlyOrder.png");
        scenes.add(GameScenes.ADVENTURE_GUIDE);
        imagePaths.put(GameScenes.ADVENTURE_GUIDE,"images/adventureGuide.png");
        scenes.add(GameScenes.SQUARE);
        imagePaths.put(GameScenes.SQUARE,"images/square.png");
        scenes.add(GameScenes.ANGEL_BOUNTY);
        imagePaths.put(GameScenes.ANGEL_BOUNTY,"images/angelBounty.png");
    }

    public void preLoadImage(boolean once){
        //创建继承于Service的匿名类
        Service<Image> imageLoader =new Service<>() {
            @Override
            protected Task<Image> createTask(){
                return new ImageLoaderTask(imagePaths.get(scenes.get(i)));
            }
        };

        imageLoader.setOnFailed(e-> {
            System.out.println("图片预加载失败");
            Next();
        });

        //图片加载完成后，将其存入map备用
        imageLoader.setOnSucceeded(event->{
            Image image=imageLoader.getValue();
            BackgroundImage bckImage=new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(image.getWidth(),image.getHeight(),true,true,true,true)
            );
            sceneImages.put(scenes.get(i),bckImage);
            i++;
            if(!once)
                Next();
            else{
                invoke(imageHandlers);
                i=0;    //虽然i=0可以写在invoke中，当时依据单一职责原则，i不应该写进去
            }
        });

        //开始异步加载
        imageLoader.start();
    }

    //处理下一步决策
    private void Next(){
        //若还有未加载的图片，继续加载
        if(i<scenes.size())
            preLoadImage(false);
        else{
            invoke(imageHandlers);
            i=0;
        }
    }

    //注册事件
    public void setOnImageLoaded(EventHandler<ActionEvent> value){
        imageHandlers.add(value);
    }

    //依次触发订阅事件
    public void invoke(List<EventHandler<ActionEvent>> handlerList){
        ActionEvent actionEvent = new ActionEvent();

        for(EventHandler<ActionEvent> handler:handlerList)
            handler.handle(actionEvent);
        handlerList.clear();
    }
}
