package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Prueba {

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        Connection conexion = null;

        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Se obtiene una conexión con la base de datos.
            // En este caso nos conectamos a la base de datos prueba
            // con el usuario root y contraseña root
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/prueba_persona", "root", "root");
            System.out.println(ANSI_RESET + "TODO CORRECTO, conexion correcta ");

            // ^ Consultar la tabla persona
            consultarTablaPersona(conexion);
            // ^ Crear tabla contactos
            crearTablaContactos(conexion);
            // ^ Insertar datos en la tabla contactos
            // insertarTablaContactos(conexion);
            // ^ Modificar telefono por nombre
            // modificarTelefonoPorNombre(conexion, "Juan", "653220027");
            // ^ Modificar registro persona
            // modificarRegistroPersona(conexion);
            // ^ Insertar registro persona
            // insertarRegistroPersona(conexion);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException ex) {
            System.out.println(ANSI_RED + "error en la conexion");
        } finally { // ! Se cierra la conexión con la base de datos.
            try {
                if (conexion != null) {
                    conexion.close();
                    System.out.println(ANSI_RESET + "TODO CORRECTO cerramos conexion");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // ! 1. Consultar tabla persona
    public static void consultarTablaPersona(Connection conexion) throws SQLException {
        String consulta = "SELECT * FROM persona";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);
        System.out.println(ANSI_GREEN + "Tabla persona:");

        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") +
                    ", Nacimiento: " + rs.getDate("nacimiento"));
        }

        rs.close();
        stmt.close();
    }

    // ! 2. Crear tabla contactos
    public static void crearTablaContactos(Connection conexion) throws SQLException {

        Statement stmt = conexion.createStatement();

        // Borrar la tabla si ya existe
        String borrarTabla = "DROP TABLE IF EXISTS contactos";
        stmt.executeUpdate(borrarTabla);

        // Crear la tabla contactos
        String consulta = "CREATE TABLE contactos (" +
                "Id INT AUTO_INCREMENT PRIMARY KEY," +
                "Nombre VARCHAR(20)," +
                "Apellidos VARCHAR(40)," +
                "Telefono VARCHAR(10)," +
                "id_persona INT," +
                "FOREIGN KEY (id_persona) REFERENCES persona(id))";
        stmt.execute(consulta);
        System.out.println(ANSI_GREEN + "Tabla contactos creada");

        stmt.close();
    }

    // ! 3. Insertar registros en la tabla contacos
    public static void insertarTablaContactos(Connection conexion) throws SQLException {
        String[] nombres = { "Juan", "Maria", "Pablo" };
        String[] apellidos = { "Perez", "Fernandez", "Lopez" };
        String[] telefono = { "985563214", "653252227", "679541418" };

        Statement stmt = null;

        try {
            stmt = conexion.createStatement();

            for (int i = 0; i < nombres.length; i++) {
                String consulta = "INSERT INTO contactos (nombre, apellidos, telefono) VALUES ("
                        + nombres[i] + ","
                        + apellidos[i] + ","
                        + telefono[i] + ")";

                stmt.executeUpdate(consulta);
            }

        } catch (SQLException ex) {
            System.out.println(ANSI_RED + "Error al insertar contactos: " + ex.getMessage());
            throw ex; // Re-lanzar la excepción para manejo posterior
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    // ! 4. Modificar telefono por nombre
    public static void modificarTelefonoPorNombre(Connection conexion, String nombre, String nuevoTelefono)
            throws SQLException {
        String consulta = "UPDATE contactos SET telefono = '" + nuevoTelefono + "' WHERE nombre = '" + nombre + "'";
        Statement stmt = null;

        try {
            stmt = conexion.createStatement();
            int ru = stmt.executeUpdate(consulta);

            if (ru > 0) {
                System.out.println("Telefono de " + nombre + " actualizado correctamente");
            } else {
                System.out.println("No se encontro ningún contacto con el nombre " + nombre);
            }
        } catch (SQLException E) {
            System.out.println(ANSI_RED + "Error al modificar el telefono");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    // ! 5. Modificar registro persona
    public static void modificarRegistroPersona(Connection conexion, String nombre, int id) throws SQLException {
        String consulta = "SELECT * FROM persona";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(consulta);
        } catch (SQLException e){
            System.out.println(ANSI_RED + "Error al modificar el registro de la persona");
        } finally {
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
        }
    }

    // ! 6. Insertar registro persona
    public static void insertarRegistroPersona(Connection conexion) throws SQLException {
    }
}
