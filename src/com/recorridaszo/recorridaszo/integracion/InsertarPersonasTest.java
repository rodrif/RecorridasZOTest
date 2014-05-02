package com.recorridaszo.recorridaszo.integracion;

import java.util.Iterator;
import android.test.ActivityInstrumentationTestCase2;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.MapaActivity;
import com.recorridaszo.recorridaszo.personas.PersonaTest;
import com.recorridaszo.utilitarios.Utils;

public class InsertarPersonasTest extends ActivityInstrumentationTestCase2<MapaActivity>{
	private MapaActivity activity;
	private Personas personas;
	private ManejadorBDLocal ml = ManejadorBDLocal.getInstance();	
	private Persona persona1;
	private Persona persona2;
	
	public InsertarPersonasTest() {
		super(MapaActivity.class);
	}
	
	protected void setUp() {
		ManejadorBDWeb.setMock(true);
		activity = getActivity(); // get a references to the app under test
		persona1 = PersonaTest.crearPersonaLatLngVariable();
		persona1.setId(10);
		persona1.setEstado(Utils.EST_NUEVO);
		persona2 = PersonaTest.crearPersonaLatLngVariable();
		persona2.setId(11);
		persona2.setEstado(Utils.EST_NUEVO);
		personas = new Personas();
		personas.addPersona(persona1);
		personas.addPersona(persona2);
		ml.conectarse(activity);
		ml.borrarTodo();
		ml.guardarPersonas(personas);
		
		this.getInstrumentation().waitForIdleSync();
	}
	
	public void testInsertarPersonas(){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.onBotonSubirClick();
			}
		});		

		this.getInstrumentation().waitForIdleSync();		
		
		Personas resultado = ml.obtenerPersonas();
		Iterator<Persona> it = resultado.iterator();
		
		assertEquals(2,resultado.size());
		assertEquals(Utils.EST_ACTUALIZADO,it.next().getEstado());
		assertEquals(Utils.EST_ACTUALIZADO,it.next().getEstado());
	}

	@Override
	protected void tearDown() {
		ManejadorBDWeb.setMock(false);	
	}
}
