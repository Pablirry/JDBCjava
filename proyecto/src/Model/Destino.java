package Model;

import java.util.*;

import Logic.Mostrable;

public class Destino implements Mostrable{
	
	private String nombre, descripcion, region, clima, tipo;
	private List<String> actividadesRecomendadas;
	
	/**
	 * Constructor de la clase con todos los parametros
	 * @param nombre : String
	 * @param descripcion : String
	 * @param region : String
	 * @param clima : String
	 * @param actividadesRecomendadas : Lista
	 */

	public Destino(String nombre, String descripcion, String region, String clima,
			List<String> actividadesRecomendadas) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.region = region;
		this.clima = clima;
		this.actividadesRecomendadas = actividadesRecomendadas;
	}
	
	/**
	 * Metodo get del atributo Tipo
	 * @return : String
	 */
	
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Metodo get del atributo Nombre
	 * @return : String
	 */

	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Metodo get del atributo Descripcion
	 * @return : String
	 */

	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Metodo get del atributo Region
	 * @return : String
	 */

	public String getRegion() {
		return region;
	}
	
	/**
	 * Metodo get del atributo Clima
	 * @return : String
	 */

	public String getClima() {
		return clima;
	}
	
	/**
	 * Metodo get del atributo Actividades recomendadas
	 * @return : List
	 */

	public List<String> getActividadesRecomendadas() {
		return actividadesRecomendadas;
	}
	
	/**
	 * Metodo para mostrar las actividades recomendadas
	 */
	
	public void mostrarActividadesRecomendadas() {
		System.out.println("Actividades recomendadas en " + nombre + ":");
		for (String actividad : actividadesRecomendadas) {
			System.out.println("- " +actividad);
		}
	}
	
	/**
	 * Metodo toString
	 */

	@Override
	public String toString() {
		 return tipo + "Destino: " + nombre + "\nDescripción: " + descripcion + "\nRegión: " + region + "\nClima: " + clima;
	}
	
	/**
	 * Metodo de la interfaz para mostar los detalles
	 */

	@Override
	public void mostrarDetalles() {
		System.out.println(this.toString());
		
	}

}
