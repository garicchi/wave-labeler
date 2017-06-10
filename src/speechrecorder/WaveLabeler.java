package speechrecorder; /**
 * Created by garicchi on 2015/11/14.
 */

import gui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WaveLabeler extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            mainStage = primaryStage;
            this.mainStage.setTitle("WaveLabeler");
            this.mainStage.setScene(scene);
            this.mainStage.show();

            MainWindowController controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
