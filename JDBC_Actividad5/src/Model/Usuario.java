package Model;


public class Usuario {
    int dni;
    String nombre;
    String apel1;
    String apel2;
    String dir;
    String nacionalidad;
    
    public String toString(){
        String cadena = "";
        cadena+=String.format("DNI: %d\n", this.dni);
        cadena+=String.format("Nombre: %s\n", this.nombre);
        cadena+=String.format("Primer apellido: %s\n", this.apel1);
        cadena+=String.format("Segundo apellido: %s\n", this.apel2);
        cadena+=String.format("Dirección: %s\n", this.dir);
        cadena+=String.format("Nacionalidad: %s\n", this.nacionalidad);
        return cadena;
    }

	public Usuario(int dni, String nombre, String apel1, String apel2, String dir, String nacionalidad) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apel1 = apel1;
		this.apel2 = apel2;
		this.dir = dir;
		this.nacionalidad = nacionalidad;
	}

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApel1() {
        return apel1;
    }

    public String getApel2() {
        return apel2;
    }

    public String getDir() {
        return dir;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
    
}
