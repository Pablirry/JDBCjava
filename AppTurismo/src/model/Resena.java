package model;

public class Resena {
	
	private int id;
    private int restauranteId;
    private String usuario;
    private String comentario;
    private int puntuacion;

    public Resena(int id, int restauranteId, String usuario, String comentario, int puntuacion) {
        this.id = id;
        this.restauranteId = restauranteId;
        this.usuario = usuario;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public int getRestauranteId() {
        return restauranteId;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

}
