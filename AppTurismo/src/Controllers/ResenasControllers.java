package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class ResenasControllers {
	
	@FXML
    private ListView<String> reseñasList;

    @FXML
    private TextArea reseñaInput;

    @FXML
    private HBox starRating;

    private int puntuacionSeleccionada = 0;

    public void initialize() {
        // Cargar reseñas iniciales (puedes obtenerlas de la base de datos)
        reseñasList.getItems().addAll(
            "Muy buena comida. Puntuación: ★★★★☆",
            "Ambiente acogedor. Puntuación: ★★★★☆"
        );
    }

    @FXML
    private void selectStar() {
        // Determinar cuántas estrellas están seleccionadas
        puntuacionSeleccionada = (int) starRating.getChildren().stream()
                .filter(node -> node instanceof ToggleButton && ((ToggleButton) node).isSelected())
                .count();
    }

    @FXML
    private void enviarReseña() {
        String reseña = reseñaInput.getText();
        if (reseña.isEmpty() || puntuacionSeleccionada == 0) {
            System.out.println("Por favor, completa todos los campos.");
            return;
        }

        // Agregar reseña a la lista
        reseñasList.getItems().add("Reseña: " + reseña + " Puntuación: " + "★".repeat(puntuacionSeleccionada));

        // Limpiar campos
        reseñaInput.clear();
        starRating.getChildren().forEach(node -> {
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        });

        puntuacionSeleccionada = 0;
    }

}
