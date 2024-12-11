package Persistence;

import Model.Actividad;
import Model.Destino;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorJDBC {

	private static final String URL = "jdbc:mysql://localhost:3306/turismo";
	private static final String USER = "root";
	private static final String PASS = "root";

	private Connection conn;
	
	/**
	 * Constructo de la clase para conectar con la base de datos
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public GestorJDBC() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USER, PASS);
	}
	
	/**
	 * Metodo para cerrar conexion
	 * @throws SQLException
	 */

	public void cerrar() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
	
	/**
	 * Metodo para insertar destinos en la base de datos
	 * @param destino : Destino
	 * @throws SQLException
	 */

	public void insertarDestino(Destino destino) throws SQLException {
		String consulta = "INSERT INTO destinos (nombre, descripcion, region, clima, tipo) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, destino.getNombre());
			stmt.setString(2, destino.getDescripcion());
			stmt.setString(3, destino.getRegion());
			stmt.setString(4, destino.getClima());
			stmt.setString(5, destino.getTipo());
			stmt.executeUpdate();
		}
	}
	
	/**
	 * Metodo para eliminar un destino en la base de datos
	 * @param nombre : String
	 * @return : Entero
	 * @throws SQLException
	 */

	public boolean eliminarDestino(String nombre) throws SQLException {
		String consulta = "DELETE FROM destinos WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nombre);
			int rowsAffected = stmt.executeUpdate();
			resetAutoIncrement("destinos");
			return rowsAffected > 0;
		}
	}
	
	/**
	 * Metodo para eliminar una actividaden la base de datos
	 * @param nombre : String
	 * @return : Entero
	 * @throws SQLException
	 */

	public boolean eliminarActividad(String nombre) throws SQLException {
		String consulta = "DELETE FROM actividades WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nombre);
			int rowsAffected = stmt.executeUpdate();
			resetAutoIncrement("actividades");
			return rowsAffected > 0;
		}
	}
	
	/**
	 * Metodo para actualizar un destino en la base de datos
	 * @param nombre : String
	 * @param nuevaDescripcion : String
	 * @throws SQLException
	 */

	public void actualizarDestino(String nombre, String nuevaDescripcion) throws SQLException {
		String consulta = "UPDATE destinos SET descripcion = ? WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nuevaDescripcion);
			stmt.setString(2, nombre);
			stmt.executeUpdate();
		}
	}
	
	/**
	 * Metodo para listar los destinos que hay en la base de datos
	 * @return : Destino
	 * @throws SQLException
	 */

	public List<Destino> listarDestinos() throws SQLException {
		List<Destino> destinos = new ArrayList<>();
		String consulta = "SELECT * FROM destinos";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
			while (rs.next()) {
				Destino destino = new Destino(rs.getString("nombre"), rs.getString("descripcion"),
						rs.getString("region"), rs.getString("clima"), List.of("Actividades"));
				destinos.add(destino);
			}
		}
		return destinos;
	}
	
	/**
	 * Metodo para listar las actividades que hay en la base de datos
	 * @return : Actividad
	 * @throws SQLException
	 */

	public List<Actividad> listarActividades() throws SQLException {
		List<Actividad> actividades = new ArrayList<>();
		String consulta = "SELECT * FROM actividades";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
			while (rs.next()) {
				Actividad actividad = new Actividad(rs.getString("nombre"), rs.getString("tipo"),
						rs.getDouble("precio"), rs.getInt("duracion"), rs.getString("dificultad"));
				actividades.add(actividad);
			}
		}
		return actividades;
	}
	
	/**
	 * Metodo que lee los archivos de destinos del csv y los introduce a la base de datos
	 * @param destinosCsv : List
	 * @throws SQLException
	 */

	public void sincronizarDestinosDesdeCsv(List<Destino> destinosCsv) throws SQLException {
		List<Destino> destinosBD = listarDestinos();
		for (Destino destino : destinosCsv) {
			boolean existe = false;
			for (Destino d : destinosBD) {
				if (d.getNombre().equalsIgnoreCase(destino.getNombre())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				insertarDestino(destino);
			}
		}
	}
	
	/**
	 * Metodo que lee los archivos de actividades del csv y los introduce a la base de datos
	 * @param actividadesCsv : List
	 * @throws SQLException
	 */

	public void sincronizarActividadesDesdeCsv(List<Actividad> actividadesCsv) throws SQLException {
		List<Actividad> actividadesBD = listarActividades();
		for (Actividad actividad : actividadesCsv) {
			boolean existe = false;
			for (Actividad a : actividadesBD) {
				if (a.getNombre().equalsIgnoreCase(actividad.getNombre())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				insertarActividad(actividad);
			}
		}
	}
	
	/**
	 * Metodo para insertar una nueva actividad en la base de datos
	 * @param actividad : Actividad
	 * @throws SQLException
	 */
	public void insertarActividad(Actividad actividad) throws SQLException {
		String consulta = "INSERT INTO actividades (nombre, tipo, precio, duracion, dificultad) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, actividad.getNombre());
			stmt.setString(2, actividad.getTipo());
			stmt.setDouble(3, actividad.getPrecio());
			stmt.setInt(4, actividad.getDuracion());
			stmt.setString(5, actividad.getDificultad());
			stmt.executeUpdate();
		}
	}
	
	/**
	 * Metodo para resetear el id
	 * @param tableName : String
	 * @throws SQLException
	 */

	public void resetAutoIncrement(String tableName) throws SQLException {
		String resetQuery = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(resetQuery);
		}
	}

}
