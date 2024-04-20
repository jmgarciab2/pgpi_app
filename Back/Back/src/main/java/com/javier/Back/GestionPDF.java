package com.javier.Back;

import com.google.gson.Gson;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GestionPDF {

    // Método para imprimir el pedido en un archivo PDF
    public static void imprimirPedidoEnPDF(Pedido pedido, String filePath) {
        try {
            // Crear un nuevo documento PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Crear un nuevo stream de contenido para escribir en la página
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Configurar la fuente y el tamaño del texto
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Escribir el encabezado del pedido
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Registro de Pedidos");
            contentStream.newLine();
            contentStream.showText("ID del Pedido: " + pedido.getId());
            contentStream.newLine();
            contentStream.showText("Nombre de la ONG: " + pedido.getNombreONG());
            contentStream.newLine();
            contentStream.showText("Dirección de entrega: " + pedido.getDirecciones());
            contentStream.newLine();
            contentStream.newLine();

            // Escribir los detalles del pedido
            ArrayList<String> nombresProveedor = pedido.getNombresProveedor();
            ArrayList<Double> cantidades = pedido.getCantidades();
            ArrayList<String> productos = pedido.getProductos();
            for (int i = 0; i < productos.size(); i++) {
                String registro = "Proveedor: " + nombresProveedor.get(i) + ", Producto: " + productos.get(i) + ", Cantidad: " + cantidades.get(i);
                contentStream.showText(registro);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            // Guardar el documento PDF en el archivo especificado
            document.save(new File(filePath));
            document.close();
            System.out.println("Pedido impreso en PDF exitosamente en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
