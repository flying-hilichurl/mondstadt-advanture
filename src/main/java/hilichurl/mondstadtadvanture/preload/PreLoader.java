package hilichurl.mondstadtadvanture.preload;

import hilichurl.mondstadtadvanture.enums.GameScenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import org.jetbrains.annotations.NotNull;

public class PreLoader{
    public static HashMap<GameScenes, BackgroundImage> sceneImages= new HashMap<>();
    private final HashMap<GameScenes,String> imagePaths =new HashMap<>();
    private final ArrayList<GameScenes> scenes =new ArrayList<>();
    private final static PreLoader instance = new PreLoader();
    private final ArrayList<EventHandler<ActionEvent>> imageHandlers =new ArrayList<>();  //事件响应者
    int i=0;

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

    //预加载所有图片
    public void preLoadAllImage(){
        i=0;
        for(;i<scenes.size();i++){
            //只有图片未被加载时，再去加载图片
            if(!sceneImages.containsKey(scenes.get(i))){
                Service<Image> imageLoader = createImageLoader();
                int num=i;

                //处理加载失败时的逻辑
                imageLoader.setOnFailed(failEvent->System.out.println(scenes.get(num)+"加载失败"));

                //加载成功时的逻辑
                imageLoader.setOnSucceeded(event-> {
                    sortBacImage(imageLoader.getValue(),scenes.get(num));
                    System.out.println(scenes.get(num));

                    if (num+1>=scenes.size()) {
                        invoke(imageHandlers);
                    }
                });

                imageLoader.start();
            }
        }
    }

    //加载某一张图片
    public void preLoadOneImage(GameScenes gameScenes){
        if(sceneImages.containsKey(gameScenes))
            invoke(imageHandlers);
        else
        {
            Service<Image> imageLoader=createImageLoader();

            imageLoader.setOnFailed(e->{
                System.out.println("图片预加载失败");
                invoke(imageHandlers);
            });

            imageLoader.setOnSucceeded(event->{
                sortBacImage(imageLoader.getValue(),gameScenes);
                invoke(imageHandlers);
            });

            imageLoader.start();
        }
    }

    @NotNull
    private Service<Image> createImageLoader() {
        //创建继承于Service的匿名类
        Service<Image> imageLoader =new Service<>() {
            @Override
            protected Task<Image> createTask(){
                return new ImageLoaderTask(imagePaths.get(scenes.get(i)));
            }
        };

        return imageLoader;
    }

    //处理并存储Image
    private void sortBacImage(Image image,GameScenes gameScenes) {
        BackgroundImage bckImage=new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(image.getWidth(),image.getHeight(),true,true,true,true)
        );
        sceneImages.put(gameScenes,bckImage);
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
