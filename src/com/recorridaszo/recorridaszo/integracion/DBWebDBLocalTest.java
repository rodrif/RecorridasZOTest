package com.recorridaszo.recorridaszo.integracion;

import android.test.AndroidTestCase;

// TODO: ya realizada en mapa activity por temas de concurrencia
public class DBWebDBLocalTest extends AndroidTestCase {
/*	ManejadorBDWeb mw;
	ManejadorBDLocal ml;
	Persona unaPersona;
	
	
	public DBWebDBLocalTest() {
		mw = ManejadorBDWeb.getInstance();
		ml = ManejadorBDLocal.getInstance();
		unaPersona = PersonaTest.crearPersona();	
	}

	@Override
	protected void setUp() {
		mw = ManejadorBDWeb.getInstance();
		ml = ManejadorBDLocal.getInstance();
		unaPersona = PersonaTest.crearPersona();
		ml.conectarse(getContext());
	}

	public void testInsercionDBWeb() {
		mw.borrarDBWEB();
		ml.borrarTodo();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Cursor c = ml.selectTodo();
		assertEquals(0, c.getCount());
		
		mw.insertar(unaPersona, getContext(), null);
		mw.obtenerPersonasDBWeb(getContext(), null);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		c = ml.selectTodo();
		assertEquals(1, c.getCount());
	}		
	*/
}
