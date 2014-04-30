package com.recorridaszo.recorridaszo.test;


import android.test.ActivityInstrumentationTestCase2;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.recorridaszo.MapaActivity;

public class MapaActivityTest extends
		ActivityInstrumentationTestCase2<MapaActivity> {
	
//	private ManejadorBDWeb mw;
	private ManejadorBDLocal ml;
//	private Persona unaPersona;
	private MapaActivity activity;

	public MapaActivityTest() {
		super(MapaActivity.class);
	}

	@Override
	protected void setUp() {
//		mw = ManejadorBDWeb.getInstance();
		ml = ManejadorBDLocal.getInstance();
//		unaPersona = PersonaTest.crearPersona();
		activity = getActivity(); // get a references to the app under test
		ml.conectarse(activity);
	}

	//TODO

}
