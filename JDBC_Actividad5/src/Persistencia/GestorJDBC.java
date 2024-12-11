package Persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.ReservasHotel;
import Model.ReservasVuelo;
import Model.Usuario;

public class GestorJDBC {

	private static final String user = "root";
	private static final String pass = "root";
	private static final String url = "jdbc:mysql://localhost/viajes";

	public GestorJDBC() {
	}

	public List<ReservasVuelo> buscarReservasVuelos(String dni) throws ClassNotFoundException, SQLException {
		List<ReservasVuelo> lista = new ArrayList<ReservasVuelo>();
		String consulta = "select * from resvuelo where DniUsuario = dni";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);
		PreparedStatement pt = con.prepareStatement(consulta);
		pt.setInt(1, Integer.parseInt(dni));

		ResultSet rs = pt.executeQuery();
		while (rs.next()) {
			int idreserva = rs.getInt(1);
			byte numHotel = rs.getByte(2);
			int dniUsuario = rs.getInt(3);
			Date fechaReserva = rs.getDate(4);
			int numPersonas = rs.getInt(5);

			ReservasVuelo rv = new ReservasVuelo(idreserva, numHotel, dniUsuario, fechaReserva, numPersonas);

			lista.add(rv);
		}
		return lista;

	}

	public List<ReservasHotel> buscarReservasHoteles(String dni) throws ClassNotFoundException, SQLException {
		List<ReservasHotel> lista = new ArrayList<ReservasHotel>();
		String consulta = "select * from reshotel where DniUsuario = dni";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);
		PreparedStatement pt = con.prepareStatement(consulta);
		pt.setInt(1, Integer.parseInt(dni));

		ResultSet rs = pt.executeQuery();
		while (rs.next()) {

			int idreserva = rs.getInt(1);
			byte numHotel = rs.getByte(2);
			int dniUsuario = rs.getInt(3);
			Date fechaReserva = rs.getDate(4);
			int numPersonas = rs.getInt(5);

			ReservasHotel rh = new ReservasHotel(idreserva, numHotel, dniUsuario, fechaReserva, numPersonas);

			lista.add(rh);

		}
		return lista;

	}

	public Usuario buscarUsuario(String dni) throws SQLException, ClassNotFoundException {

		String consulta = "select * from usuario where Dni = ?";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);

		PreparedStatement pt = con.prepareStatement(consulta);
		pt.setInt(1, Integer.parseInt(dni));

		ResultSet rs = pt.executeQuery();
		if (rs.next()) {

			int Dni = rs.getInt(1);
			String nombre = rs.getString(2);
			String apel1 = rs.getString(3);
			String apel2 = rs.getString(4);
			String dir = rs.getString(5);
			String nacionalidad = rs.getString(6);

			Usuario u = new Usuario(Dni, nombre, apel1, apel2, dir, nacionalidad);

			return u;
		}

		return null;
	}

}
