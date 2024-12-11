package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Excepciones.PersistenciaError;

public class JDBC_CocheDAO implements ICocheDAO {

	@Override
	public boolean insertarCoche(String infoCoche) {
		// TODO Auto-generated method stub
		String trozos[]=infoCoche.split(";");
		String sentencia="insert into coche values(?,?,?,?)";
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			PreparedStatement ps=con.prepareStatement(sentencia);
			ps.setString(1, trozos[0]);
			ps.setString(2, trozos[1]);
			ps.setInt(3, Integer.parseInt(trozos[2]));
			ps.setString(4,trozos[3]);
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
	public boolean actualizarCoche(String matricula, String infoCoche) {
		if (borrarCoche(matricula)==true &&
				insertarCoche(infoCoche)==true) return true;
		else return false; 
	}

	@Override
	public String listarTodosLosCoches() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="select * from coche";
			ResultSet rs=st.executeQuery(select);
			String resul="";
			while(rs.next()) {
				String matricula=rs.getString(1);
				String marca=rs.getString(2);
				int precio=rs.getInt(3);
				String dni=rs.getString(4);
				
				String linea=matricula+";"+marca+";"+precio+";"+dni+"\n";;
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
	public String listarCocheDni(String dni) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="select * from coche where dni='"+dni+"'";
			ResultSet rs=st.executeQuery(select);
			String resul="";
			while(rs.next()) {
				String matricula=rs.getString(1);
				String marca=rs.getString(2);
				int precio=rs.getInt(3);
				String dniP=rs.getString(4);
				
				String linea=matricula+";"+marca+";"+precio+";"+dniP+"\n";;
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
	public boolean borrarCoche(String matricula) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="delete from coche where matricula='"+matricula+"'";
			if(st.executeUpdate(select)!=1) {
				
				st.close();
				con.close();
				return false;
			}

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
	public String buscarCoche(String matricula) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st=con.createStatement();
			String select="select * from coche where matricula='"+matricula+"'";
			ResultSet rs=st.executeQuery(select);
			
			String resul="";
			rs.next();
			if(rs.getRow()!=1) {
				return resul;
			}
			String matriculaR=rs.getString(1);
			String marcaR=rs.getString(2);
			int precio=rs.getInt(3);
			String dni=rs.getString(4);
			resul=matriculaR+";"+marcaR+";"+precio+";"+dni+"\n";
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
