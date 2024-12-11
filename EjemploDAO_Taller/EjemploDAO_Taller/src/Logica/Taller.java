package Logica;

import java.util.ArrayList;
import java.util.List;

import Excepciones.LogicaError;
import Persistencia.GestorJDBC;

public class Taller {

	private List<Propietario> propietarios;
	GestorJDBC gestor;

	public Taller() throws LogicaError {
		propietarios = new ArrayList<Propietario>();
		gestor = new GestorJDBC();
		gestor.cargarDatos(this);

	}

	public Coche buscarCoche(String m) {
		for (Propietario p : propietarios) {
			for (Coche c : p.getCoches()) {
				if (c.getMatricula().compareTo(m) == 0)
					return c;
			}
		}
		return null;
	}

	public Propietario buscarPropietario(String dni) {
		for (Propietario p : propietarios) {
			if (p.getDni().equals(dni))
				return p;
		}
		return null;
	}

	public void addPropietario(Propietario p) {
		propietarios.add(p);
	}

	public void addCoche(Coche c, String dni) throws LogicaError {
		Propietario p = buscarPropietario(dni);
		if (p != null)
			p.addCoche(c, dni);

	}

	public String mostrarDatos() {
		String texto = "Propietarios " + propietarios.size() + "\n";
		for (Propietario p : propietarios) {
			texto += p.mostrarDatos();
		}

		return texto;
	}

	public void borrarCoche(String matricula) throws LogicaError {
		Coche c = buscarCoche(matricula);
		if (c == null)
			throw new LogicaError("ERROR coche no existe ");
		gestor.borrarCoche(c.getMatricula());
		for (Propietario p : propietarios) {
			for (Coche caux : p.getCoches()) {
				if (caux.getMatricula().compareTo(matricula) == 0) {
					p.getCoches().remove(caux);
					return;
				}
			}
		}
		throw new LogicaError("ERROR coche no existe ");

	}

	public void borrarPropietario(String dni) throws LogicaError {
		Propietario p = buscarPropietario(dni);
		if (p == null) {
			throw new LogicaError("ERROR propietario no existe ");
		}
		gestor.borrarPropietario(p.getDni());
		propietarios.remove(p);

	}

	public String buscarCocheDAO(String m) {
		return gestor.buscarCoche(m);

	}

	public String buscarPropietarioDAO(String d) {
		return gestor.buscarPropietario(d);

	}

	public String GetInfo() {
		// TODO Auto-generated method stub
		return gestor.getInfo();
	}

	
	 public void nuevoPropietario(Propietario p) throws LogicaError {
	 
		if (buscarPropietario(p.getDni()) != null) {
			throw new LogicaError("ERROR insertar");
		} else {
			this.addPropietario(p);
			String linea = p.getDni() + ";" + p.getNombre();
			gestor.insertarPropietario(linea);
		}
	}
	

}
