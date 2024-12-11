package Persistencia;

public interface IPropietarioDAO {
	/**
	 * inserta un nuevo Propietario con ls infor separada por ;
	 * @param infoPropietario
	 * @return
	 */
	public boolean insertarPropietario(String infoPropietario);
	/**
	 * modifica los campos de un propietario con la infor separada por ;
	 * @param infoPropietario
	 * @return
	 */
	public boolean actualizarPropietario(String dni, String infoPropietario);
	/**
	 * Retorna un string separando cada campo por ; y cada registro \n
	 * @return
	 */
	public String listarTodosLosPropietarios();

	
	public boolean borrarPropietario(String dni);
	
	public String buscarPropietario(String dni);
}
