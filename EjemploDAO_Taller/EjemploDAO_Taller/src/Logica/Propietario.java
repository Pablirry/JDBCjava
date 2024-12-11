package Logica;

import java.util.ArrayList;
import java.util.List;

import Excepciones.LogicaError;

public class Propietario {
	private String dni;
	private String nombre;
	private int edad;
	private List<Coche> coches;
	public Propietario(String dni, String nombre, int edad) {
		this.dni = dni;
		this.nombre = nombre;
		this.edad = edad;
		coches=new ArrayList<Coche>();
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
	@Override
	public String toString() {
		return "Propietario [dni=" + dni + ", nombre=" + nombre + ", edad=" + edad + "]";
	}
	
	public String mostrarDatos() {
		String texto="Propietario [dni=" + dni + ", nombre=" + nombre + ", edad=" + edad + "]\n";
		for(Coche c:coches) {
				texto+="\t"+c.toString()+"\n";			
		}
		return texto;
	}
	
	public void addCoche(Coche c, String dni) throws LogicaError {
		if(this.dni.compareTo(dni)==0) {
			coches.add(c);
		}else {
			throw new LogicaError("ERROR dni no coherente ");
		}
	}
	public List<Coche> getCoches() {
		return coches;
	}
	public void setCoches(List<Coche> coches) {
		this.coches = coches;
	}
	
	
}
