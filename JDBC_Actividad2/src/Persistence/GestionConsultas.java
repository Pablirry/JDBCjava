package Persistence;

import java.sql.*;

import java.util.List;

import Error.ExcepcionPersistencia;
import Error.LogicaExcepcion;
import Logic.Coche;
import Logic.Propietario;
import Logic.Taller;

public class GestionConsultas {

    private static final String url = "jdbc:mysql://localhost/concesionario";
    private static final String user = "root";
    private static final String pass = "root";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public void leerCoches(Taller taller) {
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);

            st = conexion.createStatement();

            // Como es una consulta se hace executeQuery
            rs = st.executeQuery("SELECT * FROM coche");

            while (rs.next()) {
                // Se extraen los campos del coche
                String matricula = rs.getString("matricula");
                String marca = rs.getString("marca");
                int precio = rs.getInt("precio");
                String dniPropietario = rs.getString("dni");

                taller.addCoche(new Coche(matricula, marca, precio, dniPropietario));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Problemas al abrir el driver");
            System.out.println(e.toString());
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BBDD");
        } finally {
            try {
                rs.close();
                st.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void actualizarCoches(Taller taller) {

    }

    public void leerPropietario(Taller taller) {
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);

            st = conexion.createStatement();

            rs = st.executeQuery("SELECT * FROM propietario");

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");

                taller.addPropietario(new Propietario(dni, nombre, edad));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Problemas al abrir el driver");
            System.out.println(e.toString());
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BBDD");
        } finally {
            try {
                rs.close();
                st.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void actualizarPropietarios(Taller taller) {

    }

    public String buscarCoche(String matricula) {
        return "";
    }

    public void insertarNuevoPropietario(Propietario p) throws ExcepcionPersistencia {

        Connection conexion = null;
        Statement st = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);

            st = conexion.createStatement();

            // Como es una consulta se hace executeQuery

            String sentencia = "INSERT INTO propietario VALUES('" + p.getDni() + "', '" + p.getNombre() + "', "
                    + p.getEdad() + ")";
            int resultado = st.executeUpdate(sentencia); // Devuelve la cantidad de registros que han quedado afectados
            if (resultado == 0)
                System.out.println("TRAZA: Error, no insertado");
            else
                System.out.println("TRAZA: Insertado propietario dni: " + p.getDni());
        } catch (Exception e) {
            throw new ExcepcionPersistencia("Error al insertar en la tabla");
        } finally {
            try {
                st.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void insertarNuevoCoche(Coche c) throws ExcepcionPersistencia {
        Connection conexion = null;
        Statement st = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);

            st = conexion.createStatement();

            // Como es una consulta se hace executeQuery

            String sentencia = "INSERT INTO coche VALUES('" + c.getMatricula() + "', '" + c.getMarca() + "', "
                    + c.getPrecio() + ", '" + c.getDniPropietario() + "')";
            int resultado = st.executeUpdate(sentencia); // Devuelve la cantidad de registros que han quedado afectados
            if (resultado == 0)
                System.out.println("TRAZA: Error, no insertado");
            else
                System.out.println("TRAZA: Insertado propietario matrícula: " + c.getMatricula());
        } catch (Exception e) {
            throw new ExcepcionPersistencia("Error al insertar en la tabla");
        } finally {
            try {
                st.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void borrarCoche(Coche c) throws ExcepcionPersistencia, LogicaExcepcion, SQLException {

        Statement st = StatementSingleton.getInstance();
        String sentencia = "DELETE FROM coche WHERE matricula = '" + c.getMatricula() + "'";
        int regAfectado;

        regAfectado = st.executeUpdate(sentencia);
        System.out.println("Traza numero de registros afectados: " + regAfectado);

    }

    public void borrarPropietario(Propietario p, List<Coche> cochePropietario)
            throws ExcepcionPersistencia, LogicaExcepcion, SQLException {
        for (Coche c : cochePropietario) {
            this.borrarCoche(c);
        }
        Statement st = StatementSingleton.getInstance();
        String sentencia = "DELETE FROM propietario WHERE dni = '" + p.getDni() + "'";
        st.executeUpdate(sentencia);

        st.close();
    }

    public void incrementaEdad() throws ClassNotFoundException, SQLException {
        Connection conexion = null;

        Class.forName(driver);
        conexion = DriverManager.getConnection(url, user, pass);

        String consulta = "update coche set precio=?";
        PreparedStatement ps = conexion.prepareStatement(consulta);
        ps.setInt(1, 20);
        int resul = ps.executeUpdate();
        System.out.println("Actualiza precio " + resul);
    }

    public void listarInfo() throws ClassNotFoundException, SQLException {
    	Connection conexion = null;

        try {
            // Cargar el driver
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);

            // Obtener metadatos de la base de datos
            DatabaseMetaData datos = conexion.getMetaData();

            // Mostrar información general de la base de datos
            System.out.println("Nombre del Producto: " + datos.getDatabaseProductName());
            System.out.println("Versión del Producto: " + datos.getDatabaseProductVersion());
            System.out.println("Nombre del Usuario: " + datos.getUserName());

            // Obtener y mostrar los catálogos (bases de datos)
            ResultSet rsCatalogs = datos.getCatalogs();
            System.out.println("\nCatálogos (Bases de datos):");
            while (rsCatalogs.next()) {
                System.out.println("- " + rsCatalogs.getString(1));
            }

            // Obtener y mostrar las tablas de la base de datos actual
            String catalog = conexion.getCatalog(); // Catalogo actual
            String schemaPattern = null; // Patrón de esquema, null para todos
            String tableNamePattern = "%"; // Patrón de nombre de tabla
            String[] types = {"TABLE"}; // Solo tablas

            ResultSet rsTables = datos.getTables(catalog, schemaPattern, tableNamePattern, types);
            System.out.println("\nTablas:");
            while (rsTables.next()) {
                String tableName = rsTables.getString("TABLE_NAME");
                System.out.println("Tabla: " + tableName);

                // Obtener columnas de cada tabla
                ResultSet rsColumns = datos.getColumns(catalog, schemaPattern, tableName, "%");
                System.out.println("  Columnas:");
                while (rsColumns.next()) {
                    String columnName = rsColumns.getString("COLUMN_NAME");
                    String columnType = rsColumns.getString("TYPE_NAME");
                    int columnSize = rsColumns.getInt("COLUMN_SIZE");
                    System.out.println("    - " + columnName + " (" + columnType + " (" + columnSize + "))");
                }
                rsColumns.close();
            }

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
            
        }
        

    }


