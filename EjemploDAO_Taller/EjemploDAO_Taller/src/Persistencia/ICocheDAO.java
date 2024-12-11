package Persistencia;

public interface ICocheDAO {
	/**
	 * inserta un nuevo coche con ls infor separada por ;
	 * @param infoCoche
	 * @return
	 */
	public boolean insertarCoche(String infoCoche);
	/**
	 * modifica los campos de un coche  con la infor separada por ;
	 * @param infoCoche
	 * @return
	 */
	public boolean actualizarCoche(String matricula,String infoCoche);
	/**
	 * Retorna un string separando cada campo por ; y cada registro \n
	 * @return
	 */
	public String listarTodosLosCoches();
	/**
	 * Retorna un string separando cada campo por ; y cada registro \n
	 * @return
	 */
	public String listarCocheDni(String dni);
	
	public boolean borrarCoche(String matricula);
	
	public String buscarCoche(String matricula);
}
