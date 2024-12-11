package Interface;

import java.sql.SQLException;

import Logic.Agencia;

public class Principal {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Agencia agencia = new Agencia();
		System.out.println("Consulta 1 : listado de datos cliente 2121212");
		// System.out.println(agencia.consulta1(2121212));


		System.out.println("Consulta 2 : 2121212");
		System.out.println(agencia.consulta2(2121212 , "Pablo"));
		System.out.println("Consulta 1 : listado de datos cliente 2121212");
		System.out.println(agencia.consulta2(2121212 , "Miguel"));
		System.out.println("Consulta 1 : listado de datos cliente 2121212");
		System.out.println(agencia.consulta2(2122212 , "Pablo"));



	}

}
