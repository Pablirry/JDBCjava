package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Error.ExcepcionPersistencia;

public class StatementSingleton {
	
	private static final String url = "jdbc:mysql://localhost/concesionario";
    private static final String user = "root";
    private static final String pass = "root";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static StatementSingleton state;
    private static Connection conexion;
    private static Statement st;

    private StatementSingleton() {
    }
    
    public static Statement getInstance() throws ExcepcionPersistencia {
        if (state == null) {
            state = new StatementSingleton();
        }

        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);

            st = conexion.createStatement();
        } catch (SQLException e) {
            throw new ExcepcionPersistencia("ERROR en la conexion");
        } catch (ClassNotFoundException e) {
            throw new ExcepcionPersistencia("ERROR en el driver");
        }
        return st;

    }

}
