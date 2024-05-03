package com.javier.Back;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GestionXLSX {
    private static ArrayList<DatosXLSX> stockLocal = new ArrayList<>();
    private static Set<Integer> idsUtilizados = new HashSet<>();

    public static ArrayList<DatosXLSX> leerDatosDesdeXLSX(String filePath) {
        ArrayList<DatosXLSX> listaDatos = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Empezamos desde la fila 1 para evitar la fila de encabezados
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String proveedorOrigen = row.getCell(0).getStringCellValue();
                    String referencia = row.getCell(1).getStringCellValue();
                    double cantidad = row.getCell(2).getNumericCellValue();
                    int tiempoEntregaDias = (int) row.getCell(3).getNumericCellValue();

                    DatosXLSX datos = new DatosXLSX(proveedorOrigen, referencia, cantidad, tiempoEntregaDias);
                    listaDatos.add(datos);
                }
            }

            inputStream.close();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }
    // Método para recibir un pedido de ayuda en el portal
    public static ArrayList<String> recibirPedidoAyuda(String nombreONG, String direccionONG, ArrayList<String> nombresProveedor, ArrayList<String> referencias, ArrayList<Double> cantidades) {
        ArrayList<String> listaPicking = new ArrayList<>();
        // Verificar disponibilidad de stock para cada referencia en el pedido
        for (int i = 0; i < referencias.size(); i++) {
            String nombreProveedor = nombresProveedor.get(i);
            String referencia = referencias.get(i);
            double cantidadPedido = cantidades.get(i);

            // Buscar el producto en el stock local por nombre del proveedor y referencia
            DatosXLSX producto = buscarProductoPorProveedorYReferencia(nombreProveedor, referencia);
            if (producto != null) {
                // Si hay suficiente cantidad en stock
                if (producto.getCantidad() >= cantidadPedido) {
                    // Se descuenta la cantidad indicada
                    producto.setCantidad(producto.getCantidad() - cantidadPedido);
                    // Se genera un registro en la lista de picking
                    String registroPicking = "ID Pedido: " + generarIDPedido() + ", Cantidad: " + cantidadPedido + ", Ubicación: " + direccionONG;
                    listaPicking.add(registroPicking);
                } else {
                    // Si no hay suficiente stock, se genera un faltante
                    System.out.println("Faltante para el producto: " + referencia + " del proveedor: " + nombreProveedor);
                }
            } else {
                // Si el producto no está en el stock local, se genera un faltante
                System.out.println("Faltante para el producto: " + referencia + " del proveedor: " + nombreProveedor);
            }
        }

        // Regresar la lista de picking generada
        return listaPicking;
    }

    // Método auxiliar para buscar un producto por nombre del proveedor y referencia en el stock local
    private static DatosXLSX buscarProductoPorProveedorYReferencia(String nombreProveedor, String referencia) {
        for (DatosXLSX producto : stockLocal) {
            if (producto.getProveedorOrigen().equals(nombreProveedor) && producto.getReferencia().equals(referencia)) {
                return producto;
            }
        }
        return null;
    }



    // Método auxiliar para generar un ID de pedido aleatorio
    private static int generarIDPedido() {
        int nuevoID;
        do {
            nuevoID = (int) (Math.random() * 10000);
        } while (idsUtilizados.contains(nuevoID));
        idsUtilizados.add(nuevoID);
        return nuevoID;
    }

    // Método para agregar productos al stock local (solo para propósitos de demostración)
    public static void agregarProductosAlStockLocal(ArrayList<DatosXLSX> productos) {
        stockLocal.addAll(productos);
    }
}
