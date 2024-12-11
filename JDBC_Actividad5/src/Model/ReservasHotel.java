package Model;

import java.sql.Date;


public class ReservasHotel {
    private int idreserva;
    private byte numHotel;
    private int dniUsuario;
    private Date fechaReserva;
    private int numPersonas;
	public ReservasHotel(int idreserva, byte numHotel, int dniUsuario, Date fechaReserva, int numPersonas) {
		super();
		this.idreserva = idreserva;
		this.numHotel = numHotel;
		this.dniUsuario = dniUsuario;
		this.fechaReserva = fechaReserva;
		this.numPersonas = numPersonas;
	}
	public int getIdreserva() {
		return idreserva;
	}
	public byte getNumHotel() {
		return numHotel;
	}
	public int getDniUsuario() {
		return dniUsuario;
	}
	public Date getFechaReserva() {
		return fechaReserva;
	}
	public int getNumPersonas() {
		return numPersonas;
	}
	@Override
	public String toString() {
		return "ReservasHotel [idreserva=" + idreserva + ", numHotel=" + numHotel + ", dniUsuario=" + dniUsuario
				+ ", fechaReserva=" + fechaReserva + ", numPersonas=" + numPersonas + "]";
	}
    
}
