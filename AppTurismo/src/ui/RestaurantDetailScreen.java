package ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Restaurante;

public class RestaurantDetailScreen {
	
	private Scene scene;
    private Stage window;

    public RestaurantDetailScreen(Stage window, Restaurante restaurante) {
        this.window = window;
        VBox layout = new VBox(10);

        Label lblNombre = new Label(restaurante.getNombre());
        Label lblDescripcion = new Label(restaurante.getDescripcion());
        Button btnVerUbicacion = new Button("Ver Ubicación en Google Maps");

        btnVerUbicacion.setOnAction(e -> {
            // Aquí abrimos Google Maps con el enlace almacenado
            window.setScene(new MainScreen(window).getScene());
        });

        layout.getChildren().addAll(lblNombre, lblDescripcion, btnVerUbicacion);

        this.scene = new Scene(layout, 400, 300);
    }

    public Scene getScene() {
        return scene;
    }

}
