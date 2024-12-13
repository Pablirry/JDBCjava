package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorJDBC {

	private Connection con;
	private Statement st;

	public GestorJDBC() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/ventas", "root", "root");
		System.out.println("Base conectada ");
	}

	public boolean consulta1(String consulta) throws SQLException {
		Statement st = con.createStatement();
		st.execute(consulta);
		return true;
	}

	public boolean consulta2(String consulta, String nombre, String pass) throws SQLException {
		PreparedStatement ps = con.prepareStatement(consulta);
		ps.setString(1, nombre);
		ps.setString(2, pass);
		ps.executeUpdate();
		return true;

	}

	public String consulta3(String consulta, String nombre) throws SQLException {
		PreparedStatement ps = con.prepareStatement(consulta);
		ps.setString(1, nombre);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getString("pass");
		}

		return null;
	}

	public List<String[]> consulta4() throws SQLException {
		String consulta = """
				SELECT p.id AS id_pedido, p.total, p.fecha,
				    	c.id AS id_cliente, c.nombre AS nombre_cliente, c.apellido1 AS apellido1_cliente,
				        c.apellido2 AS apellido2_cliente, c.ciudad AS ciudad_cliente, c.categoria AS categoria_cliente,
				        com.id AS id_comercial, com.nombre AS nombre_comercial, com.apellido1 AS apellido1_comercial,
				        com.apellido2 AS apellido2_comercial, com.ciudad AS ciudad_comercial, com.comision AS comision_comercial
				       FROM pedido AS p
				       JOIN cliente AS c ON p.id_cliente = c.id
				       JOIN comercial AS com ON p.id_comercial = com.id """;
		Statement ts = con.createStatement();
		ResultSet rs = ts.executeQuery(consulta);
		List<String[]> datos = new ArrayList<>();

		while (rs.next()) {
			datos.add(new String[] {

					"Pedido [idPedido=" + rs.getInt(1) +
							", cantidad=" + rs.getDouble(2) +
							", fecha=" + rs.getDate(3) + "]",
					"Comercial [id=" + rs.getInt(10) +
							", nombre=" + rs.getString(11) +
							", apellido1=" + rs.getString(12) +
							", apellido2=" + rs.getString(13) +
							", ciudad=" + rs.getString(14) +
							", comision=" + rs.getFloat(15) + "]",
					"Cliente [idCliente=" + rs.getInt(4) +
							", nombre=" + rs.getString(5) +
							", apellido1=" + rs.getString(6) +
							", apellido2=" + rs.getString(7) +
							", ciudad=" + rs.getString(8) +
							", categoria=" + rs.getInt(9) + "]"
			});
		}
		return datos;

	}

	public List<String> consulta5() throws SQLException {
		String consulta = "SELECT DISTINCT c.id, c.nombre, c.apellido1, c.apellido2, "
				+ "c.ciudad, c.categoria "
				+ "FROM cliente AS c "
				+ "INNER JOIN pedido AS p ON c.id = p.id_cliente";

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		List<String> clientes = new ArrayList<>();

		while (rs.next()) {
			clientes.add("Cliente [idCliente=" + rs.getInt(1) +
					", nombre=" + rs.getString(2) +
					", apellido1=" + rs.getString(3) +
					", apellido2=" + rs.getString(4) +
					", ciudad=" + rs.getString(5) +
					", categoria=" + rs.getInt(6) + "]");
		}
		return clientes;

	}

	public String consulta6() throws SQLException {
		String consulta = """
					SELECT p.id, p.total, p.fecha,
						   c.id, c.nombre, c.apellido1, c.apellido2, c.ciudad, c.categoria,
						   com.id, com.nombre, com.apellido1, com.apellido2, com.ciudad, com.comision
					FROM pedido AS p
					JOIN cliente AS c ON p.id = c.id
					JOIN comercial AS com ON p.id = com.id
					ORDER BY p.total DESC
					LIMIT 1;
				""";

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		if (rs.next()) {
			return "Pedido [idPedido=" + rs.getInt(1) +
					", cantidad=" + rs.getDouble(2) +
					", fecha=" + rs.getDate(3) +
					", idCliente=" + rs.getInt(4) +
					", idComercial=" + rs.getInt(10) + "]" +
					"\n\tCliente [idCliente=" + rs.getInt(4) +
					", nombre=" + rs.getString(5) +
					", apellido1=" + rs.getString(6) +
					", apellido2=" + rs.getString(7) +
					", ciudad=" + rs.getString(8) +
					", categoria=" + rs.getInt(9) + "]" +
					"\n\tComercial [id=" + rs.getInt(10) +
					", nombre=" + rs.getString(11) +
					", apellido1=" + rs.getString(12) +
					", apellido2=" + rs.getString(13) +
					", ciudad=" + rs.getString(14) +
					", comision=" + rs.getFloat(15) + "]";
		}
		return "No se encontró ningún pedido.";
	}

	public List<String[]> consulta7() throws SQLException {
		String consulta = """
					SELECT com.id, com.nombre, com.apellido1, com.apellido2, com.ciudad, com.comision
					FROM comercial AS com
					LEFT JOIN pedido AS p ON com.id = p.id_comercial
					WHERE p.id IS NULL;
				""";

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		List<String[]> comercialesSinPedidos = new ArrayList<>();

		while (rs.next()) {
			comercialesSinPedidos.add(new String[] {
					String.valueOf(rs.getInt(1)),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					String.valueOf(rs.getFloat(6))
			});
		}
		return comercialesSinPedidos;

	}

	public List<String> obtenerNombresTablas() throws SQLException {
		String consulta = "SHOW TABLES";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		List<String> tablas = new ArrayList<>();
		while (rs.next()) {
			tablas.add(rs.getString(1)); // Nombre de la tabla
		}
		return tablas;
	}

	public List<String[]> descripcionTabla(String tabla) throws SQLException {
		String consulta = "DESCRIBE " + tabla;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		List<String[]> columnas = new ArrayList<>();
		while (rs.next()) {
			columnas.add(new String[] {
				rs.getString("Field"),
				rs.getString("Type"),
				rs.getString("Null"),
				rs.getString("Key"),
				rs.getString("Default"),
				rs.getString("Extra")
			});
		}
		return columnas;
	}

}
