package model;

public class Restaurante {
	
	private int id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;
    private String ubicacion;

    public Restaurante(int id, String nombre, String descripcion, String rutaImagen, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.ubicacion = ubicacion;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getUbicacion() {
        return ubicacion;
    }

}
