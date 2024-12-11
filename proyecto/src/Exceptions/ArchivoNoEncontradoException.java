package Exceptions;

public class ArchivoNoEncontradoException extends Exception {
	
	/**
	 * Contructor para manejar excepcion de archivo
	 * @param mensaje : String
	 */
	
	public ArchivoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

}
