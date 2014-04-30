package com.recorridaszo.recorridaszo.test;

import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.recorridaszo.MapaActivity;

public class MapaActivityTest extends
		ActivityInstrumentationTestCase2<MapaActivity> {

	private ManejadorBDWeb mw;
	private ManejadorBDLocal ml;
	private Persona unaPersona;
	private MapaActivity activity;

	public MapaActivityTest() {
		super(MapaActivity.class);
	}

	@Override
	protected void setUp() {
		mw = ManejadorBDWeb.getInstance();
		ml = ManejadorBDLocal.getInstance();
		unaPersona = PersonaTest.crearPersona();
		activity = getActivity(); // get a references to the app under test
		ml.conectarse(activity);
	}

	public void testInsercionDBWeb() {
		mw.borrarDBWEB();
		ml.borrarTodo();

		this.getInstrumentation().waitForIdleSync();

		Cursor c = ml.selectTodo();
		assertEquals(0, c.getCount());

		mw.insertar(unaPersona, activity, null);		
		this.getInstrumentation().waitForIdleSync();
		mw.obtenerPersonasDBWeb(activity, null);		
		this.getInstrumentation().waitForIdleSync();
		c = ml.selectTodo();		
		this.getInstrumentation().waitForIdleSync();
		
		assertEquals(1, c.getCount());
	}

}
