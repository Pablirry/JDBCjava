package Logic;

import java.util.ArrayList;
import java.util.List;

import Error.ExcepcionPersistencia;
import Error.LogicaExcepcion;
import Persistence.GestionConsultas;

public class Taller {

    private List<Coche> coches;
    private List<Propietario> propietarios;

    private GestionConsultas gestor;

    public Taller() {
        this.coches = new ArrayList<Coche>();
        this.propietarios = new ArrayList<Propietario>();
        gestor = new GestionConsultas();
        gestor.leerCoches(this);
        gestor.leerPropietario(this);
    }

    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }

    public List<Propietario> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Propietario> propietarios) {
        this.propietarios = propietarios;
    }

    public Coche buscarCoche(String matricula) {
        for (Coche c : this.coches) {
            if (c.getMarca().compareTo(matricula) == 0)
                return c;
        }
        return null;
    }

    public Propietario buscarPropietario(String dni) {
        for (Propietario p : this.propietarios) {
            if (p.getDni().compareTo(dni) == 0)
                return p;
        }
        return null;
    }

    public void addPropietario(Propietario p) {
        this.propietarios.add(p);
    }

    public void addCoche(Coche c) {
        this.coches.add(c);
    }

    public String mostrarDatos() {
        String texto = "Propietarios: " + this.propietarios.size();
        for (Propietario p : this.propietarios) {
            texto += "\n\t" + p.toString();
        }
        texto += "\nCoches: " + this.coches.size();
        for (Coche c : this.coches) {
            texto += "\n\t" + c.toString();
            Propietario p = buscarPropietario(c.getDniPropietario());
            texto += "\n\t\t" + p.getNombre();
        }
        return texto;
    }

    public void insertarNuevoPropietario(String dni, String nombre, int edad)
            throws LogicaExcepcion, ExcepcionPersistencia {
        // Validar que se puede añadir
        if (buscarPropietario(dni) != null)
            throw new LogicaExcepcion("Error insertar propietario");
        Propietario p = new Propietario(dni, nombre, edad);
        this.gestor.insertarNuevoPropietario(p);
        this.propietarios.add(p);
    }

    public void insertarNuevoCoche(String matricula, String marca, int precio, String dniPropietario)
            throws LogicaExcepcion, ExcepcionPersistencia {
        if (buscarCoche(matricula) != null)
            throw new LogicaExcepcion("Error insertar coche");
        if (buscarPropietario(dniPropietario) == null)
            throw new LogicaExcepcion("Error insertar coche: propietario no encontrado");
        Coche c = new Coche(matricula, marca, precio, dniPropietario);
        this.gestor.insertarNuevoCoche(c);
        this.coches.add(c);

    }

    public void borrarCoche(String matricula) {
        // TODO Auto-generated method stub

    }

    public void borrarPropietario(String dni) {

        // Borrar todos los coches del propietario

        // Borrar el proipetario
    }

}
