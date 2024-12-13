package Interface;

import Model.Actividad;
import Model.Destino;
import Persistence.CsvReader;
import Persistence.GestorJDBC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

	private static List<Destino> destinos = new ArrayList<>();
	private static List<Actividad> actividades = new ArrayList<>();

	public static void main(String[] args) {
		try {
			GestorJDBC dbManager = new GestorJDBC();
			Scanner sc = new Scanner(System.in);

			destinos = CsvReader.cargarDestinosCsv("destinos.csv", destinos);
			actividades = CsvReader.cargarActividadesCsv("actividades.csv", actividades);

			
			dbManager.sincronizarActividadesDesdeCsv(actividades);
			dbManager.sincronizarDestinosDesdeCsv(destinos);

			CsvReader.guardarDestinosCsv("destinos.csv", dbManager.listarDestinos());
			CsvReader.guardarActividadesCsv("actividades.csv", dbManager.listarActividades());

			int opcion;
			do {
				mostrarMenu();
				opcion = Integer.parseInt(sc.nextLine().trim());

				switch (opcion) {
				case 1 -> listarDestinos(dbManager);
				case 2 -> listarActividades(dbManager);
				case 3 -> insertarDestino(dbManager, sc);
				case 4 -> insertarActividad(dbManager, sc);
				case 5 -> eliminarDestino(dbManager, sc);
				case 6 -> eliminarActividad(dbManager, sc);
				case 7 -> actualizarDestino(dbManager, sc);
				case 0 -> System.out.println("Saliendo de la aplicación...");
				default -> System.out.println("Opción no válida.");
				}
			} while (opcion != 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para mostrar menu de opciones
	 */

	private static void mostrarMenu() {
		System.out.println("\n---- Turismo Database App ----");
		System.out.println("1. Listar destinos");
		System.out.println("2. Listar actividades");
		System.out.println("3. Insertar destino");
		System.out.println("4. Insertar actividad");
		System.out.println("5. Eliminar destino");
		System.out.println("6. Eliminar actividad");
		System.out.println("7. Actualizar destino");
		System.out.println("0. Salir");
	}

	/**
	 * Metodo para listar los destinos
	 * 
	 * @param dbManager
	 * @throws SQLException
	 */

	private static void listarDestinos(GestorJDBC dbManager) throws SQLException {
		destinos = dbManager.listarDestinos();
		System.out.println("\n---- Lista de Destinos ----");
		for (Destino destino : destinos) {
			System.out.println(destino);
		}
	}

	/**
	 * Metodo para listar las actividades
	 * 
	 * @param dbManager
	 * @throws SQLException
	 */

	private static void listarActividades(GestorJDBC dbManager) throws SQLException {
		actividades = dbManager.listarActividades();
		System.out.println("\n---- Lista de Actividades ----");
		for (Actividad actividad : actividades) {
			System.out.println(actividad);
		}
	}

	/**
	 * Metodo para insertar destinos
	 * 
	 * @param dbManager
	 * @param sc
	 * @throws SQLException
	 */

	private static void insertarDestino(GestorJDBC dbManager, Scanner sc) throws SQLException {
		System.out.print("Nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Descripción: ");
		String descripcion = sc.nextLine();
		System.out.print("Región: ");
		String region = sc.nextLine();
		System.out.print("Clima: ");
		String clima = sc.nextLine();
		System.out.print("Tipo: ");
		String tipo = sc.nextLine();
		Destino destino = new Destino(nombre, descripcion, region, clima, List.of("Actividades"));
		dbManager.insertarDestino(destino);
		destinos = dbManager.listarDestinos();
		CsvReader.guardarDestinosCsv("destinos.csv", destinos);
		System.out.println("Destino insertado con éxito.");
	}

	/**
	 * Metodo para insertar una nueva actividad
	 * 
	 * @param dbManager
	 * @param scanner
	 * @throws SQLException
	 */

	private static void insertarActividad(GestorJDBC dbManager, Scanner scanner) throws SQLException {
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Tipo: ");
		String tipo = scanner.nextLine();
		System.out.print("Precio: ");
		double precio = Double.parseDouble(scanner.nextLine());
		System.out.print("Duración: ");
		int duracion = Integer.parseInt(scanner.nextLine());
		System.out.print("Dificultad: ");
		String dificultad = scanner.nextLine();

		Actividad actividad = new Actividad(nombre, tipo, precio, duracion, dificultad);
		dbManager.insertarActividad(actividad);
		actividades = dbManager.listarActividades();
		CsvReader.guardarActividadesCsv("actividades.csv", actividades);
		System.out.println("Actividad insertada con éxito.");
	}

	/**
	 * Metodo para eliminar un destino
	 * 
	 * @param dbManager
	 * @param sc
	 * @throws SQLException
	 */

	private static void eliminarDestino(GestorJDBC dbManager, Scanner sc) throws SQLException {
		System.out.print("Nombre del destino a eliminar: ");
		String nombre = sc.nextLine();
		if (dbManager.eliminarDestino(nombre)) {
			destinos = dbManager.listarDestinos();
			CsvReader.guardarDestinosCsv("destinos.csv", destinos); 
			System.out.println("Destino eliminado con éxito.");
		} else {
			System.out.println("Destino no encontrado en la base de datos.");
		}
	}

	/**
	 * Metodo para eliminar una actividad
	 * 
	 * @param dbManager
	 * @param scanner
	 * @throws SQLException
	 */

	private static void eliminarActividad(GestorJDBC dbManager, Scanner sc) throws SQLException {
		System.out.print("Nombre de la actividad a eliminar: ");
		String nombre = sc.nextLine();
		if (dbManager.eliminarActividad(nombre)) {
			actividades = dbManager.listarActividades();
			CsvReader.guardarActividadesCsv("actividades.csv", actividades);
			System.out.println("Actividad eliminada con éxito.");
		} else {
			System.out.println("Actividad no encontrada en la base de datos.");
		}
	}

	/**
	 * Metodo para actualizar un destino
	 * 
	 * @param dbManager
	 * @param sc
	 * @throws SQLException
	 */

	private static void actualizarDestino(GestorJDBC dbManager, Scanner sc) throws SQLException {
		System.out.print("Nombre del destino a actualizar: ");
		String nombre = sc.nextLine();
		System.out.print("Nueva descripción: ");
		String nuevaDescripcion = sc.nextLine();
		dbManager.actualizarDestino(nombre, nuevaDescripcion);
		destinos = dbManager.listarDestinos();
		CsvReader.guardarDestinosCsv("destinos.csv", destinos);
		System.out.println("Destino actualizado con éxito.");
	}
}