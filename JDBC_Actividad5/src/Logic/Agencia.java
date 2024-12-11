package Logic;

import java.sql.SQLException;

import Model.Usuario;
import Persistencia.GestorJDBC;

public class Agencia {

    GestorJDBC gestor; 

    public Agencia() {
        this.gestor = new GestorJDBC(); // Initialize gestor
    }

    public String consulta2(int dni, String nombre) throws ClassNotFoundException, SQLException {
        Usuario u = gestor.buscarUsuario(dni + "");

        if (u == null) return "dni no encontrado";
        else {
            if(u.getNombre().compareTo(nombre) == 0) {
                return "Usuario encontrado";
            } else {
                return "Usuario no encontrado";
            }
        }


        
    }

}
