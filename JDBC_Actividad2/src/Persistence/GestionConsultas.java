package Persistence;

import java.sql.*;

import Exceptions.ExcepcionPersistencia;
import Logic.Coche;
import Logic.Propietario;
import Logic.Taller;

public class GestionConsultas {

    private static final String url = "jdbc:mysql://localhost/concesionario";
    private static final String user = "root";
    private static final String pass = "";
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

    public void buscarCoche(Taller taller) {

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

}
