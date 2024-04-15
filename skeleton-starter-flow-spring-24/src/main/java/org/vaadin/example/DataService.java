package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.UUID;

public class DataService {
    private static final String urlPrefix = "http://backend:8081";
    public static ArrayList<DatosJSON> obtenerListaDatos()  throws IOException, URISyntaxException {
        ArrayList<DatosJSON> datos = new ArrayList<>();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(urlPrefix + "/obtenerDatos")).GET().build();
        Gson gson = new Gson();
        String resultado = null;
        HttpResponse<String> respuesta = null;

        try {
            respuesta = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            resultado = respuesta.body();
            datos = gson.fromJson(resultado, new TypeToken<ArrayList<DatosJSON>>() {
            }.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return datos;
    }
    public static ArrayList<DatosJSON> obtenerListaDatosPorMsCode(String msCodeSeleccionado) throws IOException, URISyntaxException{
        ArrayList<DatosJSON> datos = new ArrayList<>();

        // Construir la URL con el msCode proporcionado
        URI uri = new URI(urlPrefix + "/obtenerDatosAgrupados/" + msCodeSeleccionado);

        // Crear la solicitud GET
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().build();

        // Crear un objeto Gson para deserializar la respuesta
        Gson gson = new Gson();

        // Variables para almacenar el resultado de la solicitud
        String resultado = null;
        HttpResponse<String> respuesta = null;

        try {
            // Enviar la solicitud y obtener la respuesta
            respuesta = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            resultado = respuesta.body();

            // Deserializar la respuesta a la lista de DatosJSON
            datos = gson.fromJson(resultado, new TypeToken<ArrayList<DatosJSON>>() {
            }.getType());

        } catch (IOException | InterruptedException e) {
            // Manejar las excepciones
            throw new RuntimeException(e);
        }

        // Devolver la lista de datos
        return datos;
    }

    public static boolean addDato(@RequestBody DatosJSON datin) {
        // Crear una instancia de Gson para convertir objetos a JSON y viceversa
        Gson g = new Gson();
        boolean result = false;
        ArrayList<DatosJSON> lAux = new ArrayList<>();
        // Obtener la representación en formato JSON de los datos utilizando el método mostrarJson()
        String datospasar = datin.mostrarJson();

        try {
            // Construir la solicitud HTTP para enviar datos al endpoint "/addDatos"
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlPrefix + "/addDatos")) // Asegúrate de que urlPrefix esté definido y apunte al backend correcto
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(datospasar))
                    .build();

            // Enviar la solicitud y obtener la respuesta del servidor
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Obtener la respuesta en formato JSON del servidor
            String respuestaActual = response.body();

            // Convertir la respuesta JSON a un objeto DatosJSON usando Gson
            lAux = g.fromJson(respuestaActual, new TypeToken<ArrayList<DatosJSON>>() {}.getType());

            // Puedes realizar acciones adicionales con el objeto 'datin' después de la conversión

        } catch (IOException | InterruptedException e) {
            // Manejar cualquier excepción ocurrida durante la comunicación
            throw new RuntimeException(e);
        }
        if (datin.getMsCode() != null){
            result = true;
        }
        return result;
    }


    public static ArrayList<DatosJSON> actualizarDatos(@RequestBody ArrayList<DatosJSON> listaDatossaux) throws URISyntaxException, IOException, InterruptedException {
        // Crear una instancia de Gson para convertir objetos a JSON y viceversa
        Gson g = new Gson();

        String jsonpasado = g.toJson(listaDatossaux);

        // Construir la solicitud HTTP para enviar la lista de datos al backend
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlPrefix + "/actualizarDatos"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonpasado))
                .build();

        // Enviar la solicitud y obtener la respuesta del servidor
            HttpResponse<String> response = null;

            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        // Analizar la respuesta JSON del servidor a una lista de DatosJSON
        ArrayList<DatosJSON> listaDatosActualizada = g.fromJson(response.body(), new TypeToken<ArrayList<DatosJSON>>() {}.getType());

        return listaDatosActualizada;
    }

    @DeleteMapping("/eliminarDato/{_id}")
    public static ArrayList<DatosJSON> eliminarDato(@PathVariable UUID _id) {
        try {
            // Crear una instancia de Gson para convertir objetos a JSON y viceversa
            Gson g = new Gson();

            // Crear una instancia de HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Construir la solicitud HTTP DELETE
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlPrefix + "/eliminarDato/" + _id))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();

            // Enviar la solicitud y obtener la respuesta del servidor
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Convertir la respuesta JSON a una lista de DatosJSON usando Gson
            ArrayList<DatosJSON> listaDevuelta = g.fromJson(response.body(), new TypeToken<ArrayList<DatosJSON>>() {}.getType());

            return listaDevuelta;

        } catch (Exception e) {
            throw new RuntimeException("Error en la comunicación con el servidor", e);
        }
    }

    public static ArrayList<DatosXLSX> ObtenerDatosXLSX() throws IOException, URISyntaxException{
        ArrayList<DatosXLSX> datos = new ArrayList<>();

        // Construir la URL con el msCode proporcionado
        URI uri = new URI(urlPrefix + "/obtenerDatosAyudaHumanitaria/");

        // Crear la solicitud GET
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().build();

        // Crear un objeto Gson para deserializar la respuesta
        Gson gson = new Gson();

        // Variables para almacenar el resultado de la solicitud
        String resultado = null;
        HttpResponse<String> respuesta = null;

        try {
            // Enviar la solicitud y obtener la respuesta
            respuesta = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            resultado = respuesta.body();

            // Deserializar la respuesta a la lista de DatosJSON
            datos = gson.fromJson(resultado, new TypeToken<ArrayList<DatosXLSX>>() {
            }.getType());

        } catch (IOException | InterruptedException e) {
            // Manejar las excepciones
            throw new RuntimeException(e);
        }

        // Devolver la lista de datos
        return datos;
    }
}

