package com.recorridaszo.recorridaszo.test;

import java.util.Random;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.utilitarios.Utils;

import android.test.AndroidTestCase;

public class PersonaTest extends AndroidTestCase {

	public PersonaTest() {
	}

	public static Persona crearPersona() {
		Persona persona = new Persona(10, "nom", "ap", "murguiondo", "Ramos",
				"descripcion1", new LatLng(5.55, 5.01), Utils.getDateTime(),
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

	public void testCrearPersona() {//TODO falta fecha
		Persona persona = PersonaTest.crearPersona();

		assertEquals(10, persona.getId());
		assertEquals("nom", persona.getNombre());
		assertEquals("ap", persona.getApellido());
		assertEquals("murguiondo", persona.getDireccion());
		assertEquals("Ramos", persona.getZona());
		assertEquals("descripcion1", persona.getDescripcion());
		assertEquals(5.55, persona.getLatitud());
		assertEquals(5.01, persona.getLongitud());
		assertEquals(Utils.EST_NUEVO, persona.getEstado());
	}

}
