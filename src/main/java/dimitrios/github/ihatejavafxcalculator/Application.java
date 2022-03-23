package dimitrios.github.ihatejavafxcalculator;
/**
 * @author: Dimitrios Lanaras
 */

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello Calculator!");
        stage.setScene(scene);
        ((HelloController) fxmlLoader.getController()).setGlobalKeyPressed(scene);
        stage.show();
        ((HelloController) fxmlLoader.getController()).focusOnEquals();

}

    public static void main(String[] args) {
        launch();
    }
}