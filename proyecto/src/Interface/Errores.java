package Interface;

import Exceptions.ArchivoNoEncontradoException;
import Exceptions.OpcionInvalidaException;
import Log.Logger;
import Persistence.CsvReader;

public class Errores {

	public static void main(String[] args) throws ArchivoNoEncontradoException {
		try {
            String numeroInvalido = "abc";
            int numero = Integer.parseInt(numeroInvalido);
        } catch (NumberFormatException e) {
            Errores.manejarError("Formato no valido", e);
        }

        try {
            throw new OpcionInvalidaException("Opción no válida.");
        } catch (OpcionInvalidaException e) {
            Errores.manejarError("Opción no válida", e);
        }
        try {
        CsvReader.cargarDestinosCsv("archivo_inexistente.csv", null);
        } catch (ArchivoNoEncontradoException e) {
        	Errores.manejarError("Error en el archivo", e);
        }

	}
	
	/**
	 * Metodo para manejar los errores y ver el tipo de error
	 * @param tipoError : String
	 * @param e : Exception
	 */
	
	public static void manejarError(String tipoError, Exception e) {
        System.out.println(tipoError + ": " + e.getMessage());
        Logger.logError(tipoError + ": " + e.getMessage());
    }

}
