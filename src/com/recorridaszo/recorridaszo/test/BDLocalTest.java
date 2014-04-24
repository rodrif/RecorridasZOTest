package com.recorridaszo.recorridaszo.test;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.Utils;

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
		Persona persona = PersonaTest.crearPersona();
		ml.guardarPersona(persona);
		Cursor c = ml.selectTodo();
		c.moveToFirst();

		assertEquals(persona.getId(), c.getInt(c.getColumnIndex("id")));
		assertEquals(persona.getNombre(),
				c.getString(c.getColumnIndex("nombre")));
		assertEquals(persona.getApellido(),
				c.getString(c.getColumnIndex("apellido")));
		assertEquals(persona.getDireccion(),
				c.getString(c.getColumnIndex("direccion")));
		assertEquals(persona.getZona(), c.getString(c.getColumnIndex("zona")));
		assertEquals(persona.getDescripcion(),
				c.getString(c.getColumnIndex("descripcion")));
		assertEquals(persona.getLatitud(), c.getDouble(c.getColumnIndex("latitud")), 0.0000001);
		assertEquals(persona.getLongitud(), c.getDouble(c.getColumnIndex("longitud")), 0.0000001);
		assertEquals(persona.getEstado(), c.getString(c.getColumnIndex("estado")));
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
		Persona nuevaPersona = PersonaTest.crearPersona();
		ml.guardarPersona(nuevaPersona);
		ml.guardarPersona(nuevaPersona);

		assertEquals(2, ml.selectTodo().getCount());
	}
	
	public void testObtenerPersonasPorEstado() {
		ml.borrarTodo();
		Persona persona1 = PersonaTest.crearPersonaLatLngVariable();
		persona1.setEstado(Utils.EST_NUEVO);
		ml.guardarPersona(persona1);
		Persona persona2 = PersonaTest.crearPersonaLatLngVariable();
		persona2.setEstado(Utils.EST_ACTUALIZADO);
		ml.guardarPersona(persona2);
		Persona persona3 = PersonaTest.crearPersonaLatLngVariable();
		persona3.setEstado(Utils.EST_BORRADO);
		ml.guardarPersona(persona3);
		Persona persona4 = PersonaTest.crearPersonaLatLngVariable();
		persona4.setEstado(Utils.EST_NUEVO);
		ml.guardarPersona(persona4);
		Persona persona5 = PersonaTest.crearPersonaLatLngVariable();
		persona5.setEstado(Utils.EST_MODIFICADO);
		ml.guardarPersona(persona5);
		
		Personas resultadoNuevas = ml.obtenerPersonasNuevas();
		Personas resultadoModificadas = ml.obtenerPersonasModificadas();
		Personas resultadoBorradas = ml.obtenerPersonasBorradas();

		assertEquals(2, resultadoNuevas.size());
		assertEquals(1, resultadoModificadas.size());
		assertEquals(1, resultadoBorradas.size());
	}
	

	@Override
	protected void tearDown() {
		// Cerramos la base de datos
		ml.desconectarse();
	}
}
