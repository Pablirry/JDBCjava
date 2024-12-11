package Model;

public class ActividadAventura extends Actividad {
	
	private boolean equipoIncl;
	
	/**
	 * Constructor de la clase que hereda de Actividad
	 * @param nombre : String
	 * @param precio : real
	 * @param duracion : entero
	 * @param dificultad : String
	 * @param equipoIncl : boolean
	 */

	public ActividadAventura(String nombre, double precio, int duracion, String dificultad, boolean equipoIncl) {
		super(nombre, "Aventura", precio, duracion, dificultad);
		this.equipoIncl = equipoIncl;
	}
	
	/**
	 * Metodo get del atributo EquipoIncluido
	 * @return : boolean
	 */

	public boolean isEquipoIncl() {
		return equipoIncl;
	}
	
	/**
	 * Metodo mostrar detalles para mostrar si el quipo esta incluido o no
	 */

	@Override
	public void mostrarDetalles() {
		super.mostrarDetalles();
		System.out.println("Equipo incluido: " +(equipoIncl ? "Si" : "No"));
	}

}
