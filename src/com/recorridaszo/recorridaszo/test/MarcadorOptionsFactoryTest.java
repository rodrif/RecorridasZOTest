package com.recorridaszo.recorridaszo.test;

import android.test.AndroidTestCase;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.recorridaszo.MarcadorOptionsFactory;
import com.recorridaszo.recorridaszo.personas.PersonaTest;
import com.recorridaszo.utilitarios.Utils;

public class MarcadorOptionsFactoryTest extends AndroidTestCase {

	public MarcadorOptionsFactoryTest() {

	}

	@Override
	protected void setUp() {
		MapsInitializer.initialize(getContext());
	}

	public void testCrearOpciones() {// TODO: arreglar
		Persona p = PersonaTest.crearPersona();
		LatLng ubicacion = p.getUbicacion();

		MarkerOptions markerOptions1 = MarcadorOptionsFactory.crearOpciones(
				Utils.EST_NUEVO, ubicacion);
		MarkerOptions markerOptions2 = MarcadorOptionsFactory.crearOpciones(
				Utils.EST_ACTUALIZADO, ubicacion);
		MarkerOptions markerOptions3 = MarcadorOptionsFactory.crearOpciones(
				Utils.EST_MODIFICADO, ubicacion);
		MarkerOptions markerOptions4 = MarcadorOptionsFactory.crearOpciones(
				"inexistente", ubicacion);

		assertEquals(
				BitmapDescriptorFactory.defaultMarker(
						BitmapDescriptorFactory.HUE_AZURE).toString(),
				markerOptions1.getIcon().toString());
		// assertEquals(BitmapDescriptorFactory.HUE_GREEN,
		// markerOptions2.getIcon());
		// assertEquals(BitmapDescriptorFactory.HUE_YELLOW,
		// markerOptions3.getIcon());
		// assertEquals(BitmapDescriptorFactory.HUE_RED,
		// markerOptions4.getIcon());
	}
}
