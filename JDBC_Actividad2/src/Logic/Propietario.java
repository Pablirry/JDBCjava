package Logic;

public class Propietario {
	
	private String dni;
	private String nombre;
	private int edad;
	
	public Propietario(String dni, String nombre, int edad) {
		this.dni = dni;
		this.edad = edad;
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

}
