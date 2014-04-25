package com.recorridaszo.recorridaszo.test;

import android.test.AndroidTestCase;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;

public class BDWebTest extends AndroidTestCase{
	ManejadorBDWeb manejador = ManejadorBDWeb.getInstance();
	Persona unaPersona = PersonaTest.crearPersona();
	Personas personas = new Personas();
	
	
	@Override
	protected void setUp() {
	}
	
	public void testInsertar() {		
		String resultado = manejador.insertar(unaPersona, getContext());
		assertEquals("No inserto en DBWeb","bien", resultado);
	}
	
	public void testBuscar(){
	
	}
	
	public void borrar(){
		manejador.borrar(personas);
	}

}
