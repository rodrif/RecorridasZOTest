package com.recorridaszo.recorridaszo.integracion;

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
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Personas p = ml.selectTodoPersonas();
		assertEquals(0, p.size());
		
		mw.insertar(unaPersona, getContext(), null);
		mw.obtenerPersonasDBWeb(getContext(), null);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		p = ml.selectTodoPersonas();
		assertEquals(1, p.size());
	}	
}
