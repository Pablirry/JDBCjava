package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import Excepciones.PersistenciaError;

public class JDBC_PropietarioDAO implements IPropietarioDAO {

	@Override
	public boolean insertarPropietario(String infoPropietario) {
		String trozos[]=infoPropietario.split(";");
		String sentencia="insert into propietario values(?,?,?)";
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			PreparedStatement ps=con.prepareStatement(sentencia);
			ps.setString(1, trozos[0]);
			ps.setString(2, trozos[1]);
			ps.setInt(3, Integer.parseInt(trozos[2]));
			if(ps.executeUpdate()!=1) {
				throw new PersistenciaError("ERROR al insertar coche");
			}
			ps.close();
			con.close();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		} catch (PersistenciaError e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		} 
	}

	@Override
	public boolean actualizarPropietario(String dni, String infoPropietario) {
		if (borrarPropietario(dni)==true &&
				insertarPropietario(infoPropietario)==true) return true;
		else return false; 
	}

	@Override
	public String listarTodosLosPropietarios() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="select * from propietario";
			ResultSet rs=st.executeQuery(select);
			String resul="";
			while(rs.next()) {
				String dni=rs.getString(1);
				String nombre=rs.getString(2);
				int edad=rs.getInt(3);
				
				
				String linea=dni+";"+nombre+";"+edad+"\n";
				resul+=linea;
			}
			rs.close();
			st.close();
			con.close();
			return resul;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "";
		} 
	}

	@Override
	public boolean borrarPropietario(String dni) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="delete from propietario where dni='"+dni+"'";
			if(st.executeUpdate(select)!=1) return false;
			
			st.close();
			con.close();
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return true;
		} 
	}

	@Override
	public String buscarPropietario(String dni) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="select * from propietario where dni='"+dni+"'";
			ResultSet rs=st.executeQuery(select);
			rs.next();
			if(rs.getRow()!=1) {
				rs.close();
				st.close();
				con.close();
				return "";
			}
			String dniP=rs.getString(1);
			String nombre=rs.getString(2);
			int edad=rs.getInt(3);
								
			String resul=dni+";"+nombre+";"+edad+"\n";
			
			rs.close();
			st.close();
			con.close();
			
			return resul;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "";
		} 
	}

}
