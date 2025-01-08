package Interface;

import Model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TurismoInterfazFX extends Application {

	private TableView<Destino> tableDestinos;
    private TableView<Actividad> tableActividades;    
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Turismo");

        BorderPane root = new BorderPane();
        root.setTop(createMenuBar(primaryStage));
        root.setCenter(mostrarPantallaDeInicioSesion(primaryStage));

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add("styles.css"); // Vincula el CSS
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu menuArchivo = new Menu("Archivo");
        MenuItem cerrarSesion = new MenuItem("Cerrar Sesión");
        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> System.exit(0));
        cerrarSesion.setOnAction(e -> stage.setScene(new Scene(mostrarPantallaDeInicioSesion(stage))));

        menuArchivo.getItems().addAll(cerrarSesion, salir);

        Menu menuAyuda = new Menu("Ayuda");
        MenuItem acercaDe = new MenuItem("Acerca de");
        acercaDe.setOnAction(e -> mostrarAcercaDe());

        menuAyuda.getItems().add(acercaDe);

        menuBar.getMenus().addAll(menuArchivo, menuAyuda);
        return menuBar;
    }

    private VBox mostrarPantallaDeInicioSesion(Stage stage) {
        VBox loginBox = new VBox(15);
        loginBox.setPadding(new Insets(20));
        loginBox.getStyleClass().add("login-box");

        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();
        Label lblPassword = new Label("Contraseña:");
        PasswordField txtPassword = new PasswordField();

        Button btnLogin = new Button("Iniciar Sesión");
        Button btnRegister = new Button("Registrarse");

        btnLogin.getStyleClass().add("button-primary");
        btnRegister.getStyleClass().add("button-secondary");

        btnLogin.setOnAction(e -> {
            if (validarUsuario(txtUsuario.getText(), txtPassword.getText())) {
                stage.setScene(new Scene(mostrarInterfazDeGestion(), 900, 700));
            } else {
                mostrarError("Usuario o contraseña incorrectos");
            }
        });

        btnRegister.setOnAction(e -> mostrarRegistroUsuario());

        loginBox.getChildren().addAll(lblUsuario, txtUsuario, lblPassword, txtPassword, btnLogin, btnRegister);
        return loginBox;
    }

    private BorderPane mostrarInterfazDeGestion() {
        TabPane tabPane = new TabPane();

        Tab tabDestinos = new Tab("Destinos", crearPanelDestinos());
        Tab tabActividades = new Tab("Actividades", crearPanelActividades());

        tabPane.getTabs().addAll(tabDestinos, tabActividades);

        BorderPane pane = new BorderPane();
        pane.setCenter(tabPane);
        return pane;
    }

    private VBox crearPanelDestinos() {
        tableDestinos = new TableView<>();
        TableColumn<Destino, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Destino, String> colRegion = new TableColumn<>("Región");

        tableDestinos.getColumns().addAll(colNombre, colRegion);

        Button btnAgregar = new Button("Agregar");
        btnAgregar.getStyleClass().add("button-primary");

        VBox vbox = new VBox(10, tableDestinos, btnAgregar);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    private VBox crearPanelActividades() {
        tableActividades = new TableView<>();
        TableColumn<Actividad, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Actividad, String> colTipo = new TableColumn<>("Tipo");

        tableActividades.getColumns().addAll(colNombre, colTipo);

        Button btnAgregar = new Button("Agregar");
        VBox vbox = new VBox(10, tableActividades, btnAgregar);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    private void mostrarRegistroUsuario() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Funcionalidad de registro en progreso");
        alert.showAndWait();
    }

    private boolean validarUsuario(String usuario, String password) {
        return usuario.equals("admin") && password.equals("1234");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
        alert.showAndWait();
    }

    private void mostrarAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gestión de Turismo v1.0\nDesarrollado por Pablo");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
