package Logica;

public class Coche {
	private String Matricula;
	private String marca;
	private int precio;

	public Coche(String matricula, String marca, int precio) {
		Matricula = matricula;
		this.marca = marca;
		this.precio = precio;

	}
	public String getMatricula() {
		return Matricula;
	}
	public void setMatricula(String matricula) {
		Matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String toString() {
		return "Coche [Matricula=" + Matricula + ", marca=" + marca + ", precio=" + precio + "]";
	}
	
}
