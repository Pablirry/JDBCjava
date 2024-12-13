package Logica;

import java.sql.SQLException;
import java.util.List;

import Persistencia.GestorJDBC;

public class Ventas {

	GestorJDBC gestor;

	public Ventas() throws ClassNotFoundException, SQLException {
		gestor = new GestorJDBC();
	}

	public boolean consulta1() throws SQLException {
		String consulta = "CREATE TABLE IF NOT EXISTS Login (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "nombre VARCHAR(20), " + "pass VARCHAR(10))";
		return gestor.consulta1(consulta);
	}

	public int consulta2(String[] nombres, String[] pass) throws SQLException {
		String consulta = "INSERT INTO Login (nombre, pass) VALUES (?, ?)";
		int cont = 0;
		for (int i = 0; i < nombres.length; i++) {
			if (gestor.consulta2(consulta, nombres[i], pass[i])) {
				cont++;
			}
		}

		return cont;
	}

	public String consulta3(String nombre, String pass) throws SQLException {

		String consulta = "SELECT pass FROM Login WHERE nombre = ?";
		String passG = gestor.consulta3(consulta, nombre);
		if (passG == null) {
			return "Usuario no registrado";
		} else if (!passG.equals(pass)) {
			return "Usuario clave no correcto";
		} else {
			return "Login correcto";
		}
	}

	public String consulta4() throws SQLException {

		List<String[]> detallesPedidos = gestor.consulta4();
		String texto = "";
		for (String[] detalle : detallesPedidos) {
			texto += detalle[0] + "\n\t" + detalle[1] + "\n\t" + detalle[2] + "\n";
		}
		return texto.trim();
	}

	public String consulta5() throws SQLException {
		List<String> clientes = gestor.consulta5();
		String texto = "Total clientes que han realizado pedidos: " + clientes.size() + "\n";
		for (String cliente : clientes) {
			texto += cliente + "\n";
		}
		return texto.trim();
	}

	public String consulta6() throws SQLException {
		return gestor.consulta6();
	}

	public String consulta7() throws SQLException {
		List<String[]> comercialesSinPedidos = gestor.consulta7();
		String texto = "Comerciales sin ningún pedido: " + comercialesSinPedidos.size() + "\n";
		for (String[] comercial : comercialesSinPedidos) {
			texto += "Comercial [id=" + comercial[0] +
					", nombre=" + comercial[1] +
					", apellido1=" + comercial[2] +
					", apellido2=" + comercial[3] +
					", ciudad=" + comercial[4] +
					", comision=" + comercial[5] + "]\n";
		}
		return texto.trim();
	}

	public String consultaTablasYColumnas() throws SQLException {
		List<String> tablas = gestor.obtenerNombresTablas();
		String resul = "";

		for (String tabla : tablas) {
			resul += "Tabla: " + tabla + "\n";
			List<String[]> columnas = gestor.descripcionTabla(tabla);
			for (String[] columna : columnas) {
				resul += "\tColumna: " + columna[0] +
						", Tipo: " + columna[1] +
						", Null: " + columna[2] +
						", Key: " + columna[3] +
						", Default: " + columna[4] +
						", Extra: " + columna[5] + "\n";
			}
			resul += "\n";
		}

		return resul.trim();
	}
}
