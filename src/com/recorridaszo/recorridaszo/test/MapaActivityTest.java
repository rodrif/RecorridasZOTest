package com.recorridaszo.recorridaszo.test;
// TODO: rehacer cuando este el mock de manejador bd web
import android.database.Cursor;
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
		Personas personas = new Personas();
		mw.borrarDBWEB();
		ml.borrarTodo();

		Cursor c = ml.selectTodo();
		assertEquals(0, c.getCount());

		personas.addPersona(unaPersona);
		mw.insertar(personas, activity, null);		
		mw.obtenerPersonasDBWeb(activity, null);		

		c = ml.selectTodo();		
		
		assertEquals(1, c.getCount());
	}

	@Override
	protected void tearDown() {
		ManejadorBDWeb.setMock(false);
		ml.desconectarse();
	}
}
