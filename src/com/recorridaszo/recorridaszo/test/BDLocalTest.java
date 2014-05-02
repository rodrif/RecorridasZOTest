package com.recorridaszo.recorridaszo.test;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.personas.PersonaTest;
import com.recorridaszo.utilitarios.Utils;

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

	public void testBorrarTodo() {
		Persona persona = PersonaTest.crearPersona();
		// borro la bd local
		ml.borrarTodo();		
		ml.guardarPersona(persona);
		
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
		assertEquals(persona.getUltMod(), c.getString(c.getColumnIndex("ultMod")));
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

	//TODO: falta agregar a la BBDD las claves primarias
	public void testRegistrosIguales() {
		ml.borrarTodo();
		Persona nuevaPersona = PersonaTest.crearPersona();
		ml.guardarPersona(nuevaPersona);
		ml.guardarPersona(nuevaPersona);

		assertEquals(1, ml.selectTodo().getCount());
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
		assertEquals(0, resultadoBorradas.size());
	}
	
	public void testEliminarPersonasActualizadas() {
		ml.borrarTodo();
		Persona persona1 = PersonaTest.crearPersonaLatLngVariable();
		persona1.setEstado(Utils.EST_NUEVO);
		ml.guardarPersona(persona1);
		Persona persona2 = PersonaTest.crearPersonaLatLngVariable();
		persona2.setEstado(Utils.EST_ACTUALIZADO);
		ml.guardarPersona(persona2);
		Persona persona5 = PersonaTest.crearPersonaLatLngVariable();
		persona5.setEstado(Utils.EST_MODIFICADO);
		ml.guardarPersona(persona5);
		
		ml.eliminarPersonasActualizadas();
		
		Cursor c = ml.selectTodo();
		
		assertEquals(2, c.getCount());
	}
	

	@Override
	protected void tearDown() {
		ml.desconectarse();
	}
}
