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
			Scanner scanner = new Scanner(System.in);
		
            // Load initial data from CSV
            List<Destino> destinosCsv = CsvReader.cargarDestinosCsv("destinos.csv", destinos);
            List<Actividad> actividadesCsv = CsvReader.cargarActividadesCsv("actividades.csv", actividades);

			dbManager.sincronizarDestinosDesdeCsv(destinosCsv);
			dbManager.sincronizarActividadesDesdeCsv(actividadesCsv);

			int opcion;
			do {
				mostrarMenu();
				opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
				case 1 -> listarDestinos(dbManager);
				case 2 -> listarActividades(dbManager);
				case 3 -> insertarDestino(dbManager, scanner);
				case 4 -> insertarActividad(dbManager, scanner);
				case 5 -> eliminarDestino(dbManager, scanner);
				case 6 -> actualizarDestino(dbManager, scanner);
				case 0 -> System.out.println("Saliendo de la aplicación...");
				default -> System.out.println("Opción no válida.");
				}
			} while (opcion != 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mostrarMenu() {
		System.out.println("\n---- Turismo Database App ----");
		System.out.println("1. Listar destinos");
		System.out.println("2. Listar actividades");
		System.out.println("3. Insertar destino");
		System.out.println("4. Insertar actividad");
		System.out.println("5. Eliminar destino");
		System.out.println("6. Actualizar destino");
		System.out.println("0. Salir");
	}

	private static void listarDestinos(GestorJDBC dbManager) throws SQLException {
		List<Destino> destinos = dbManager.listarDestinos();
		System.out.println("\n---- Lista de Destinos ----");
		for (Destino destino : destinos) {
			System.out.println(destino);
		}
	}

	private static void listarActividades(GestorJDBC dbManager) throws SQLException {
		List<Actividad> actividades = dbManager.listarActividades();
		System.out.println("\n---- Lista de Actividades ----");
		for (Actividad actividad : actividades) {
			System.out.println(actividad);
		}
	}

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
		System.out.println("Destino insertado con éxito.");
	}

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
		System.out.println("Actividad insertada con éxito.");
	}

	private static void eliminarDestino(GestorJDBC dbManager, Scanner scanner) throws SQLException {
		System.out.print("Nombre del destino a eliminar: ");
		String nombre = scanner.nextLine();
		dbManager.eliminarDestino(nombre);
		System.out.println("Destino eliminado con éxito.");
	}

	private static void actualizarDestino(GestorJDBC dbManager, Scanner scanner) throws SQLException {
		System.out.print("Nombre del destino a actualizar: ");
		String nombre = scanner.nextLine();
		System.out.print("Nueva descripción: ");
		String nuevaDescripcion = scanner.nextLine();
		dbManager.actualizarDestino(nombre, nuevaDescripcion);
		System.out.println("Destino actualizado con éxito.");
	}

}
