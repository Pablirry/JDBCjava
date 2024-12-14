package Model;

import java.util.List;

public class DestinoUrbano extends Destino {

	private int numMuseos;

	/**
	 * Constructor de la clase que hereda de Destino
	 * 
	 * @param nombre                  : String
	 * @param descripcion             : String
	 * @param region                  : String
	 * @param clima                   : String
	 * @param actividadesRecomendadas : Lista
	 * @param numMuseos               : entero
	 */

	public DestinoUrbano(String nombre, String descripcion, String region, String clima, String tipo,
			List<String> actividadesRecomendadas, int numMuseos) {
		super(nombre, descripcion, region, clima, tipo, actividadesRecomendadas);
		this.numMuseos = numMuseos;
	}

	/**
	 * Metodo get para coger el numero de museos
	 * 
	 * @return : entero
	 */

	public int getNumMuseos() {
		return numMuseos;
	}

	/**
	 * Metodo mostrar detalles para mostrar cuantos museos hay
	 */

	@Override
	public void mostrarDetalles() {
		super.mostrarDetalles();
		System.out.println("Numero de museos: " + numMuseos);
	}

}
