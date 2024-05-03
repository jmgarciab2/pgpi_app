package com.javier.Back;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
class BackApplicationTests {


	//Test unitarios clase DataHandingTest
	private String fichero_test = "./src/main/resources/cp-national-datafile-test.json";
	@Test
	void contextLoads() {
	}
	@Test
	public void modificarDato() {
		DataHanding dataHanding = new DataHanding();
		DatosJSON datoAntiguo = new DatosJSON(); // Ajusta según tu estructura real
		datoAntiguo.set_id(UUID.fromString(UUID.randomUUID().toString()));
		datoAntiguo.setMscode("MS002"); // Ajusta según tu estructura real
		datoAntiguo.setYear("2021");
		// Puedes establecer otros atributos según tu estructura de DatosJSON

		DatosJSON datoNuevo = new DatosJSON(); // Ajusta según tu estructura real
		datoNuevo.set_id(datoAntiguo.get_id());
		datoNuevo.setMscode("MS002"); // Ajusta según tu estructura real
		datoNuevo.setYear("2022");
		// Puedes establecer otros atributos según tu estructura de DatosJSON
		//añado el dato para que pueda probarlo
		ArrayList<DatosJSON> listaActualizada = dataHanding.addDatin(datoAntiguo,fichero_test);
		ArrayList<DatosJSON>listaActualizada2 = dataHanding.modificarDato(datoAntiguo,datoNuevo,fichero_test);
		assertNotNull(listaActualizada);
		assertTrue(!listaActualizada2.isEmpty());
		assertTrue(listaActualizada2.contains(datoNuevo));
	}

	@Test
	public void eliminarDato() {
		DataHanding dataHanding = new DataHanding();
		DatosJSON datoEliminar = new DatosJSON(); // Ajusta según tu estructura real
		datoEliminar.set_id(UUID.fromString(UUID.randomUUID().toString()));
		datoEliminar.setMscode("MS003"); // Ajusta según tu estructura real
		datoEliminar.setYear("2021");
		// Puedes establecer otros atributos según tu estructura de DatosJSON
		//añado el dato para que pueda probarlo
		ArrayList<DatosJSON> listaActualizada = dataHanding.addDatin(datoEliminar,fichero_test);
		ArrayList<DatosJSON> listaActualizada2 = dataHanding.eliminarDato(UUID.fromString(datoEliminar.get_id().toString()),fichero_test);
		assertNotNull(listaActualizada2);
		assertFalse(listaActualizada2.contains(datoEliminar));
	}

	//Test unitarios de la clase GestionarJsonTest

	@Test
	public void leerJSON() {
		GestionarJson gestionarJson = new GestionarJson();
		ArrayList<DatosJSON> datos = gestionarJson.leerJSON(fichero_test);

		// Verificar que la lista de datos no sea nula y contenga al menos un elemento
		assertNotNull(datos);
		assertTrue(datos.size() > 0);
	}

	@Test
	public void escribirJSON() {
		GestionarJson gestionarJson = new GestionarJson();
		DatosJSON nuevoDato = new DatosJSON(UUID.randomUUID(),"MEASA","2007","_ChangeInProportion", 0.0F,0.0F,0.0F,0.0F,"Hola");

		ArrayList<DatosJSON> listaActualizada = gestionarJson.escribirJSON(fichero_test, nuevoDato);

		// Verificar que la lista actualizada no sea nula y contenga el nuevo dato
		assertNotNull(listaActualizada);
		assertTrue(listaActualizada.contains(nuevoDato));

	}
}
