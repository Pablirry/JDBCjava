package Logic;

public class Coche {
	
	private String matricula;
	private String marca;
	private int precio;
	private String dniP;
	
	public Coche(String matricula, String marca, int precio, String dniP) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.precio = precio;
		this.dniP = dniP;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
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

	public String getDniP() {
		return dniP;
	}

	public void setDniP(String dniP) {
		this.dniP = dniP;
	}

}
