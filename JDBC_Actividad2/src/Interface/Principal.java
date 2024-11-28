package Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Logic.Taller;

public class Principal {

	

	public static void main(String[] args) {

		Taller taller = new Taller();
		taller.insertarNuevoPropietario("2C", "Pepe_2", 30);
		taller.insertarNuevoPropietario("3C", "Pepe_3", 30);
		taller.insertarNuevoPropietario("4C", "Pepe_4", 30);
		
		/*
		taller.insertarNuevoCoche("XX-1111", "Marca", 10000, "2C");
		taller.insertarNuevoCoche("XX-2222", "Marca", 10000, "2C");
		taller.insertarNuevoCoche("XX-3333", "Marca", 10000, "2C");

		taller.borrarCoche("XX-3333");
		*/

		taller.borrarPropietario("3C");
		



		
	}

}
