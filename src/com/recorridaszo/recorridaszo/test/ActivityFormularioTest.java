package com.recorridaszo.recorridaszo.test;

import android.content.Intent;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.recorridaszo.FormularioActivity;
import com.recorridaszo.utilitarios.Utils;

public class ActivityFormularioTest extends
		ActivityInstrumentationTestCase2<FormularioActivity> {

	private FormularioActivity activity;
	private ManejadorBDLocal ml;
	LatLng ubicacion;
	EditText nombre;
	Button botonOK;

	public ActivityFormularioTest() {
		super(FormularioActivity.class);
	}

	protected void setUp() {
		ml = ManejadorBDLocal.getInstance();
		ubicacion = new LatLng(-3.0, -2.0);
		Intent intent = new Intent();
		intent.putExtra(Utils.KEY_LATITUD, ubicacion.latitude);
		intent.putExtra(Utils.KEY_LONGITUD, ubicacion.longitude);
		setActivityIntent(intent);
		activity = getActivity(); // get a references to the app under test
		nombre = (EditText) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.eTNombre);
		botonOK = (Button) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.buttonOk);
		ml.conectarse(activity);
	}

	public void testLlenarFormularioNuevo() {// TODO completar
		ml.borrarTodo();
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				nombre.setText("Juan");
				botonOK.performClick();
			}
		});		

		this.getInstrumentation().waitForIdleSync();
		
		Cursor c = ml.selectTodo();
		Persona persona = ml.obtenerPersona(ubicacion);

		assertEquals("Juan", nombre.getEditableText().toString());
		assertEquals(1, c.getCount());
		assertEquals("Juan", persona.getNombre());
	}	
	
	@Override
	protected void tearDown() {
		ManejadorBDWeb.setMock(false);
		ml.desconectarse();
	}
}
