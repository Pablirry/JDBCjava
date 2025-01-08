package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
	
	private static final String DB_URL = "jdbc:mysql://localhost/turismoAPI";

    // Establecer conexión con la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "root", "root");
    }

}
