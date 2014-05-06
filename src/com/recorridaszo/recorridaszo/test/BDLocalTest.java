package com.recorridaszo.recorridaszo.test;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.personas.PersonaTest;
import com.recorridaszo.utilitarios.Utils;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

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

	public void testGuardarPersona() {
		ml.borrarTodo();
		Persona persona = PersonaTest.crearPersona();
		String fecha = Utils.getDateTime();
		persona.setUltMod(fecha);
		ml.guardarPersona(persona);

		Persona personaEnBD = ml.obtenerPersona(persona.getUbicacion());

		assertEquals(persona.getId(), personaEnBD.getId());
		assertEquals(persona.getNombre(), personaEnBD.getNombre());
		assertEquals(persona.getApellido(), personaEnBD.getApellido());
		assertEquals(persona.getDireccion(), personaEnBD.getDireccion());
		assertEquals(persona.getZona(), personaEnBD.getZona());
		assertEquals(persona.getDescripcion(), personaEnBD.getDescripcion());
		assertEquals(persona.getLatitud(), personaEnBD.getLatitud(), 0.0000001);
		assertEquals(persona.getLongitud(), personaEnBD.getLongitud(),
				0.0000001);
		assertEquals(persona.getEstado(), personaEnBD.getEstado());
		assertEquals(fecha, personaEnBD.getUltMod());
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
	
	public void testGetUltFechaMod() {
		ml.borrarTodo();
		Persona persona1 = PersonaTest.crearPersonaLatLngVariable();		
		Persona persona2 = PersonaTest.crearPersonaLatLngVariable();
		Persona persona3 = PersonaTest.crearPersonaLatLngVariable();
		
		persona1.setUltMod("2010-05-06T16:10:41-0300");
		persona2.setUltMod("2014-01-06T16:10:41-0300");
		persona3.setUltMod("2014-05-06T16:10:41-0300");
		
		ml.guardarPersona(persona3);
		ml.guardarPersona(persona1);
		ml.guardarPersona(persona2);
		
		assertEquals(persona3.getUltMod(), ml.getUltFechaMod());
	}

	@Override
	protected void tearDown() {
		// Cerramos la base de datos
		ml.desconectarse();
	}
}
