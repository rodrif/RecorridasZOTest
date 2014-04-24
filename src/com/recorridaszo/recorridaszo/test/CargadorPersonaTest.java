package com.recorridaszo.recorridaszo.test;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.recorridaszo.persona.CargadorPersona;
import com.recorridaszo.persona.Persona;

public class CargadorPersonaTest extends AndroidTestCase {

	public CargadorPersonaTest() {

	}

	public void testCargarContentValues() {
			Persona persona = PersonaTest.crearPersona();
			
			ContentValues contentValues = CargadorPersona.cargarContentValues(persona);
			
			assertEquals(Integer.valueOf(persona.getId()), contentValues.getAsInteger("id"));
			assertEquals(persona.getNombre(), contentValues.getAsString("nombre"));
			assertEquals(persona.getApellido(),	contentValues.getAsString("apellido"));
			assertEquals(persona.getDireccion(), contentValues.getAsString("direccion"));
			assertEquals(persona.getZona(), contentValues.getAsString("zona"));
			assertEquals(persona.getDescripcion(), contentValues.getAsString("descripcion"));
			assertEquals(persona.getLatitud(), contentValues.getAsDouble("latitud"), 0.0000001);
			assertEquals(persona.getLongitud(), contentValues.getAsDouble("longitud"), 0.0000001);
			assertEquals(persona.getEstado(), contentValues.getAsString("estado"));
		}
}
