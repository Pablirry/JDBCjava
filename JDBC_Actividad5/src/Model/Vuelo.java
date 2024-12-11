package Model;

import java.sql.Date;
import java.sql.Time;


public class Vuelo {
    int numero;
    String aeroInic;
    String aeroFin;
    Date diaLleg;
    Date diaSal;
    Time horaSal;
    Time horaLleg;
    String avion;
    int plazaDisp;

    public Vuelo(int numero, String aeroInic, String aeroFin, Date diaLleg, Date diaSal, Time horaSal, Time horaLleg, String avion, int plazaDisp) {
        this.numero = numero;
        this.aeroInic = aeroInic;
        this.aeroFin = aeroFin;
        this.diaLleg = diaLleg;
        this.diaSal = diaSal;
        this.horaSal = horaSal;
        this.horaLleg = horaLleg;
        this.avion = avion;
        this.plazaDisp = plazaDisp;
    }
    
    public String toString(){
        String cadena = "";
        cadena+=String.format("Número de vuelo: %d\n", this.numero);
        cadena+=String.format("Aerolínea inicial: %s\n", this.aeroInic);
        cadena+=String.format("Aerolínea fin: %s\n", this.aeroFin);
        cadena+=String.format("dia de llegada: %s\n", this.diaLleg);
        cadena+=String.format("Día de salida: %s\n", this.diaSal);
        cadena+=String.format("Hora de salida: %s\n", this.horaSal);
        cadena+=String.format("Hora de llegada: %s\n", this.horaLleg);
        cadena+=String.format("Avión: %s\n", this.avion);
        cadena+=String.format("Plazas disponibles: %d\n", this.plazaDisp);
        return cadena;
    }

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getAeroInic() {
		return aeroInic;
	}

	public void setAeroInic(String aeroInic) {
		this.aeroInic = aeroInic;
	}

	public String getAeroFin() {
		return aeroFin;
	}

	public void setAeroFin(String aeroFin) {
		this.aeroFin = aeroFin;
	}

	public Date getDiaLleg() {
		return diaLleg;
	}

	public void setDiaLleg(Date diaLleg) {
		this.diaLleg = diaLleg;
	}

	public Date getDiaSal() {
		return diaSal;
	}

	public void setDiaSal(Date diaSal) {
		this.diaSal = diaSal;
	}

	public Time getHoraSal() {
		return horaSal;
	}

	public void setHoraSal(Time horaSal) {
		this.horaSal = horaSal;
	}

	public Time getHoraLleg() {
		return horaLleg;
	}

	public void setHoraLleg(Time horaLleg) {
		this.horaLleg = horaLleg;
	}

	public String getAvion() {
		return avion;
	}

	public void setAvion(String avion) {
		this.avion = avion;
	}

	public int getPlazaDisp() {
		return plazaDisp;
	}

	public void setPlazaDisp(int plazaDisp) {
		this.plazaDisp = plazaDisp;
	} 
    
    
}
