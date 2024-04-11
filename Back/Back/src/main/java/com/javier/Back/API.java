package com.javier.Back;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class API {
    //Aquí se van a crear las peticiones a la API para poder crear, eliminar y modificar los datos de los ficheros JSON
    //Llamarán a los métodos de la clase DataHanding para gestionar los datos
    @GetMapping("/obtenerDatos")
    public ArrayList<DatosJSON> datos(){
        DataHanding data = new DataHanding();
        String fichero = "Back/Back/src/main/resources/cp-national-datafile-test.json";
        return data.obtenDatos(fichero);

    }
    @GetMapping("/obtenerDatosAgrupados/{msCode}")
    public ArrayList<DatosJSON> datosAgrupados(@PathVariable String msCode){
        DataHanding data = new DataHanding();
        return data.obtenDatosAgrupados(msCode);

    }

    @PostMapping("/addDatos")
    public ArrayList<DatosJSON> addDatos(@RequestBody DatosJSON dato){
        DataHanding data = new DataHanding();
        String fichero = "./src/main/resources/cp-national-datafile.json";
        //Llamamos al método de añadir datos
        return data.addDatin(dato,fichero);
    }

    @PutMapping("/actualizarDatos")
    public ArrayList<DatosJSON> modificarDato(@RequestBody ArrayList<DatosJSON> datosActualizar) throws ClassNotFoundException {
        DataHanding data = new DataHanding();
        String fichero = "./src/main/resources/cp-national-datafile.json";
        DatosJSON datoAntiguo = datosActualizar.get(0);
        DatosJSON datoNuevo = datosActualizar.get(1);
        return data.modificarDato(datoAntiguo, datoNuevo,fichero);
    }

    @DeleteMapping("/eliminarDato/{_id}")
    public ArrayList<DatosJSON> eliminarDato(@PathVariable UUID _id) {
        DataHanding data = new DataHanding();
        String fichero = "./src/main/resources/cp-national-datafile.json";
        // Llamamos al método de eliminar datos y nos devuelve ya la lista actualizada
        return data.eliminarDato(_id,fichero);
    }
    @GetMapping("/obtenerDatosAyudaHumanitaria")
    public ArrayList<DatosXLSX> getDatosAyudaHumanitaria(){
        DataHanding data = new DataHanding();
        String fichero = "Back/Back/src/main/resources/Ejemplo_Proov_Ref_2024.xlsx";
        return data.obtenDatosPoblacion(fichero);
    }
    @PutMapping("/recibirPedido")
    public ArrayList<String> recibirPedido(@RequestBody Pedido pedido){
        DataHanding data = new DataHanding();
        return data.recibirPedido(pedido);
    }
    //Registro
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        RegistroUsuario registroUsuario = new RegistroUsuario();
        if (!registroUsuario.usuarioYaRegistrado(nuevoUsuario.getUsuario(), nuevoUsuario.getContraseña())) {
            try {
                // Llamamos al método de RegistroUsuario para registrar el nuevo usuario
                registroUsuario.registrarUsuario(nuevoUsuario.getUsuario(), nuevoUsuario.getContraseña(), nuevoUsuario.getEmail(), nuevoUsuario.getFechaNacimiento());

                // Retorna una respuesta exitosa si el usuario se registró correctamente
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
                // Retorna un mensaje de error si hubo algún problema al registrar el usuario
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario.");
            }
        } else {
            // Retorna un mensaje de error si el usuario ya está registrado
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya está registrado.");
        }
    }

}
