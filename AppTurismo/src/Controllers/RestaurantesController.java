package Controllers;

import Helper.NavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantesController {
	
	 @FXML
	    private ImageView backgroundImage;

	    @FXML
	    private VBox restaurantList;

	    public void initialize() {
	        // Cargar fondo
	        backgroundImage.setImage(new Image("/resources/asturias_restaurants.jpg"));

	        // Crear dinámicamente elementos para cada restaurante
	        agregarRestaurante("Casa Gerardo", "Excelente comida asturiana.", "/resources/casa_gerardo.jpg");
	        agregarRestaurante("La Marisquería", "Mariscos frescos con vista al mar.", "/resources/la_marisqueria.jpg");
	    }

	    private void agregarRestaurante(String nombre, String descripcion, String imagePath) {
	        VBox restaurantBox = new VBox();
	        restaurantBox.setSpacing(5);

	        ImageView imageView = new ImageView(new Image(imagePath));
	        imageView.setFitWidth(100);
	        imageView.setFitHeight(100);

	        Text nameText = new Text(nombre);
	        Text descText = new Text(descripcion);

	        Button btnDetails = new Button("Ver Reseñas");
	        btnDetails.setOnAction(event -> abrirResenas(nombre));

	        restaurantBox.getChildren().addAll(imageView, nameText, descText, btnDetails);
	        restaurantList.getChildren().add(restaurantBox);
	    }

	    private void abrirResenas(String restaurante) {
	        // Lógica para mostrar las reseñas del restaurante seleccionado
	        System.out.println("Abriendo reseñas de: " + restaurante);
	    }

	    @FXML
	    private void volverAlMenu() {
	        NavigationHelper.loadWindow((Stage) backgroundImage.getScene().getWindow(), "/fxml/MainMenu.fxml", "Menú Principal");
	    }

}
