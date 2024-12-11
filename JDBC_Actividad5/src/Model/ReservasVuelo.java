package Model;

import java.util.Date;

public class ReservasVuelo {
	private int idreserva;
	private byte numvuelo;
	private int dniUsuario;
	private Date fechaReserva;
	private int precio;

	public ReservasVuelo(int idreserva, byte numvuelo, int dniUsuario, Date fechaReserva, int precio) {
		super();
		this.idreserva = idreserva;
		this.numvuelo = numvuelo;
		this.dniUsuario = dniUsuario;
		this.fechaReserva = fechaReserva;
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "ReservasVuelo [idreserva=" + idreserva + ", numvuelo=" + numvuelo + ", dniUsuario=" + dniUsuario
				+ ", fechaReserva=" + fechaReserva + ", precio=" + precio + "]";
	}

	public int getIdreserva() {
		return idreserva;
	}

	public byte getNumvuelo() {
		return numvuelo;
	}

	public int getDniUsuario() {
		return dniUsuario;
	}

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public int getPrecio() {
		return precio;
	}

}
