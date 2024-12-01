package Logic;

public class Coche {

	private String matricula;
	private String marca;
	private int precio;
	private String dniPropietario;

	public Coche(String matricula, String marca, int precio, String dniPropietario) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.precio = precio;
		this.dniPropietario = dniPropietario;
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

	public String getDniPropietario() {
		return dniPropietario;
	}

	public void setDniPropietario(String dniPropietario) {
		this.dniPropietario = dniPropietario;
	}

	@Override
	public String toString() {
		return "Coche [matricula=" + matricula + ", marca=" + marca + ", precio=" + precio + ", dniPropietario="
				+ dniPropietario + "]";
	}

}
