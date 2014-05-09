package com.recorridaszo.recorridaszo.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.FormularioActivity;
import com.recorridaszo.utilitarios.Utils;

public class ActivityFormularioTest extends
		ActivityInstrumentationTestCase2<FormularioActivity> {

	private FormularioActivity activity;
	private ManejadorBDLocal ml;
	LatLng ubicacion;
	EditText nombre;
	EditText apellido;
	EditText descripcion;
	EditText direccion;
	EditText zona;
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
		apellido = (EditText) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.eTApellido);
		descripcion = (EditText) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.eTDescripcion);
		direccion = (EditText) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.eTDireccion);
		zona = (EditText) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.eTZona);
		botonOK = (Button) activity
				.findViewById(com.recorridaszo.recorridaszo.R.id.buttonOk);
		ml.conectarse(activity);
	}

	public void testLlenarFormularioNuevo() {
		ml.borrarTodo();
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				nombre.setText("Juan");
				apellido.setText("ApellidoPrueba");
				descripcion.setText("DescripcionPrueba");
				direccion.setText("DireccionPrueba");
				zona.setText("ZonaPrueba");
				botonOK.performClick();
			}
		});		

		this.getInstrumentation().waitForIdleSync();
		
		Personas p = ml.selectTodoPersonas();
		Persona persona = ml.obtenerPersona(ubicacion);

		assertEquals("Juan", nombre.getEditableText().toString());
		assertEquals("ApellidoPrueba", apellido.getEditableText().toString());
		assertEquals("DescripcionPrueba", descripcion.getEditableText().toString());
		assertEquals("DireccionPrueba", direccion.getEditableText().toString());
		assertEquals("ZonaPrueba", zona.getEditableText().toString());
		assertEquals(1, p.size());
		assertEquals("Juan", persona.getNombre());
		assertEquals("ApellidoPrueba", persona.getApellido());
		assertEquals("DescripcionPrueba",  persona.getDescripcion());
		assertEquals("DireccionPrueba",  persona.getDireccion());
		assertEquals("ZonaPrueba",  persona.getZona());
	}	
}
