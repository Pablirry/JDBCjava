package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {
	
	private Scene scene;
    private Stage window;

    public LoginScreen(Stage window) {
        this.window = window;
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();

        Label lblContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();

        Button btnLogin = new Button("Iniciar Sesión");

        btnLogin.setOnAction(e -> {
            // Aquí podríamos validar el usuario antes de mostrar el MainScreen
            window.setScene(new MainScreen(window).getScene());
        });

        layout.getChildren().addAll(lblUsuario, txtUsuario, lblContrasena, txtContrasena, btnLogin);
        this.scene = new Scene(layout, 400, 300);
    }

    public Scene getScene() {
        return scene;
    }

}
