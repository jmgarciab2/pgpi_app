package com.javier.Back;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.javier.Back.Pedido;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class GestionPDF {

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

            // Escribir el encabezado
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Registro de Pedidos");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20);
            // Escribir los detalles del pedido
            contentStream.showText("ID del Pedido: " + pedido.getId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Nombre de la ONG: " + pedido.getNombreONG());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Dirección de entrega: " + pedido.getDirecciones());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Productos:");
            contentStream.newLineAtOffset(0, -20);
            contentStream.newLine();
            for (int i = 0; i < pedido.getProductos().size(); i++) {
                contentStream.showText("Proveedor: " + pedido.getNombresProveedor().get(i));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Producto: " + pedido.getProductos().get(i));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Cantidad: " + pedido.getCantidades().get(i));
                contentStream.newLineAtOffset(0, -20);
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
