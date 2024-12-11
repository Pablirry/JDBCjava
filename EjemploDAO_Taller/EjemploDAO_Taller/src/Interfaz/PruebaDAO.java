package Interfaz;

import Excepciones.LogicaError;
import Logica.Coche;
import Logica.Propietario;
import Logica.Taller;

public class PruebaDAO {

	public PruebaDAO() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws LogicaError {
		// TODO Auto-generated method stub
		
		
		Taller taller=new Taller();
		//System.out.println(taller.mostrarDatos());
		
		// insertar propeitario
		/*
			taller.addPropietario(new Propietario("Z1C","ana_1",30));
			taller.addPropietario(new Propietario("Z2C","ana_2",30));
			
		

			taller.addCoche(new Coche("XX-1111","marca1",10000),"Z2C");
			taller.addCoche(new Coche("XX-2222","marca2",10000),"Z2C");
			taller.addCoche(new Coche("XX-3333","marca3",10000),"Z1C");
		
			System.out.println("**************************************");
			
			System.out.println(taller.mostrarDatos());
			
			System.out.println("Buscar coche ");
			System.out.println(taller.buscarCocheDAO("BA-3333"));
			System.out.println("Buscar Propeitario");
			System.out.println(taller.buscarPropietarioDAO("Z1C"));
			
		*/
			taller.borrarCoche("XX-3333");
			taller.borrarPropietario("Z2C");
			System.out.println(taller.mostrarDatos());
		
			
		String resul=taller.GetInfo();
		System.out.println("Info BD");
		System.out.println(resul);
			
			
	}

}
