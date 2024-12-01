package Interface;

import Logic.Taller;

public class Principal {

	public static void main(String[] args) {

		Taller taller = new Taller();
		/*
		 * try{
		 * taller.insertarNuevoPropietario("2c", "Pepe", 30);
		 * taller.insertarNuevoPropietario("3c", "Pepe2", 30);
		 * taller.insertarNuevoPropietario("4c", "Pepe3", 30);
		 * } catch (Exception e) {
		 * System.out.println(e.toString());
		 * }
		 */
		try {
			taller.insertarNuevoCoche("XX-1111", "marca", 10000, "2c");
			taller.insertarNuevoCoche("XX-2222", "marca", 10000, "2c");
			taller.insertarNuevoCoche("XX-3333", "marca", 10000, "2c");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		taller.borrarCoche("XX-3333");

		taller.borrarPropietario("2c");
	}

}
