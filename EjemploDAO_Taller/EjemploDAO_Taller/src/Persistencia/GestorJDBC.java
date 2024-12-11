package Persistencia;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Excepciones.LogicaError;
import Logica.Coche;
import Logica.Propietario;
import Logica.Taller;

public class GestorJDBC {

	IPropietarioDAO pDao;
	ICocheDAO cDao;
	
	public GestorJDBC() {
		// TODO Auto-generated constructor stub
		pDao=new JDBC_PropietarioDAO();
		cDao=new JDBC_CocheDAO();
	}

	public void cargarDatos(Taller taller) throws LogicaError {
		// TODO Auto-generated method stub
		leerPropietarios(taller);
		leerCoches(taller);
	}

	private void leerPropietarios(Taller taller) {
		
		String datos=pDao.listarTodosLosPropietarios();
		String reg[]=datos.split("\n");
		for(int i=0;i<reg.length;i++) {
			String trozos[]=reg[i].split(";");
			String dni=trozos[0];
			String nombre=trozos[1];
			int edad=Integer.parseInt(trozos[2]);
			Propietario p=new Propietario(dni, nombre, edad);
			taller.addPropietario(p);
		}
	}

	private void leerCoches(Taller taller) throws LogicaError {
		
		String datos=cDao.listarTodosLosCoches();
		String[] reg=datos.split("\n");
		for(int i=0;i<reg.length;i++) {
			String trozos[]=reg[i].split(";");
			String matricula=trozos[0];
			String marcar=trozos[1];
			int precio=Integer.parseInt(trozos[2]);
			String dni=trozos[3];
			Coche c=new Coche(matricula, marcar, precio);
			taller.addCoche(c, dni);
		}
		
	}

	public void borrarCoche(String matricula) {
		cDao.borrarCoche(matricula);
	}

	public void borrarPropietario(String dni) {
		// TODO Auto-generated method stub
		pDao.borrarPropietario(dni);
	}

	public String buscarPropietario(String d) {
		// TODO Auto-generated method stub
		return pDao.buscarPropietario(d);
	}

	public String buscarCoche(String m) {
		// TODO Auto-generated method stub
		return cDao.buscarCoche(m);
	}

	public String getInfo() {
		String resul="";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			DatabaseMetaData dbMet = con.getMetaData();
	        if (dbMet==null){
	            resul+="No hay información de MetaData";
	        } 
	        else { 
	            resul+="Tipo de la BD: " + dbMet.getDatabaseProductName()+"\n";
	            resul+="Versión : " + dbMet.getDatabaseProductVersion()+"\n"; 
	            resul+="Cantidad máxima de conexiones activas: " + dbMet.getMaxConnections()+"\n"; 
	        }
	        
	        
	        String[] types = {"TABLE"};
	        ResultSet rs=dbMet.getTables(con.getCatalog(), null, "%", types);
	        
	        while(rs.next()) {
	        	String tabla=rs.getString("TABLE_NAME")+"\n";
	        	resul+="TABLA : "+tabla+"\n";
	        	resul+=infoTabla(tabla);
	        }
	        
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

	private String infoTabla(String tabla) {
		// TODO Auto-generated method stub
		String resul="";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(""
			 		+ "jdbc:mysql://localhost/concesionario", "root", "root");
			Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from " + tabla);
            //Obtiene el metadata del ResultSet   
            ResultSetMetaData rsmeta = rs.getMetaData();               
            //Obtiene la cantidad de columnas del ResultSet               
            int col = rsmeta.getColumnCount();               
            for (int i = 1; i <= col; i++) {    
                //Devuelve el nombre del campo i 
                String nombreCampo_i = rsmeta.getColumnLabel(i); 
                //devuelve el tipo del campo i
                String tipoCampo_i = rsmeta.getColumnTypeName(i);
                resul+="\tCampo " + nombreCampo_i + " Tipo "+tipoCampo_i+"\n";                      
            }    
	        
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

	public void insertarPropietario(String infoP) {
		pDao.insertarPropietario(infoP);
	}
	
	
	
		
	

}
