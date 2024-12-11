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

    public GestorJDBC() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASS);
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public void insertarDestino (Destino destino) throws SQLException {

        String consulta = "INSERT INTO destinos (nombre, descripcion, region, clima, tipo) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(consulta);
            stmt.setString(1, destino.getNombre());
            stmt.setString(2, destino.getDescripcion());
            stmt.setString(3, destino.getRegion());
            stmt.setString(4, destino.getClima());
            stmt.setString(5, destino.getTipo());
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error al insertar destino: " + e.getMessage());
        }
    }

    public void eliminarDestino(String nombre) throws SQLException {
        String query = "DELETE FROM destinos WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        }
    }

    // Update a destination's description
    public void actualizarDestino(String nombre, String nuevaDescripcion) throws SQLException {
        String query = "UPDATE destinos SET descripcion = ? WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevaDescripcion);
            stmt.setString(2, nombre);
            stmt.executeUpdate();
        }
    }

    // List all destinations
    public List<Destino> listarDestinos() throws SQLException {
        List<Destino> destinos = new ArrayList<>();
        String query = "SELECT * FROM destinos";
        try (Statement stmt = conn.createStatement(); 
        		ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Destino destino = new Destino(
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("region"),
                    rs.getString("clima"),
                    List.of("Actividades")
                );
                destinos.add(destino);
            }
        }
        return destinos;
    }

    // Insert an activity into the database
    public void insertarActividad(Actividad actividad) throws SQLException {
        String query = "INSERT INTO actividades (nombre, tipo, precio, duracion, dificultad) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, actividad.getNombre());
            stmt.setString(2, actividad.getTipo());
            stmt.setDouble(3, actividad.getPrecio());
            stmt.setInt(4, actividad.getDuracion());
            stmt.setString(5, actividad.getDificultad());
            stmt.executeUpdate();
        }
    }

    // List all activities
    public List<Actividad> listarActividades() throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM actividades";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getInt("duracion"),
                    rs.getString("dificultad")
                );
                actividades.add(actividad);
            }
        }
        return actividades;
    }
    
    public void sincronizarDestinosDesdeCsv(List<Destino> destinosCsv) throws SQLException {
        for (Destino destino : destinosCsv) {
            insertarDestino(destino);
        }
    }

    public void sincronizarActividadesDesdeCsv(List<Actividad> actividadesCsv) throws SQLException {
        for (Actividad actividad : actividadesCsv) {
            insertarActividad(actividad);
        }
    }

    }
