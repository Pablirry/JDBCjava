package Exceptions;

public class OpcionInvalidaException extends Exception {
	
	/**
	 * Constructor para maneja excepcion de opcion no válida
	 * @param mensaje : String
	 */
	
	public OpcionInvalidaException(String mensaje) {
        super(mensaje);
    }

}
