package App;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.LoginScreen;
import ui.MainScreen;

public class TurismoAsturiasApp extends Application {
	
	private Stage window;
    private LoginScreen loginScreen;
    private MainScreen mainScreen;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
    	Scene scene = new Scene(fxmlLoader.load());
    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Turismo en Asturias");
    	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
