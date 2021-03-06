package com.recorridaszo.recorridaszo.test;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDLocal.ManejadorBDLocal;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;
import com.recorridaszo.recorridaszo.personas.PersonaTest;
import com.recorridaszo.utilitarios.Utils;
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

		Personas p = ml.selectTodoPersonas();
		assertEquals(1, p.size());
		// borro la bd local
		ml.borrarTodo();

		p = ml.selectTodoPersonas();
		assertEquals(0, p.size());
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

	public void testObtenerPersonaPorId() {
		ml.borrarTodo();
		Persona persona = new Persona("Alberto", "apellido", new LatLng(-10.0,
				12.0));
		persona.setId(34);
		Persona otra = PersonaTest.crearPersonaLatLngVariable();
		ml.guardarPersona(persona);
		ml.guardarPersona(otra);

		assertEquals(34, ml.obtenerPersonaPorId(persona.getId()).getId());
	}

	public void testRegistrosIguales() {
		ml.borrarTodo();
		Persona nuevaPersona = PersonaTest.crearPersona();
		ml.guardarPersona(nuevaPersona);
		ml.guardarPersona(nuevaPersona);

		assertEquals(1, ml.selectTodoPersonas().size());
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

		Personas p = ml.selectTodoPersonas();

		assertEquals(2, p.size());
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

	public void testUltFechaModFechaCero() {
		ml.borrarTodo();

		assertEquals(Utils.FECHA_CERO, ml.getUltFechaMod());
	}

	public void testUpdateNormal() {
		ml.borrarTodo();
		Persona personaNormal = PersonaTest.crearPersonaLatLngVariable();
		LatLng nuevaUbicacion = new LatLng(-34.61509026099774,
				-58.55060879141091);
		personaNormal.setUbicacion(nuevaUbicacion);
		ml.guardarPersona(personaNormal);
		personaNormal.setNombre("JuanPrueba");
		ml.guardarPersona(personaNormal);

		Persona personaEncontrada = ml.obtenerPersona(personaNormal
				.getUbicacion());

		assertEquals(personaNormal.getNombre(), personaEncontrada.getNombre());
	}

	public void testUpdateProblematico() {
		ml.borrarTodo();
		Persona personaProblematica = PersonaTest.crearPersonaLatLngVariable();
		LatLng nuevaUbicacion = new LatLng(-34.6150902609977411111111,
				-58.550608791410916);
		personaProblematica.setUbicacion(nuevaUbicacion);
		ml.guardarPersona(personaProblematica);
		personaProblematica.setNombre("JuanPrueba");
		ml.guardarPersona(personaProblematica);

		Persona personaEncontrada = ml.obtenerPersona(personaProblematica
				.getUbicacion());

		Log.e(Utils.APPTAG, "nuevaUbicacion " + nuevaUbicacion.longitude);
		assertEquals(personaProblematica.getNombre(),
				personaEncontrada.getNombre());
	}

	public void testEliminarPersona() {
		ml.borrarTodo();
		Persona persona = PersonaTest.crearPersonaLatLngVariable();
		ml.guardarPersona(persona);

		Persona pEliminar = PersonaTest.crearPersonaLatLngVariable();
		LatLng ubicacionParecida = new LatLng(persona.getLatitud()
				+ Utils.PRECISION, persona.getLongitud() - Utils.PRECISION);
		pEliminar.setUbicacion(ubicacionParecida);

		ml.eliminarPersona(pEliminar.getUbicacion());
		Personas p = ml.selectTodoPersonas();

		assertEquals(1, p.size());
	}

	public void testEliminarPersonaInex() {
		ml.borrarTodo();
		int res = ml.eliminarPersona(new LatLng(-5.0, -5.0));

		assertEquals(-1, res);
	}

	public void testInsertarPersona() {
		ml.borrarTodo();
		Persona persona = PersonaTest.crearPersonaLatLngVariable();
		ml.guardarPersona(persona);

		assertEquals(1, ml.selectTodoPersonas().size());
	}

	public void testInsertarPersonaDistinta() {
		ml.borrarTodo();
		Persona persona = PersonaTest.crearPersonaLatLngVariable();
		ml.guardarPersona(persona);

		LatLng ubicacionParecida = new LatLng(persona.getLatitud() + 2
				* Utils.PRECISION, persona.getLongitud());
		persona.setUbicacion(ubicacionParecida);

		ml.guardarPersona(persona);

		assertEquals(2, ml.selectTodoPersonas().size());
	}

	@Override
	protected void tearDown() {
		// Cerramos la base de datos
		ml.desconectarse();
	}
}
