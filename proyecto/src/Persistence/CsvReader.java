package Persistence;

import java.io.*;
import java.util.List;

import Exceptions.ArchivoNoEncontradoException;
import Model.*;

public class CsvReader {

	private static final String DATA = "data/";

	/**
	 * Carga destinos desde un archivo CSV y los agrega a la lista de destinos
	 * 
	 * @param archivo  : String
	 * @param destinos : Lista
	 * @return
	 * @throws ArchivoNoEncontradoException : Exception
	 */

	public static List<Destino> cargarDestinosCsv(String archivo, List<Destino> destinos)
			throws ArchivoNoEncontradoException {

		File file = new File(DATA + archivo);
	    if (!file.exists()) {
	        System.out.println("Archivo " + archivo + " no encontrado, iniciando vacío.");
	        return destinos;
	    }

	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
	        String linea;
	        boolean primeraLinea = true;
	        while ((linea = br.readLine()) != null) {
	            if (primeraLinea) {
	                primeraLinea = false;
	                continue;
	            }
	            
	            linea = linea.trim();
	            String[] datos = linea.split(";");
	            if (datos.length < 5) {
	                System.out.println("Línea inválida en el CSV: " + linea);
	                continue;
	            }

	            String nombre = datos[0].trim();
	            String descripcion = datos[1].trim();
	            String region = datos[2].trim();
	            String clima = datos[3].trim();
	            String tipo = datos[4].trim();

	            destinos.add(new Destino(nombre, descripcion, region, clima, tipo, List.of()));
	        }
	    } catch (IOException e) {
	        throw new ArchivoNoEncontradoException("Error al leer el archivo");
	    }
	    return destinos;
	}

	/**
	 * Carga actividades desde un archivo CSV y las agrega a la lista de actividades
	 * 
	 * @param archivo     : String
	 * @param actividades : Lista
	 * @return
	 * @throws ArchivoNoEncontradoException : Exception
	 */

	public static List<Actividad> cargarActividadesCsv(String archivo, List<Actividad> actividades)
	        throws ArchivoNoEncontradoException {
	    try (BufferedReader br = new BufferedReader(new FileReader(DATA + archivo))) {
	        String linea;

	        br.readLine(); 

	        while ((linea = br.readLine()) != null) {
	            String[] datos = linea.split(";");
	            String tipoActividad = datos[0];
	            String nombre = datos[1];
	            
	            String precioString = datos[2].replace(",", ".");
	            double precio = Double.parseDouble(precioString);
	            
	            int duracion = Integer.parseInt(datos[3]);
	            String nivelDeDificultad = datos[4];

	            if (tipoActividad.equalsIgnoreCase("aventura")) {
	                actividades.add(new ActividadAventura(nombre, precio, duracion, nivelDeDificultad, true));
	            } else if (tipoActividad.equalsIgnoreCase("cultural")) {
	                actividades.add(new ActividadCultural(nombre, precio, duracion, nivelDeDificultad, "Español"));
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error al leer el archivo de actividades: " + archivo);
	    }
	    return actividades;
	}

	/**
	 * Guarda actividades en un archivo CSV
	 * 
	 * @param archivo     : String
	 * @param actividades : Lista
	 */

	public static void guardarActividadesCsv(String archivo, List<Actividad> actividades) {

		try (PrintWriter pw = new PrintWriter(new FileWriter(DATA + archivo))) {

			pw.println("Tipo;Nombre;Precio;Duración;Nivel de Dificultad");

			for (Actividad actividad : actividades) {
				pw.printf("%s;%s;%.2f;%d;%s%n", actividad.getTipo(), actividad.getNombre(), actividad.getPrecio(),
						actividad.getDuracion(), actividad.getDificultad());
			}
		} catch (IOException e) {
			System.out.println("Error al guardar actividades");
		}
	}

	/**
	 * Guarda destinos en un archivo CSV
	 * 
	 * @param archivo  : String
	 * @param destinos : Lista
	 */

	public static void guardarDestinosCsv(String archivo, List<Destino> destinos) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(DATA + archivo))) {
	        pw.println("Nombre;Descripción;Región;Clima;Tipo");
	        for (Destino destino : destinos) {
	            pw.printf("%s;%s;%s;%s;%s%n",
	                      destino.getTipo(), destino.getNombre(), destino.getDescripcion(),
	                      destino.getRegion(), destino.getClima());
	        }
	    } catch (IOException e) {
	        System.out.println("Error al guardar destinos en el archivo CSV");
	    }
	}

}
