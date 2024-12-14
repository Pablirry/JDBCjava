package Model;

import java.util.List;

public class DestinoNatural extends Destino{
	
	private double areaProtegida;
	
	/**
	 * Constructor de la clase que hereda de destino
	 * @param nombre : String
	 * @param descripcion : String
	 * @param region : String
	 * @param clima : String
	 * @param actividadesRecomendadas : Lista
	 * @param areaProtegida : real
	 */
	
	public DestinoNatural(String nombre, String descripcion, String region, String clima, String tipo,
			List<String> actividadesRecomendadas, double areaProtegida) {
		super(nombre, descripcion, region, clima, tipo, actividadesRecomendadas);
		this.areaProtegida = areaProtegida;
	}
	
	/**
	 * Metodo get del atributo AreaProtegida
	 * @return : real
	 */

	public double getAreaProtegida() {
		return areaProtegida;
	}
	
	/**
	 * Metodo mostrar detalles para mostrar cuanta area tiene
	 */
	
	@Override
	public void mostrarDetalles() {
		super.mostrarDetalles();
		System.out.println("Area protegida: " +areaProtegida+ " km²");
	}
}
