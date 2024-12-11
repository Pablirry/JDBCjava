package Interface;

import Logic.Taller;

public class Principal {

	public static void main(String[] args) {

		Taller taller = new Taller();
		/*
		 * try {
		 * taller.insertarNuevoPropietario("2c", "Pepe", 30);
		 * taller.insertarNuevoPropietario("3c", "Pepe2", 30);
		 * taller.insertarNuevoPropietario("4c", "Pepe3", 30);
		 * } catch (Exception e) {
		 * System.out.println(e.toString());
		 * }
		 * 
		 * try {
		 * taller.insertarNuevoCoche("XX-1111", "marca", 10000, "3c");
		 * taller.insertarNuevoCoche("XX-2222", "marca", 10000, "3c");
		 * taller.insertarNuevoCoche("XX-3333", "marca", 10000, "3c");
		 * } catch (Exception e) {
		 * System.out.println(e.toString());
		 * }
		 * 
		 * try {
		 * taller.borrarCoche("AA-0005");
		 * } catch (LogicaExcepcion e) {
		 * System.out.println("Error: propietario no encontrado");
		 * }
		 * 
		 * try {
		 * taller.borrarPropietario("4c");
		 * } catch (LogicaExcepcion | ExcepcionPersistencia | SQLException e) {
		 * System.out.println("Error propietario no encontrado");
		 * }
		 * 
		
		List<Propietario> lista = new ArrayList<Propietario>();
		lista.add(new Propietario("XXX1C", "Paco1", 30));
		lista.add(new Propietario("XXX2C", "Paco2", 70));
		lista.add(new Propietario("XXX3C", "Paco3", 30));
		lista.add(new Propietario("XXX4C", "Paco4", 40));
		lista.add(new Propietario("XXX5C", "Paco5", 50));
		taller.insertar2(lista);
		*/

		// System.out.println(taller.mostrarDatos());

		// taller.incrementaEdad();

		taller.listarInfo();



	}

}
