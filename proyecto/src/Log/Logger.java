package Log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	private static final String LOG_FILE = "data/errores.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Método para escribir y guardar el archivo de errores .log
     * @param mensaje : String
     */
    
    public static void logError(String mensaje) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            pw.println(timestamp + " - ERROR: " + mensaje);
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo de log: " + e.getMessage());
        }
    }
}