/**
 * 
 */
/**
 * 
 */
module AppTurismo {
	requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires javafx.web;
	requires javafx.graphics;

    opens Controllers to javafx.fxml; // Permite acceso al paquete Controllers
    opens ui to javafx.fxml;

    exports App;
    exports Controllers;
    exports db;
    exports model;
    exports Helper;
}