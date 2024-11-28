package Persistence;

import java.sql.*;

import Logic.Coche;
import Logic.Propietario;
import Logic.Taller;

public class GestionConsultas {

    private static final String url = "jdbc:mysql://localhost/concesionario";
    private static final String user = "root";
    private static final String pass = "";

    public void leerCoches(Taller taller) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
                st = con.createStatement();
                String consulta = "SELECT * FROM coche";
                rs = st.executeQuery(consulta);

                while(rs.next()) {
                    // extraemos campos
                    String matricula = rs.getString(1);
                    String marca = rs.getString(2);
                    int precio = rs.getInt(3);
                    String dni = rs.getString(4);

                    taller.addCoche(new Coche(matricula, marca, precio, dni));
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void actualizarCoches(Taller taller) {
        

    }

    public void leerPropietarios(Taller taller) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
                st = con.createStatement();
                String consulta = "SELECT * FROM propietario";
                rs = st.executeQuery(consulta);

                while(rs.next()) {
                    // extraemos campos
                    String dni = rs.getString(1);
                    String nombre = rs.getString(2);
                    int edad = rs.getInt(3);

                    taller.addPropietario(new Propietario(dni, nombre, edad));
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void actualizarPropietarios(Taller taller) {

    }

}
