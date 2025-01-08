package Helper;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationHelper {
	
	/**
     * Carga una nueva ventana reemplazando la escena actual.
     *
     * @param stage       La ventana actual (Stage).
     * @param fxmlPath    Ruta del archivo FXML que se va a cargar.
     * @param windowTitle Título de la nueva ventana.
     */
    public static void loadWindow(Stage stage, String fxmlPath, String windowTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(windowTitle);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la ventana: " + fxmlPath);
            e.printStackTrace();
        }
    }

}
