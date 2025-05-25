package hilichurl.mondstadtadvanture;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MusicManager {
    private final static MusicManager instance=new MusicManager();
    private Media bacMusic;     //背景音乐
    private MediaPlayer mediaPlayer;

    public static MusicManager getInstance(){return instance;}

    private MusicManager(){}

    public void play(){
        bacMusic=new Media(Objects.requireNonNull(Program.class.getResource("musics/Bustling Afternoon of Mon.mp3")).toExternalForm());
        mediaPlayer=new MediaPlayer(bacMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void stop(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}