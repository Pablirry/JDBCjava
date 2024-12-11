package Model;

public class ActividadCultural extends Actividad {
	
	private String idioma;
	
	/**
	 * Constructor de la clase que hereda de Actividad
	 * @param nombre : String
	 * @param precio : real
	 * @param duracion : entero
	 * @param dificultad : String
	 * @param idioma : String
	 */
	
	public ActividadCultural(String nombre, double precio, int duracion, String dificultad, String idioma) {
		super(nombre,  "Cultural", precio, duracion, dificultad);
		this.idioma = idioma;
	}
	
	/**
	 * Metodo get del atributo Idioma
	 * @return : String
	 */

	public String getIdioma() {
		return idioma;
	}
	
	/**
	 * Metodo mostrar detalles para mostrar en que idioma esta
	 */
	
	@Override
	public void mostrarDetalles() {
		super.mostrarDetalles();
		System.out.println("Disponible en: " + idioma);
	}

}
