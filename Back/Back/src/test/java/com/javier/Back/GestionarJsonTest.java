package com.javier.Back;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class GestionarJsonTest {
    //Usamos el fichero de prueba
    private String fichero_test = "./src/main/resources/cp-national-datafile-test.json";

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