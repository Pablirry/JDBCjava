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
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public GestorJDBC() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USER, PASS);
	}

	/**
	 * Metodo para cerrar conexion
	 * 
	 * @throws SQLException
	 */

	public void cerrar() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	/**
	 * Metodo para insertar destinos en la base de datos
	 * 
	 * @param destino : Destino
	 * @throws SQLException
	 */

	public void insertarDestino(Destino destino) throws SQLException {
		String consulta = "INSERT INTO destinos (nombre, descripcion, region, clima, tipo) "
				+ "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
				+ "descripcion = VALUES(descripcion), region = VALUES(region), clima = VALUES(clima), tipo = VALUES(tipo)";
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
	 * 
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
	 * 
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
	 * 
	 * @param nombre           : String
	 * @param nuevaDescripcion : String
	 * @throws SQLException
	 */

	public void actualizarDestino(String nombre, Destino nuevoDestino) throws SQLException {
		String consulta = "UPDATE destinos SET nombre = ?, descripcion = ?, region = ?, clima = ?, tipo = ? WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nuevoDestino.getNombre());
			stmt.setString(2, nuevoDestino.getDescripcion());
			stmt.setString(3, nuevoDestino.getRegion());
			stmt.setString(4, nuevoDestino.getClima());
			stmt.setString(5, nuevoDestino.getTipo());
			stmt.setString(6, nombre);

			int act = stmt.executeUpdate();
			if (act > 0) {
				System.out.println("Destino actualizado correctamente: " + nuevoDestino.getNombre());
			} else {
				System.out.println("No se encontró el destino para actualizar: " + nombre);
			}
		}
	}

	/**
	 * Metodo para listar los destinos que hay en la base de datos
	 * 
	 * @return : Destino
	 * @throws SQLException
	 */

	public List<Destino> listarDestinos() throws SQLException {
		List<Destino> destinos = new ArrayList<>();
		String consulta = "SELECT * FROM destinos";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
			while (rs.next()) {
				Destino destino = new Destino(rs.getString("nombre"), rs.getString("descripcion"),
						rs.getString("region"), rs.getString("clima"), rs.getString("tipo"), new ArrayList<>());
				destinos.add(destino);
			}
		}
		return destinos;
	}

	/**
	 * Metodo para listar las actividades que hay en la base de datos
	 * 
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
	 * Metodo que lee los archivos de destinos del csv y los introduce a la base de
	 * datos
	 * 
	 * @param destinosCsv : List
	 * @throws SQLException
	 */

	public void sincronizarDestinosDesdeCsv(List<Destino> destinosCsv) throws SQLException {
		List<Destino> destinosBD = listarDestinos();
	    for (Destino destino : destinosCsv) {
	        boolean existe = destinosBD.stream()
	                                   .anyMatch(d -> d.getNombre().equalsIgnoreCase(destino.getNombre())
	                                               && d.getTipo().equalsIgnoreCase(destino.getTipo()));
	        if (!existe) {
	            System.out.println("Insertando destino en la base de datos: " + destino.getNombre());
	            insertarDestino(destino);
	        } else {
	            System.out.println("Destino ya existe en la base de datos: " + destino.getNombre());
	        }
	    }
	    System.out.println("Sincronización de destinos completada.");
	}

	/**
	 * Metodo que lee los archivos de actividades del csv y los introduce a la base
	 * de datos
	 * 
	 * @param actividadesCsv : List
	 * @throws SQLException
	 */

	public void sincronizarActividadesDesdeCsv(List<Actividad> actividadesCsv) throws SQLException {
		List<Actividad> actividadesBD = listarActividades();
		boolean cambiosRealizados = false;
		for (Actividad actividad : actividadesCsv) {
			boolean existe = false;
			for (Actividad a : actividadesBD) {
				if (a.getNombre().equalsIgnoreCase(actividad.getNombre())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				System.out.println("Insertando actividad en la base de datos: " + actividad.getNombre());
				insertarActividad(actividad);
				cambiosRealizados = true;
			}
		}

		if (!cambiosRealizados) {
			System.out.println("No se encontraron nuevas actividades para sincronizar.");
		} else {
			System.out.println("Sincronización de actividades completada.");
		}
	}

	/**
	 * Metodo para insertar una nueva actividad en la base de datos
	 * 
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
	 * 
	 * @param tableName : String
	 * @throws SQLException
	 */

	public void resetAutoIncrement(String tableName) throws SQLException {
		String reset = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(reset);
		}
	}

	public void actualizarActividad(String nombre, Actividad nuevaActividad) throws SQLException {
		String consulta = "UPDATE actividades SET nombre = ?, tipo = ?, precio = ?, duracion = ?, dificultad = ? WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nuevaActividad.getNombre());
			stmt.setString(2, nuevaActividad.getTipo());
			stmt.setDouble(3, nuevaActividad.getPrecio());
			stmt.setInt(4, nuevaActividad.getDuracion());
			stmt.setString(5, nuevaActividad.getDificultad());
			stmt.setString(6, nombre);

			int act = stmt.executeUpdate();
			if (act > 0) {
				System.out.println("Actividad actualizada correctamente: " + nuevaActividad.getNombre());
			} else {
				System.out.println("No se encontró la actividad para actualizar: " + nombre);
			}
		}
	}

	public Destino obtenerDestinoPorNombre(String nombre) throws SQLException {
		String consulta = "SELECT * FROM destinos WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nombre);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Destino(rs.getString("nombre"), rs.getString("descripcion"), rs.getString("region"),
							rs.getString("clima"), rs.getString("tipo"), new ArrayList<>());
				}
			}
		}
		return null;
	}

	public Actividad obtenerActividadPorNombre(String nombre) throws SQLException {
		String consulta = "SELECT * FROM actividades WHERE nombre = ?";
		try (PreparedStatement stmt = conn.prepareStatement(consulta)) {
			stmt.setString(1, nombre);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Actividad(rs.getString("nombre"), rs.getString("tipo"), rs.getDouble("precio"),
							rs.getInt("duracion"), rs.getString("dificultad"));
				}
			}
		}
		return null;
	}

}
