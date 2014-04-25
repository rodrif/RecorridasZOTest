package com.recorridaszo.recorridaszo.test;

import android.test.AndroidTestCase;

import com.google.android.gms.maps.model.LatLng;
import com.recorridaszo.BDWeb.ManejadorBDWeb;
import com.recorridaszo.persona.Persona;
import com.recorridaszo.persona.Personas;

public class BDWebTest extends AndroidTestCase{
	ManejadorBDWeb manejador = ManejadorBDWeb.getInstance();
	Persona unaPersona = PersonaTest.crearPersona();
	Personas personas = new Personas();
	
	@Override
	protected void setUp() {
		personas.addPersona(unaPersona);
	}
	
	public void testInsertar() {		
		String resultado = manejador.insertar(unaPersona, getContext());
		assertEquals("No inserto en DBWeb","bien", resultado);
	}
	
	public void testBuscar() {
		Persona personaBuscada = manejador.buscar(new LatLng(unaPersona.getLatitud(), 
				unaPersona.getLongitud()));
		
		assertEquals(personaBuscada.getApellido(), unaPersona.getApellido());
		assertEquals(personaBuscada.getDescripcion(), unaPersona.getDescripcion());
		assertEquals(personaBuscada.getDireccion(), unaPersona.getDireccion());
		assertEquals(personaBuscada.getEstado(), unaPersona.getEstado());
		assertEquals(personaBuscada.getLatitud(), unaPersona.getLatitud());
		assertEquals(personaBuscada.getLongitud(), unaPersona.getLongitud());
		assertEquals(personaBuscada.getNombre(), unaPersona.getNombre());
		assertEquals(personaBuscada.getZona(), unaPersona.getZona());
		assertEquals(personaBuscada.getUltMod(), unaPersona.getUltMod());
	}
	
	public void borrar(){
		manejador.borrar(personas);//TODO
	}

}
