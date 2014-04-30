package com.recorridaszo.recorridaszo.test;

import android.test.AndroidTestCase;


public class BDWebTest extends AndroidTestCase {//TODO: hacer
/*	ManejadorBDWeb manejador = ManejadorBDWeb.getInstance();
	Persona unaPersona = PersonaTest.crearPersona();
	Personas personas = new Personas();

	@Override
	protected void setUp() {
		personas.addPersona(unaPersona);
	}

	public void testInsertar() { // REVISAR		
		String resultado = manejador.insertar(unaPersona, getContext(),
				new MapaActivity());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("No inserto en DBWeb", "bien", resultado);
	}
*/
/*	public void testBuscar() { //TODO: no implementado en php
		String resultado = manejador.insertar(unaPersona, getContext(),
				new MapaActivity());
		assertEquals("No inserto en DBWeb", "bien", resultado);
		Persona personaBuscada = manejador.buscar(new LatLng(unaPersona
				.getLatitud(), unaPersona.getLongitud()));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (personaBuscada != null) {
			assertEquals(personaBuscada.getApellido(), unaPersona.getApellido());
			assertEquals(personaBuscada.getDescripcion(),
					unaPersona.getDescripcion());
			assertEquals(personaBuscada.getDireccion(),
					unaPersona.getDireccion());
			assertEquals(personaBuscada.getEstado(), unaPersona.getEstado());
			assertEquals(personaBuscada.getLatitud(), unaPersona.getLatitud());
			assertEquals(personaBuscada.getLongitud(), unaPersona.getLongitud());
			assertEquals(personaBuscada.getNombre(), unaPersona.getNombre());
			assertEquals(personaBuscada.getZona(), unaPersona.getZona());
			assertEquals(personaBuscada.getUltMod(), unaPersona.getUltMod());
		}
		else {
			fail("falló al buscar una persona en la BDWeb");
		}
	}

	public void Testborrar() {
		manejador.borrarDBWEB();
	}
*/
}
