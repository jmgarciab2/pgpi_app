package com.javier.Back;

import java.util.ArrayList;
import java.util.UUID;

public class DataHanding {
    //Aquí se desarrollan los métodos que gestionarán los datos de los ficheros
    private String fichero2 = "./src/main/resources/MsCode_json.json";
    public ArrayList<DatosJSON> obtenDatos(String fichero) {
        GestionarJson lector = new GestionarJson();
        ArrayList<DatosJSON> listaDatos = lector.leerJSON(fichero);
        return listaDatos;
    }
    public ArrayList<DatosJSON> obtenDatosAgrupados(String msCode) {
        GestionarJson lector = new GestionarJson();
        ArrayList<DatosJSON> listaDatos = lector.leerJSONAgrupado(fichero2,msCode);
        return listaDatos;
    }

    public ArrayList<DatosJSON> addDatin(DatosJSON dato, String ficherito) {
        //Le pasamos el objeto al metodo escribirJSON
        GestionarJson escritor = new GestionarJson();
        //Devolvemos una lista actualizada
        return escritor.escribirJSON(ficherito,dato);
    }
    /*El objetivo es modificar el dato antiguo que había en la lista: en este caso será setearle al dato modificado
    el mismo id, borrar el antiguo y llamar al metodo escribir Json, que meterá el nuevo dato y ordenará la lista,
    devolviendo así una lista actualizada, que devolveremos para que se pueda cargar en el grid*/
    public ArrayList<DatosJSON> modificarDato(DatosJSON datoAntiguo, DatosJSON datoNuevo, String ficherito) {

        //Lista auxiliar que recogerá la lista actualizada del metodo escribirJSON
        ArrayList<DatosJSON> lAux = new ArrayList<>();
        //Objeto clase GestorJSON para poder realizar acciones con el fichero
        GestionarJson gestor = new GestionarJson();
        try {
            //Lista para leer el JSON
            ArrayList<DatosJSON> listaDatos = gestor.leerJSON(ficherito);

            //Comprobar que efectivamente los dos tienen el mismo id
            if (datoAntiguo.get_id().equals(datoNuevo.get_id())){
                // Encontrar el dato antiguo en la lista
                DatosJSON antiguoEnLista = null;
                for (DatosJSON dato : listaDatos) {
                    if (dato.get_id().equals(datoAntiguo.get_id())) {
                        antiguoEnLista = dato;
                        break;
                    }
                }
                // Reemplazamos el dato antiguo con el nuevo dato
                if (antiguoEnLista != null) {
                    //Eliminamos el dato de la lista y añadimos el nuevo
                    listaDatos.remove(antiguoEnLista);
                    listaDatos.add(datoNuevo);
                    //Recibimos la lista actualizada que escribimos con el dato ya modificado y la lista ordenada
                    lAux = gestor.escribirLista(ficherito,listaDatos);
                }

            } else {
                System.out.println("No son el mismo objeto.");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en la manipulación del archivo JSON", e);
        }

        return lAux;
    }

    public ArrayList<DatosJSON> eliminarDato(UUID id, String ficherito) {
        //Lista auxiliar que recogerá la lista actualizada del metodo escribirJSON
        ArrayList<DatosJSON> lAux = new ArrayList<>();
        //Objeto clase GestorJSON para poder realizar acciones con el fichero
        GestionarJson gestor = new GestionarJson();
        try {
            //Lista para leer el JSON
            ArrayList<DatosJSON> listaDatos = gestor.leerJSON(ficherito);

                // Encontrar el dato antiguo en la lista
                DatosJSON antiguoEnLista = null;
                for (DatosJSON dato : listaDatos) {
                    if (dato.get_id().equals(id)) {
                        //Encontramos el objeto
                        antiguoEnLista = dato;
                        break;
                    }
                }
                // Eliminamos el dato con el id que pasamos
                if (antiguoEnLista != null) {
                    //Eliminamos el dato de la lista
                    listaDatos.remove(antiguoEnLista);
                    //Recibimos la lista actualizada que escribimos con el dato eliminado
                    lAux = gestor.escribirLista(ficherito,listaDatos);
                }


        } catch (RuntimeException e) {
            throw new RuntimeException("Error en la manipulación del archivo JSON", e);
        }

        return lAux;
    }
    public ArrayList<DatosXLSX> obtenDatosPoblacion(String fichero) {
        GestionXLSX lector = new GestionXLSX();
        ArrayList<DatosXLSX> listaDatos = lector.leerDatosDesdeXLSX(fichero);
        return listaDatos;
    }
    public ArrayList<String> recibirPedido(Pedido pedido) {
        GestionXLSX lector = new GestionXLSX();
        ArrayList<String> result = lector.recibirPedidoAyuda(pedido.getNombreONG(), pedido.getDirecciones(), pedido.getNombresProveedor(), pedido.getProductos(), pedido.getCantidades());
        return result;
    }
    //inicio sesion

}
