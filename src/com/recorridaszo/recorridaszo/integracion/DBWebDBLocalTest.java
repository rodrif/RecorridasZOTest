package com.recorridaszo.recorridaszo.integracion;

import android.database.Cursor;
import android.test.AndroidTestCase;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.interfaces.IManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.personas.PersonaTest;


public class DBWebDBLocalTest extends AndroidTestCase {
	IManejadorBDWeb mw;
	ManejadorBDLocal ml;
	Persona unaPersona;
	
	
	public DBWebDBLocalTest() {
		ManejadorBDWeb.setMock(false);
	}

	@Override
	protected void setUp() {
		mw = ManejadorBDWeb.getInstance();
		ml = ManejadorBDLocal.getInstance();
		unaPersona = PersonaTest.crearPersona();
		ml.conectarse(getContext());
	}

	public void testInsercionDBWeb() {
		Personas personas = new Personas();
		mw.borrarDBWEB();
		ml.borrarTodo();
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Cursor c = ml.selectTodo();
		assertEquals(0, c.getCount());
		
		personas.addPersona(unaPersona);
		mw.insertar(personas, getContext(), null);
		mw.obtenerPersonasDBWeb(getContext(), null);
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		c = ml.selectTodo();
		assertEquals(1, c.getCount());
	}
	
	@Override
	protected void tearDown() {
		ml.desconectarse();
	}
	
}
