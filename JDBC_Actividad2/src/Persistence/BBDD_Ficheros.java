package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BBDD_Ficheros {

	private static final String url = "jdbc:mysql://localhost/concesionario";
	private static final String user = "root";
	private static final String pass = "root";
	private static final String driver = "com.mysql.cj.jdbc.Driver";

	private static BBDD_Ficheros instancia;
	private static Connection connection;

	private BBDD_Ficheros() {
		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Conectado a la base de datos ");
		} catch (ClassNotFoundException e) {
			System.err.println("Error al acceder al driver");
			System.out.println(e.toString());
		} catch (SQLException e) {
			System.err.println("Error al acceder a la BBDD");
			System.out.println(e.toString());
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			System.err.println("Error al cerrar la conexión");
			System.out.println(e.toString());
		}
	}

	public static Connection getInstance() throws SQLException {
		if (instancia == null || BBDD_Ficheros.getConnection().isClosed()) {
			instancia = new BBDD_Ficheros();
		}
		return instancia.getConnection();
	}

}
