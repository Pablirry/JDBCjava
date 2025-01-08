package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import db.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Restaurante;

public class MainScreen {
	
	private Scene scene;
    private Stage window;
    private BorderPane layoutPrincipal;

    public MainScreen(Stage window) {
        this.window = window;
        layoutPrincipal = new BorderPane();

        layoutPrincipal.setTop(crearMenu());

        cargarRestaurantes();

        this.scene = new Scene(layoutPrincipal, 800, 600);
    }

    private MenuBar crearMenu() {
        Menu menuNavegacion = new Menu("Navegación");
        MenuItem menuInicio = new MenuItem("Inicio");
        MenuItem menuGastronomia = new MenuItem("Restaurantes");
        MenuItem menuMapaGlobal = new MenuItem("Mapa Global");
        MenuItem menuSalir = new MenuItem("Salir");

        menuInicio.setOnAction(e -> layoutPrincipal.setCenter(new Label("¡Bienvenido a Turismo en Asturias!")));
        menuGastronomia.setOnAction(e -> cargarRestaurantes());
        menuMapaGlobal.setOnAction(e -> mostrarMapaGlobal());
        menuSalir.setOnAction(e -> System.exit(0));

        menuNavegacion.getItems().addAll(menuInicio, menuGastronomia, menuMapaGlobal, new SeparatorMenuItem(), menuSalir);
        return new MenuBar(menuNavegacion);
    }

    private void cargarRestaurantes() {
        VBox restaurantesPanel = new VBox(10);
        restaurantesPanel.setPadding(new javafx.geometry.Insets(10));

        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM restaurantes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Restaurante restaurante = new Restaurante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("ruta_imagen"),
                        rs.getString("ubicacion")
                );

                Button btnVerResenas = new Button("Ver Reseñas");
                btnVerResenas.setOnAction(e -> window.setScene(new RestaurantDetailScreen(window, restaurante).getScene()));

                Label lblNombre = new Label(restaurante.getNombre());
                restaurantesPanel.getChildren().addAll(lblNombre, btnVerResenas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScrollPane scrollPane = new ScrollPane(restaurantesPanel);
        layoutPrincipal.setCenter(scrollPane);
    }

    private void mostrarMapaGlobal() {
        // Implementar función para mostrar mapa global
    }

    public Scene getScene() {
        return scene;
    }

}
