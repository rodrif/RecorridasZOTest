package com.recorridaszo.recorridaszo.test;

import android.test.AndroidTestCase;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.persona.Persona;

public class BDWebTest extends AndroidTestCase{
	ManejadorBDWeb manejador = ManejadorBDWeb.getInstance();	
	@Override
	protected void setUp() {
	}
	
	public void testInsertar() {
		Persona unaPersona = PersonaTest.crearPersona();
		String resultado = manejador.insertar(unaPersona, null);
		assertEquals("No inserto en DBWeb","1", resultado);
	}	
}
