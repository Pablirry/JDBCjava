package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import Exceptions.ArchivoNoEncontradoException;
import Model.*;
import Persistence.CsvReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JUnit_Persistencia {
	
	private List<Destino> destinos;
    private List<Actividad> actividades;

    @BeforeEach
    void setUp() {
        destinos = new ArrayList<>();
        actividades = new ArrayList<>();
    }

    @Test
    void testCargarDestinosCsv(@TempDir Path tempDir) throws Exception {

    	File archivoCsv = tempDir.resolve("data/destinos.csv").toFile();
        archivoCsv.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(archivoCsv)) {
            writer.write("urbano;Madrid;Capital de España;Centro;Templado\n");
            writer.write("natural;Amazonas;Selva tropical;América del Sur;Húmedo\n");
        }

        CsvReader.cargarDestinosCsv("destinos.csv", destinos);
        assertTrue(destinos.get(0) instanceof DestinoUrbano);
        assertTrue(destinos.get(1) instanceof DestinoNatural);
    }

    @Test
    void testCargarActividadesCsv(@TempDir Path tempDir) throws Exception {
    	
    	File archivoCsv = tempDir.resolve("data/actividades.csv").toFile();
        archivoCsv.getParentFile().mkdirs(); 

        try (FileWriter writer = new FileWriter(archivoCsv)) {
            writer.write("Tipo;Nombre;Precio;Duración;Nivel de Dificultad\n");
            writer.write("aventura;Escalada;120.50;4;Difícil\n");
            writer.write("cultural;Museo;15.00;2;Fácil\n");
        }

        CsvReader.cargarActividadesCsv("actividades.csv", actividades);
        assertTrue(actividades.get(0) instanceof ActividadAventura);
        assertTrue(actividades.get(1) instanceof ActividadCultural);
    }

    @Test
    void testGuardarActividadesCsv(@TempDir Path tempDir) throws Exception {

        actividades.add(new ActividadAventura("Escalada", 120.50, 4, "Difícil", true));
        actividades.add(new ActividadCultural("Museo", 15.00, 2, "Fácil", "Español"));


        File archivoCsv = tempDir.resolve("guardar_actividades.csv").toFile();


        CsvReader.guardarActividadesCsv(archivoCsv.getPath(), actividades);


        List<String> lines = java.nio.file.Files.readAllLines(archivoCsv.toPath());
        assertEquals(3, lines.size());
        assertEquals("Tipo;Nombre;Precio;Duración;Nivel de Dificultad", lines.get(0));
        assertTrue(lines.get(1).contains("Escalada"));
        assertTrue(lines.get(2).contains("Museo"));
    }

    @Test
    void testGuardarDestinosCsv(@TempDir Path tempDir) throws Exception {

        destinos.add(new DestinoUrbano("Madrid", "Capital de España", "Centro", "Templado", List.of("Museos", "Visita al centro"), 5));
        destinos.add(new DestinoNatural("Amazonas", "Selva tropical", "América del Sur", "Húmedo", List.of("Senderismo", "Montañismo"), 250.0));


        File archivoCsv = tempDir.resolve("guardar_destinos.csv").toFile();


        CsvReader.guardarDestinosCsv(archivoCsv.getPath(), destinos);


        List<String> lines = java.nio.file.Files.readAllLines(archivoCsv.toPath());
        assertEquals(3, lines.size());
        assertEquals("Tipo;Nombre;Descripción;Región;Clima", lines.get(0));
        assertTrue(lines.get(1).contains("Madrid"));
        assertTrue(lines.get(2).contains("Amazonas"));
    }

    @Test
    void testCargarDestinosCsvFileNotFound() {

        assertThrows(ArchivoNoEncontradoException.class, () -> {
            CsvReader.cargarDestinosCsv("archivo_no_existente.csv", destinos);
        });
    }

    @Test
    void testCargarActividadesCsvFileNotFound() {

        assertThrows(ArchivoNoEncontradoException.class, () -> {
            CsvReader.cargarActividadesCsv("archivo_no_existente.csv", actividades);
        });
    }

}
