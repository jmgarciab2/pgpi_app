package com.javier.Back;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class RegistroUsuario {
    private static final String USUARIOS_JSON_FILE = "Back/Back/src/main/resources/usuarios.json";

    // Función para cargar usuarios desde el archivo JSON
    static ArrayList<Usuario> cargarUsuariosDesdeJSON() throws IOException {

        try {
            // Lee el fichero que le pasemos y lo carga en un reader
            Reader reader = Files.newBufferedReader(Paths.get(USUARIOS_JSON_FILE));
            // Convierte el array JSON a un ArrayList de DatosJSON
            ArrayList<Usuario> datos = new Gson().fromJson(reader, new TypeToken<ArrayList<Usuario>>() {}.getType());

            reader.close(); // Cierra el reader
            return datos;

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>(); // Si no ha leído nada, devuelve un array vacío
        }
    }

    // Función para verificar si un usuario ya está registrado en la base de datos
    static boolean usuarioYaRegistrado(String usuario, String contrasena) {
        boolean encontrado = false;
        try {
            ArrayList<Usuario> usuarios = cargarUsuariosDesdeJSON();

            System.out.println(usuarios);
            for (Usuario u : usuarios) {
                if (u.getUsuario().equals(usuario) && u.getContraseña().equals(contrasena)) {
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    // Función para registrar un nuevo usuario
    public static void registrarUsuario(String usuario, String contrasena, String email, String fechaNacimiento) {
        if (!usuarioYaRegistrado(usuario, contrasena)) {
            try {
                // Cargar usuarios existentes desde el archivo JSON
                ArrayList<Usuario> usuarios = cargarUsuariosDesdeJSON();

                // Crear una instancia de Usuario con los datos proporcionados
                Usuario nuevoUsuario = new Usuario(usuario, contrasena, email, fechaNacimiento, new ArrayList<Pedido>());

                // Agregar el nuevo usuario a la lista de usuarios
                usuarios.add(nuevoUsuario);

                // Guardar la lista actualizada de usuarios en el archivo JSON
                guardarUsuariosEnJSON(usuarios);

                // Imprimir mensaje de confirmación
                System.out.println("Usuario registrado correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al registrar el usuario.");
            }
        } else {
            System.out.println("El usuario ya está registrado.");
        }
    }

    // Función para guardar usuarios en el archivo JSON
    private static void guardarUsuariosEnJSON(List<Usuario> usuarios) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(USUARIOS_JSON_FILE), usuarios);
    }
}
