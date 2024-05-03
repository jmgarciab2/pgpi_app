package com.javier.Back;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataHandingTest {
    private DataHanding dataHanding = new DataHanding();
    private String fichero_test = "./src/main/resources/cp-national-datafile-test.json";
    @Test
    public void modificarDato() {
        DatosJSON datoAntiguo = new DatosJSON();
        datoAntiguo.set_id(UUID.fromString(UUID.randomUUID().toString()));
        datoAntiguo.setMscode("MS002");
        datoAntiguo.setYear("2021");
        // Puedes establecer otros atributos según tu estructura de DatosJSON

        DatosJSON datoNuevo = new DatosJSON();
        datoNuevo.set_id(datoAntiguo.get_id());
        datoNuevo.setMscode("MS002");
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
        DatosJSON datoEliminar = new DatosJSON();
        datoEliminar.set_id(UUID.fromString(UUID.randomUUID().toString()));
        datoEliminar.setMscode("MS003");
        datoEliminar.setYear("2021");
        // Puedes establecer otros atributos según tu estructura de DatosJSON
        //añado el dato para que pueda probarlo
        ArrayList<DatosJSON> listaActualizada = dataHanding.addDatin(datoEliminar,fichero_test);
        ArrayList<DatosJSON> listaActualizada2 = dataHanding.eliminarDato(UUID.fromString(datoEliminar.get_id().toString()),fichero_test);
        assertNotNull(listaActualizada2);
        assertFalse(listaActualizada2.contains(datoEliminar));
    }


}