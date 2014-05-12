package com.recorridaszo.recorridaszo.personas;

import java.util.Random;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.utilitarios.Utils;

import android.test.AndroidTestCase;

public class PersonaTest extends AndroidTestCase {

	public PersonaTest() {
	}

	public static Persona crearPersona() {
		Persona persona = new Persona(-1, "nom", "ap", "murguiondo", "Ramos",
				"descripcion1", new LatLng(-34.61509026099774, -58.42213869094848), Utils.getDateTime(),
				Utils.EST_NUEVO);

		return persona;
	}

	public static Persona crearPersonaLatLngVariable() {
		Persona persona = PersonaTest.crearPersona();
		Random r = new Random();
		LatLng nuevaUbicacion = new LatLng(r.nextDouble(), r.nextDouble());
		persona.setUbicacion(nuevaUbicacion);

		return persona;
	}

	public void testCrearPersona() {
		String fecha = Utils.getDateTime();
		Persona persona = PersonaTest.crearPersona();

		assertEquals(-1, persona.getId());
		assertEquals("nom", persona.getNombre());
		assertEquals("ap", persona.getApellido());
		assertEquals("murguiondo", persona.getDireccion());
		assertEquals("Ramos", persona.getZona());
		assertEquals("descripcion1", persona.getDescripcion());
		assertEquals(-34.61509026099774, persona.getLatitud());
		assertEquals(-58.42213869094848, persona.getLongitud());
		assertEquals(Utils.EST_NUEVO, persona.getEstado());
		assertEquals(fecha, persona.getUltMod());
	}

}
