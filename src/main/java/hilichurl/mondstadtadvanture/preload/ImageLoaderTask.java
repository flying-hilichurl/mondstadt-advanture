package hilichurl.mondstadtadvanture.preload;

import hilichurl.mondstadtadvanture.Program;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class ImageLoaderTask extends Task<Image> {
    private final String imagePath;   //图片的类路径

    ImageLoaderTask(String imagePath) {
        this.imagePath=imagePath;
    }

    @Override
    protected Image call() throws Exception {
        URL url=new URI(Objects.requireNonNull(Program.class.getResource(imagePath)).toExternalForm()).toURL();
        try(InputStream inputStream = url.openStream()){
            Image image=new Image(inputStream);
            return image;
        }
    }
}
