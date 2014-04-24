package com.recorridaszo.recorridaszo.test;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import android.database.Cursor;
import android.test.AndroidTestCase;

public class BDLocalTest extends AndroidTestCase {
	ManejadorBDLocal ml;

	@Override
	protected void setUp() {
		ml.conectarse(this.getContext());
	}

	public BDLocalTest() {
		ml = ManejadorBDLocal.getInstance();
	}

	public void testInsercion() {
		int id = 12;
		String nombre = "PersonaPrueba";

		// borro la bd local
		ml.borrarTodo();

		// Insertamos los datos en la tabla Usuarios
		ml.getDB().execSQL(
				"INSERT INTO Personas (id, nombre) " + "VALUES (" + id + ", '"
						+ nombre + "')");

		Cursor c = ml.selectTodo();
		c.moveToFirst();
		String valorNombre = c.getString(c.getColumnIndex("nombre"));
		assertEquals("PersonaPrueba", valorNombre);
	}

	public void testBorrarTodo() {
		int id = 1;
		String nombre = "PersonaBorrar";

		// borro la bd local
		ml.borrarTodo();

		ml.getDB().execSQL(
				"INSERT INTO Personas (id, nombre) " + "VALUES (" + id + ", '"
						+ nombre + "')");

		Cursor c = ml.selectTodo();

		assertEquals(1, c.getCount());
		// borro la bd local
		ml.borrarTodo();

		c = ml.selectTodo();
		assertEquals(0, c.getCount());
	}

	public void testGuardarPersona() { // TODO: completarlo con fecha
		ml.borrarTodo();
		Persona persona = new Persona(10, "persona", "guardar", "murguiondo",
				"Ramos", "descripcion", new LatLng(5.55, 5.01), "fecha");
		ml.guardarPersona(persona);
		Cursor c = ml.selectTodo();
		c.moveToFirst();

		assertEquals(10, c.getInt(c.getColumnIndex("id")));
		assertEquals("persona", c.getString(c.getColumnIndex("nombre")));
		assertEquals("guardar", c.getString(c.getColumnIndex("apellido")));
		assertEquals("murguiondo", c.getString(c.getColumnIndex("direccion")));
		assertEquals("Ramos", c.getString(c.getColumnIndex("zona")));
		assertEquals("descripcion", c.getString(c.getColumnIndex("descripcion")));
		assertEquals(5.55, c.getDouble(c.getColumnIndex("latitud")), 0.0000001);
		assertEquals(5.01, c.getDouble(c.getColumnIndex("longitud")), 0.0000001);
	}

	public void testObtenerPersona() {
		LatLng ubicacionBuscar = new LatLng(-10.0, -10.0);

		ml.borrarTodo();
		Persona otraPersona = new Persona("Alberto", "apellido", new LatLng(
				-10.0, 12.0));
		LatLng ubicacion = new LatLng(-10.0, -10.0);
		Persona nuevaPersona = new Persona("Juan", "Perez", ubicacion);
		ml.guardarPersona(otraPersona);
		ml.guardarPersona(nuevaPersona);

		Persona personaObtenida = ml.obtenerPersona(ubicacion);

		assertEquals(nuevaPersona.getNombre(), personaObtenida.getNombre());
		assertEquals(ubicacionBuscar.latitude, personaObtenida.getLatitud(),
				0.000001);
		assertEquals(ubicacionBuscar.longitude, personaObtenida.getLatitud(),
				0.000001);
	}

	public void testRegistrosIguales() {
		ml.borrarTodo();
		LatLng ubicacion = new LatLng(-10.0, -10.0);
		Persona nuevaPersona = new Persona("Juan", "Perez", ubicacion);
		ml.guardarPersona(nuevaPersona);
		ml.guardarPersona(nuevaPersona);

		assertEquals(2, ml.selectTodo().getCount());
	}

	@Override
	protected void tearDown() {
		// Cerramos la base de datos
		ml.desconectarse();
	}
}
