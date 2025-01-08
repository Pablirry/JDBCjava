package Controllers;

import java.io.IOException;

import Helper.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML
    private ImageView backgroundImage;

    @FXML
    private Button btnGastronomia;

    @FXML
    private Button btnResenas;

    @FXML
    private Button btnMapa;

    public void initialize() {
    	backgroundImage.setImage(new Image(
    	        getClass().getResource("/resources/asturias_background.jpg").toExternalForm()
    	    ));
    }

    @FXML
    public void abrirGastronomia(ActionEvent event) {
        NavigationHelper.loadWindow((Stage) btnGastronomia.getScene().getWindow(), "/fxml/Restaurantes.fxml", "Gastronomía en Asturias");
    }

    @FXML
    public void abrirResenas(ActionEvent event) {
        NavigationHelper.loadWindow((Stage) btnResenas.getScene().getWindow(), "/fxml/Resenas.fxml", "Reseñas");
    }

    @FXML
    public void abrirMapa(ActionEvent event) {
    	System.out.println("Botón 'Ver Mapa' presionado");
        NavigationHelper.loadWindow((Stage) btnMapa.getScene().getWindow(), "/fxml/Mapa.fxml", "Mapa Turístico");
    }
    
    @FXML
    public void handleMapView(ActionEvent event) {
        try {
            // Cargar la vista del mapa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapaView.fxml"));
            Parent mapView = loader.load();
            
            // Obtener la escena actual
            Scene currentScene = ((Node) event.getSource()).getScene();
            
            // Establecer la nueva vista en la escena
            Stage primaryStage = (Stage) currentScene.getWindow();
            primaryStage.setScene(new Scene(mapView));
            primaryStage.show();
            
            System.out.println("Botón 'Ver Mapa' presionado");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista del mapa");
        }
    }

}
