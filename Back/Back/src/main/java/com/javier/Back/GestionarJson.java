package com.javier.Back;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class GestionarJson {

    public ArrayList<DatosJSON> leerJSON(String fichero) {
        try {
            // Lee el fichero que le pasemos y lo carga en un reader
            Reader reader = Files.newBufferedReader(Paths.get(fichero));

            // Convierte el array JSON a un ArrayList de DatosJSON
            ArrayList<DatosJSON> datos = new Gson().fromJson(reader, new TypeToken<ArrayList<DatosJSON>>() {}.getType());

            reader.close(); // Cierra el reader
            return datos;

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>(); // Si no ha leído nada, devuelve un array vacío
        }
    }
    //Método que leerá el fichero de MsCode_json.json y sacará la lista en función del msCode pasado
    public ArrayList<DatosJSON> leerJSONAgrupado(String fichero2, String msCode) {
        // Lista para almacenar los objetos DatosJSON resultantes
        ArrayList<DatosJSON> resultado = new ArrayList<>();

        try (FileReader reader = new FileReader(fichero2)) {
            // Crear un objeto Gson para deserializar el JSON
            Gson gson = new Gson();

            // Leer el JSON como un objeto JSON
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Verificar si el JSON contiene una clave (msCode) específica
            if (jsonObject.has(msCode)) {
                // Obtener la lista asociada a la clave msCode
                JsonArray jsonArray = jsonObject.getAsJsonArray(msCode);

                // Iterar sobre los elementos de la lista
                for (int i = 0; i < jsonArray.size(); i++) {
                    // Obtener cada elemento como un objeto JSON
                    JsonObject jsonElement = jsonArray.get(i).getAsJsonObject();

                    // Deserializar el objeto JSON a la clase DatosJSON
                    DatosJSON datos = gson.fromJson(jsonElement, DatosJSON.class);

                    // Agregar el objeto DatosJSON a la lista resultado
                    resultado.add(datos);
                }
            }
        } catch (Exception e) {
            // Manejar cualquier excepción y mostrar la traza de errores
            e.printStackTrace();
        }

        // Devolver la lista resultado
        return resultado;
    }

    public ArrayList<DatosJSON> escribirJSON(String fichero, DatosJSON dato) {
        try {
            // Creamos una lista auxiliar
            ArrayList<DatosJSON> listAux = leerJSON(fichero);

            // Agregamos el nuevo objeto a la lista existente
            listAux.add(dato);

            // Ordenamos la lista por msCode y luego por year
            listAux.sort(
                    Comparator.comparing(DatosJSON::getMscode)
                            .thenComparing(Comparator.comparing(DatosJSON::getYear))
            );

            // Creamos una nueva clase Json con formato legible
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Creamos la clase que permite escribir en el fichero Json
            Writer writer = new FileWriter(fichero);
            try {
                // Escribimos la lista completa al archivo
                Type listType = new TypeToken<ArrayList<DatosJSON>>() {}.getType();
                gson.toJson(listAux, listType, writer);
                writer.close();
            } catch (JsonIOException e) {
                throw new RuntimeException(e);
            }

            return listAux; // Devolvemos la lista actualizada y ordenada

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DatosJSON> escribirLista(String fichero, ArrayList<DatosJSON> listaDatos) {
        try {
            // Ordenamos la lista por msCode y luego por year
            listaDatos.sort(
                    Comparator.comparing(DatosJSON::getMscode)
                            .thenComparing(Comparator.comparing(DatosJSON::getYear))
            );

            // Creamos una nueva clase Json con formato legible
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Creamos la clase que permite escribir en el fichero Json
            try (Writer writer = new FileWriter(fichero)) {
                // Escribimos la lista completa al archivo
                Type listType = new TypeToken<ArrayList<DatosJSON>>() {}.getType();
                gson.toJson(listaDatos, listType, writer);
            } catch (JsonIOException | IOException e) {
                throw new RuntimeException("Error al escribir en el archivo JSON", e);
            }

            return listaDatos; // Devolvemos la lista actualizada y ordenada

        } catch (RuntimeException e) {
            throw e;  // Relanza la excepción para mantener la consistencia con el manejo anterior
        }
    }


}
