package Interface;

import java.util.*;

import Exceptions.ArchivoNoEncontradoException;
import Exceptions.OpcionInvalidaException;
import Log.Logger;
import Model.*;
import Persistence.CsvReader;

public class TurismoApp {
	
	private static Scanner sc = new Scanner(System.in);
	private static List<Destino> destinos = new ArrayList<>();
    private static List<Actividad> actividades = new ArrayList<>();

    public static void main(String[] args) {
    	try {
            CsvReader.cargarDestinosCsv("destinos.csv", destinos);
            CsvReader.cargarActividadesCsv("actividades.csv", actividades);

            int opcion = -1;

            do {
                mostrarMenu();
                System.out.print("Elige una opción: ");
                String entrada = sc.nextLine(); 

                try {
                    opcion = Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingresa un número válido.");
                    continue;
                }

                try {
                    switch (opcion) {
                        case 1 -> mostrarDestinos();
                        case 2 -> mostrarActividades();
                        case 3 -> crearDestino();
                        case 4 -> crearActividad();
                        case 5 -> borrarDestino();
                        case 6 -> borrarActividad();
                        case 7 -> mostrarActividadesTipo();
                        case 8 -> mostrarDestinosTipo();
                        case 9 -> guardarDestinosEnCsv();
                        case 10 -> guardarActividadesEnCsv();
                        case 0 -> System.out.println("Saliendo de la aplicación...");
                        default -> throw new OpcionInvalidaException("Opción no válida.");
                    }
                } catch (OpcionInvalidaException e) {
                    System.out.println("Opcion no valida");
                    Logger.logError("Opcion invalida");
                }

            } while (opcion != 0);

        } catch (ArchivoNoEncontradoException e) {
            System.out.println("Error al cargar archivos CSV: ");
            Logger.logError("Error en el archivo");
        }
    }
    
    /**
     * Metodo para mostrar el menu de la app
     */

	private static void mostrarMenu() {
        System.out.println("\n---- Turismo en Asturias ----");
        System.out.println("1. Ver destinos turísticos");
        System.out.println("2. Ver actividades disponibles");
        System.out.println("3. Crear un nuevo destino");
        System.out.println("4. Crear una nueva actividad");
        System.out.println("5. Borrar un destino");
        System.out.println("6. Borrar una actividad");
        System.out.println("7. Ver actividades por tipo (aventura/cultural)");
        System.out.println("8. Ver destinos por tipo (natural/urbano)");
        System.out.println("9. Guardar destinos en CSV");
        System.out.println("10. Guardar actividades en CSV");
        System.out.println("0. Salir");
    }
	
	/**
	 * Metodo para mostar todos los destinos
	 */

    private static void mostrarDestinos() {
        System.out.println("\n---- Destinos Turísticos ----");
        for (Destino destino : destinos) {
            destino.mostrarDetalles();
            System.out.println();
        }
    }
    
    /**
     * Metodo para mostar todas las actividades
     */

    private static void mostrarActividades() {
        System.out.println("\n---- Actividades Disponibles ----");
        for (Actividad actividad : actividades) {
            actividad.mostrarDetalles();
            System.out.println();
        }
    }
    
    /**
     * Metodo para crear un nuevo destino
     */

    private static void crearDestino() {
    	System.out.print("Nombre del destino: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();
        System.out.print("Región: ");
        String region = sc.nextLine();
        System.out.print("Clima: ");
        String clima = sc.nextLine();

        System.out.print("¿Es un destino urbano? (sí/no): ");
        String tipo = sc.nextLine();
        if (tipo.equalsIgnoreCase("sí")) {
            System.out.print("Número de museos: ");
            int numeroDeMuseos = sc.nextInt();
            sc.nextLine();
            destinos.add(new DestinoUrbano(nombre, descripcion, region, clima, List.of("Museo"), numeroDeMuseos));
        } else {
            System.out.print("Área protegida (km²): ");
            double areaProtegida = sc.nextDouble();
            sc.nextLine();
            destinos.add(new DestinoNatural(nombre, descripcion, region, clima, List.of("Senderismo"), areaProtegida));
        }
        System.out.println("Destino creado con éxito.");

        guardarDestinosEnCsv();
    }
    
    /**
     * Metodo para crear una nueva actividad
     */

    private static void crearActividad() {
        System.out.print("Nombre de la actividad: ");
        String nombre = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble();
        System.out.print("Duración (horas): ");
        int duracion = sc.nextInt();
        sc.nextLine();
        System.out.print("Nivel de dificultad: ");
        String nivelDeDificultad = sc.nextLine();

        System.out.print("¿Es una actividad de aventura? (sí/no): ");
        String tipo = sc.nextLine();
        if (tipo.equalsIgnoreCase("sí")) {
            actividades.add(new ActividadAventura(nombre, precio, duracion, nivelDeDificultad, true));
        } else {
            System.out.print("Idioma del guía: ");
            String idiomaGuia = sc.nextLine();
            actividades.add(new ActividadCultural(nombre, precio, duracion, nivelDeDificultad, idiomaGuia));
        }
        System.out.println("Actividad creada con éxito.");
        
        guardarActividadesEnCsv();
    }
    
    /**
     * Metodo para borrar un destino
     */

    private static void borrarDestino() {
        System.out.print("Nombre del destino a borrar: ");
        String nombre = sc.nextLine();
        destinos.removeIf(destino -> destino.getNombre().equalsIgnoreCase(nombre));
        System.out.println("Destino borrado con éxito.");
    }
    
    /**
     * Método para borrar una actividad
     */

    private static void borrarActividad() {
        System.out.print("Nombre de la actividad a borrar: ");
        String nombre = sc.nextLine();
        actividades.removeIf(actividad -> actividad.getNombre().equalsIgnoreCase(nombre));
        System.out.println("Actividad borrada con éxito.");
    }
    
    /**
     * Metodo para guardar los nuevos destinos en csv
     */

    private static void guardarDestinosEnCsv() {
        CsvReader.guardarDestinosCsv("data/nuevodestinos.csv", destinos);
		System.out.println("Destinos guardados correctamente en destinos.csv");
    }
    
    /**
     * Metodo para guardar las nuevas actividades en csv
     */
    
    private static void guardarActividadesEnCsv() {
        CsvReader.guardarActividadesCsv("data/nuevaactividades.csv", actividades);
		System.out.println("Actividades guardadas correctamente en actividades.csv");
    }
    
    /**
     * Método para mostrar los destinos por tipo
     */
    
    private static void mostrarDestinosTipo() {
    	System.out.println("Que destinos quieres ver (natural/urbano): ");
    	String tipo = sc.nextLine().toLowerCase();
    	
    	System.out.println("\n---- Destinos " + tipo + " ----");
    	boolean encontrado = false;
    	for (Destino destino : destinos) {
    		if(tipo.equals("natural") && destino instanceof DestinoNatural) {
    			System.out.println(destino + "\n");
    			encontrado = true;
    		} else if (tipo.equals("urbano") && destino instanceof DestinoUrbano) {
    			System.out.println(destino + "\n");
    			encontrado = true;
    		}
    	}
    	
    	if (!encontrado) {
            System.out.println("No se encontraron destinos de tipo " + tipo + ".");
            Logger.logError("Destinos no encontrados");
        }    	
    }
    
    /**
     * Método para mostrar las actividades por tipo
     */
    
    private static void mostrarActividadesTipo() {
    	System.out.print("¿Qué tipo de actividades deseas ver? (aventura/cultural): ");
        String tipo = sc.nextLine().toLowerCase();

        System.out.println("\n---- Actividades " + tipo + " ----");
        boolean encontrado = false;
        for (Actividad actividad : actividades) {
            if (tipo.equals("aventura") && actividad instanceof ActividadAventura) {
                System.out.println(actividad+"\n");
                encontrado = true;
            } else if (tipo.equals("cultural") && actividad instanceof ActividadCultural) {
                System.out.println(actividad+"\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron actividades de tipo " + tipo + ".");
            Logger.logError("Actividades no encontradas");
        }
	}
    
}

