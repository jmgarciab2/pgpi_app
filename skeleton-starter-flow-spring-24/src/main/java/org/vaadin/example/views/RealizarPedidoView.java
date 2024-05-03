package org.vaadin.example.views;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import org.vaadin.example.DataService;
import org.vaadin.example.objetos_parametro.DatosXLSX;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route("realizar-pedido")
public class RealizarPedidoView extends VerticalLayout {

    private List<DatosXLSX> productos;
    private Grid<DatosXLSX> grid;
    private TextField filtroReferencia;
    private Dialog dialog;
    private Map<DatosXLSX, Integer> cantidadesSeleccionadas = new HashMap<>();

    public RealizarPedidoView() {
        productos = DatosAyudaHumanitariaOrigenLayout.productos;
        initializeGrid();
        initializeFilter();

        Button realizarPedidoButton = new Button("Realizar Pedido");
        realizarPedidoButton.addClickListener(event -> mostrarDialogoPedido());

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.BASELINE);

        // Crear los tabs
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        // Añadir las pestañas
        Tab tab1 = new Tab("Lista de Productos");
        Tab tab2 = new Tab("Realizar Pedido");
        tabs.add(tab1, tab2);

        // Agregar los tabs al contenedor
        add(tabs);


        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab() == tab1) {
                UI.getCurrent().navigate("datos-ayuda-humanitaria-origen");
            } else if (event.getSelectedTab() == tab2) {
                UI.getCurrent().navigate("realizar-pedido");
            }
        });

        tabs.setSelectedTab(tab2);
        add(tabs, filtroReferencia, grid, realizarPedidoButton);
        setAlignItems(Alignment.CENTER);
    }

    private void initializeGrid() {
        grid = new Grid<>();
        grid.setItems(productos);

        grid.addColumn(DatosXLSX::getReferencia).setHeader("Referencia");
        grid.addComponentColumn(this::createCantidadField).setHeader("Cantidad");
    }

    private void initializeFilter() {
        filtroReferencia = new TextField();
        filtroReferencia.setPlaceholder("Filtrar por referencia...");
        filtroReferencia.setValueChangeMode(ValueChangeMode.EAGER);
        filtroReferencia.addValueChangeListener(event -> filtrarPorReferencia(event.getValue()));
    }

    private IntegerField createCantidadField(DatosXLSX producto) {
        IntegerField cantidadField = new IntegerField();
        cantidadField.setValue(0);
        cantidadField.setMin(0);
        cantidadField.setMax(100);
        cantidadField.addValueChangeListener(event -> actualizarCantidadPedido(producto, event.getValue()));
        return cantidadField;
    }

    private void actualizarCantidadPedido(DatosXLSX producto, int cantidad) {
        cantidadesSeleccionadas.put(producto, cantidad);
    }

    private void filtrarPorReferencia(String referencia) {
        List<DatosXLSX> productosFiltrados = productos.stream()
                .filter(producto -> producto.getReferencia().toLowerCase().contains(referencia.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(productosFiltrados);
    }

    private void mostrarDialogoPedido() {
        dialog = new Dialog();
        dialog.add(new H3("Pedido"));

        VerticalLayout contenido = new VerticalLayout();
        contenido.setSpacing(true);
        contenido.setPadding(true);
        contenido.getStyle().set("background-color", "#f9f9f9");

        for (Map.Entry<DatosXLSX, Integer> entry : cantidadesSeleccionadas.entrySet()) {
            DatosXLSX producto = entry.getKey();
            Integer cantidad = entry.getValue();
            if (cantidad != null && cantidad > 0) {
                HorizontalLayout elementoPedido = new HorizontalLayout();
                elementoPedido.setWidthFull();
                elementoPedido.setAlignItems(Alignment.CENTER);

                Span nombreProducto = new Span(producto.getReferencia());
                Span cantidadSeleccionada = new Span("Cantidad: " + cantidad);

                nombreProducto.getStyle().set("flex-grow", "1");
                cantidadSeleccionada.getStyle().set("margin-left", "10px");

                elementoPedido.add(nombreProducto, cantidadSeleccionada);
                contenido.add(elementoPedido);
            }
        }

        Button cerrarButton = new Button("Cerrar", event -> dialog.close());
        Button generarPDFButton = new Button("Generar PDF");
        generarPDFButton.addClickListener(event -> generarYDescargarPDF());

        dialog.add(contenido, new HorizontalLayout(generarPDFButton, cerrarButton));
        dialog.open();
    }

    private void generarYDescargarPDF() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            Document document = new Document(pdfDocument, PageSize.A4);

            // Estilos de fuente y párrafo
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            Paragraph title = new Paragraph("Pedido").setFont(font).setFontSize(24).setBold();
            Paragraph subtitle = new Paragraph("Detalles del Pedido:").setFont(font).setFontSize(20);

            // Añadir título al documento
            document.add(title);
            document.add(new Paragraph("\n"));

            // Actualizar las cantidades en la lista completa de productos
            for (DatosXLSX producto : productos) {
                Integer cantidadSeleccionada = cantidadesSeleccionadas.getOrDefault(producto, 0);
                if (cantidadSeleccionada != null && cantidadSeleccionada > 0) {
                    // Reducir la cantidad del producto
                    int nuevaCantidad = (int) producto.getCantidad() - cantidadSeleccionada;
                    if (nuevaCantidad < 0) {
                        nuevaCantidad = 0; // Asegurarse de que no haya cantidades negativas
                    }
                    producto.setCantidad(nuevaCantidad);
                }
            }

            // Agregar los elementos del mapa al PDF
            document.add(subtitle);
            for (Map.Entry<DatosXLSX, Integer> entry : cantidadesSeleccionadas.entrySet()) {
                DatosXLSX producto = entry.getKey();
                Integer cantidad = entry.getValue();
                if (cantidad != null && cantidad > 0) {
                    Paragraph productDetails = new Paragraph(producto.getReferencia() + " - Cantidad: " + cantidad)
                            .setFont(regularFont)
                            .setFontSize(14);
                    document.add(productDetails);
                }
            }

            document.close();

            // Llamar al método actualizarCantidades en DatosAyudaHumanitariaOrigenLayout
            // después de generar y descargar el PDF
            getParent().ifPresent(parent -> {
                if (parent instanceof DatosAyudaHumanitariaOrigenLayout) {
                    ((DatosAyudaHumanitariaOrigenLayout) parent).actualizarCantidades(productos, cantidadesSeleccionadas);
                    ((DatosAyudaHumanitariaOrigenLayout) parent).actualizarGrid();
                }
            });

            // Crear el recurso de transmisión
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            // Crear el enlace de descarga
            com.vaadin.flow.component.html.Anchor anchor = new com.vaadin.flow.component.html.Anchor();
            anchor.setHref(new StreamResource("pedido.pdf", () -> inputStream));
            anchor.getElement().setAttribute("download", true);
            // Añadir un botón para descargar el PDF
            anchor.add(new Button("Descargar PDF"));

            // Agregar el enlace al diálogo
            dialog.add(anchor);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setSpacing(true);
    }


    // Define un evento para comunicar el pedido realizado
    public static class PedidoRealizadoEvent extends ComponentEvent<RealizarPedidoView> {
        private Map<DatosXLSX, Integer> productosSeleccionados;

        public PedidoRealizadoEvent(RealizarPedidoView source, Map<DatosXLSX, Integer> productosSeleccionados) {
            super(source, false);
            this.productosSeleccionados = productosSeleccionados;
        }

        public Map<DatosXLSX, Integer> getProductosSeleccionados() {
            return productosSeleccionados;
        }
    }

    // Método para suscribirse al evento de pedido realizado
    public Registration addPedidoRealizadoListener(ComponentEventListener<PedidoRealizadoEvent> listener) {
        return addListener(PedidoRealizadoEvent.class, listener);
    }
}