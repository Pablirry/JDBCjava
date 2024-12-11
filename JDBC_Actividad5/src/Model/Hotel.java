package Model;



// Con SQL de date pilla la hora de la pila (mejor), y con util del sistema operativo (tiene más métodos)
public class Hotel {

    private int idHotel;
    private String nombre;
    private String direccion;
    private int anoCreacion;
    private int plaza;
    private int precio;

    public String toString() {

        String cadena = "";
        cadena += String.format("ID hotel: %d\n", this.idHotel);
        cadena += String.format("Nombre: %s\n", this.nombre);
        cadena += String.format("Dirección: %s\n", this.direccion);
        cadena += String.format("Año creación: %d\n", this.anoCreacion);
        cadena += String.format("Plaza: %d\n", this.plaza);
        cadena += String.format("Precio: %d €\n", this.precio);
        return cadena;

    }

	public Hotel(int idHotel, String nombre, String direccion, int anoCreacion, int plaza, int precio) {
		super();
		this.idHotel = idHotel;
		this.nombre = nombre;
		this.direccion = direccion;
		this.anoCreacion = anoCreacion;
		this.plaza = plaza;
		this.precio = precio;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getAnoCreacion() {
		return anoCreacion;
	}

	public void setAnoCreacion(int anoCreacion) {
		this.anoCreacion = anoCreacion;
	}

	public int getPlaza() {
		return plaza;
	}

	public void setPlaza(int plaza) {
		this.plaza = plaza;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}
    
}
