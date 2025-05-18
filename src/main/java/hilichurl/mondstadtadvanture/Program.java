package hilichurl.mondstadtadvanture;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("markdown-language/StartInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("蒙德历险记");
        stage.setScene(scene);
        configWindows(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void configWindows(Stage stage){
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        int width = (int) visualBounds.getWidth();
        int height = (int) visualBounds.getHeight();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX(0);
        stage.setY(0);
        stage.initStyle(StageStyle.UNDECORATED);
    }
}