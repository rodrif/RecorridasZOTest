package com.recorridaszo.recorridaszo.test;

import java.util.Iterator;

import android.test.AndroidTestCase;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;

public class PersonasTest extends AndroidTestCase {

	public PersonasTest() {

	}

	public void testAddPersona() {
		Persona persona = PersonaTest.crearPersonaLatLngVariable();
		Personas lista = new Personas();
		lista.addPersona(persona);

		assertEquals(1, lista.size());
	}

	public void testRemovePersona() {
		Persona persona = PersonaTest.crearPersonaLatLngVariable();
		Personas lista = new Personas();
		lista.addPersona(persona);
		lista.removePersona(persona);

		assertEquals(0, lista.size());
	}

	public void testIterator() {
		Personas lista = new Personas();
		Persona persona1 = PersonaTest.crearPersonaLatLngVariable();
		Persona persona2 = PersonaTest.crearPersonaLatLngVariable();
		Persona persona3 = PersonaTest.crearPersonaLatLngVariable();
		lista.addPersona(persona1);
		lista.addPersona(persona2);
		lista.addPersona(persona3);
		
		Iterator<Persona> it = lista.iterator();
		
		assertEquals(3, lista.size());
		assertEquals(persona1, it.next());
		assertEquals(persona2, it.next());
		assertEquals(persona3, it.next());
	}
	
}
