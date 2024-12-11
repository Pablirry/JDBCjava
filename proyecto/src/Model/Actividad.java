package Model;

import Logic.Mostrable;

public class Actividad implements Mostrable {
	
	private String nombre, tipo;
	private double precio;
	private int duracion;
	private String dificultad;
	
	/**
	 * Constructor de la clase actividad
	 * @param nombre : String
	 * @param tipo : String
	 * @param precio : real
	 * @param duracion : entero
	 * @param dificultad : String
	 */

	public Actividad(String nombre, String tipo, double precio, int duracion, String dificultad) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.duracion = duracion;
		this.dificultad = dificultad;
	}
	
	/**
	 * Metodo get  del atributo nombre
	 * @return : String
	 */

	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Metodo get del atributo Tipo
	 * @return : String
	 */

	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Metodo get del atributo Precio
	 * @return : real
	 */

	public double getPrecio() {
		return precio;
	}
	
	/**
	 * Metodo get del atributo Duracion
	 * @return : entero
	 */

	public int getDuracion() {
		return duracion;
	}
	
	/**
	 * Metodo get del atributo Dificultad
	 * @return : String
	 */

	public String getDificultad() {
		return dificultad;
	}
	
	/**
	 * Metodo que calcula el precio por hora de la actividad
	 * @return : real
	 */
	
	public double calcularPrecioHora() {
		return precio / duracion;
	}
	
	/**
	 * Metodo toString
	 */

	@Override
	public String toString() {
		return "Actividad: " + nombre + " (" + tipo + ") - Precio: " + precio 
				+ "€, Duración: " + duracion + "h, Dificultad: " + dificultad;
	}
	
	/**
	 * Metodo de la interfaz para mostrar los detalles de las actividades
	 */

	@Override
	public void mostrarDetalles() {
		System.out.println(this.toString());
	}

}
