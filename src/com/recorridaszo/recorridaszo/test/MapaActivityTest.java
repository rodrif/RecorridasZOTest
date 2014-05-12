package com.recorridaszo.recorridaszo.test;

import android.test.ActivityInstrumentationTestCase2;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.interfaces.IManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.MapaActivity;
import com.recorridaszo.recorridaszo.personas.PersonaTest;


public class MapaActivityTest extends
		ActivityInstrumentationTestCase2<MapaActivity> {

	private IManejadorBDWeb mw;
	private ManejadorBDLocal ml;
	private Persona unaPersona;
	private MapaActivity activity;

	public MapaActivityTest() {
		super(MapaActivity.class);
	}

	@Override
	protected void setUp() {
		ManejadorBDWeb.setMock(true);
		mw = ManejadorBDWeb.getInstance();
		ml = ManejadorBDLocal.getInstance();
		unaPersona = PersonaTest.crearPersona();
		activity = getActivity(); // get a references to the app under test
		ml.conectarse(activity);
	}

	public void testInsercionDBWeb() {
		mw.borrarDBWEB();
		ml.borrarTodo();

		Personas p = ml.selectTodoPersonas();
		assertEquals(0, p.size());

		mw.insertar(unaPersona, activity, null);
		mw.obtenerPersonasDBWeb(activity, null);

		p = ml.selectTodoPersonas();

		assertEquals(1, p.size());
	}
//TODO
/*	public void testDragMarcador() {
		ml.borrarTodo();
		Persona persona = PersonaTest.crearPersonaLatLngVariable();
		ml.guardarPersona(persona);
		activity.cargarMarcadores();
		
//		activity.getMap()
	}
*/
	@Override
	protected void tearDown() {
		ManejadorBDWeb.setMock(false);
	}
}
