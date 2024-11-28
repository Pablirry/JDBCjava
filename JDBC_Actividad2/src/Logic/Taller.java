package Logic;

import java.util.ArrayList;
import java.util.List;

import Persistence.GestionConsultas;

public class Taller {

    private List<Coche> coches;
    private List<Propietario> propietarios;
    GestionConsultas gestor;

    public Taller() {
        coches = new ArrayList<Coche>();
        propietarios = new ArrayList<Propietario>();
    }

    public Coche buscarCoche(String m) {
        for(Coche c : coches) {
            if(c.getMatricula().compareTo(m) == 0) return c;
            }
            return null;
    }

    public Propietario buscarPropietario(String d) {
        for(Propietario p : propietarios) {
            if(p.getDni().compareTo(d)  == 0) return p;
        }
        return null;
    }

    public void addPropietario(Propietario p) {
        propietarios.add(p);
    }
    public void addCoche(Coche c) {
        coches.add(c);
    }
    public String mostrarDatos () {
        String texto = "Propietarios " + propietarios.size() + "\n";
        for(Propietario p : propietarios) {
            texto += p.toString() + "\n";
        }
        texto = "Coches " + coches.size() + "\n";
        for(Coche c : coches) {
            texto += c.toString() + "\n";
            Propietario p = buscarPropietario(c.getDniP());
            texto += p.getNombre() + "\n";
        }
        return texto;
    }

    public void insertarNuevoPropietario(String dni, String nombre, int edad) {
        // ! VALIDAR...
        Propietario p = null;
        gestor.insertarNuevoPropietario();
    }

    public void borrarPropietario(String dni) {
        /*
        ^validar

        ! borrar dni de propietario
        * 1.-lista
        * 2.- de la base de datos

        ! borrar todos los coches del propietario
        * 1.- lista
        * 2.- de la base de datos
        */

        
    }
}
