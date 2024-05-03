package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.vaadin.example.objetos_parametro.DatosXLSX;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.DataService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Route("datos-ayuda-humanitaria-origen")
public class DatosAyudaHumanitariaOrigenLayout extends VerticalLayout {

    public static List<DatosXLSX> productos = new ArrayList<>();
    private Grid<DatosXLSX> grid;
    private TextField buscador;

    public DatosAyudaHumanitariaOrigenLayout() {
        // Configuración del diseño
        setAlignItems(Alignment.CENTER);

        // Crear la lista de datos de productos de ayuda humanitaria
        try {
            if (productos.isEmpty()){
                System.out.println("Estaba vacio");
                productos = crearListaProductos();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        // Crear el buscador
        buscador = new TextField();
        buscador.setPlaceholder("Buscar por proveedor...");
        buscador.setValueChangeMode(ValueChangeMode.EAGER);
        buscador.addValueChangeListener(event -> filtrarPorProveedor(event.getValue()));

        // Crear la cuadrícula para mostrar los datos
        grid = new Grid<>(DatosXLSX.class);
        grid.setItems(productos);

        // Configurar las columnas de la cuadrícula
        grid.setColumns("proveedorOrigen", "referencia", "cantidad", "tiempoEntregaDias");

        // Agregar filtros a las columnas
        grid.getColumnByKey("proveedorOrigen").setHeader("Proveedor Origen").setFooter("Filtrar");
        grid.getColumnByKey("referencia").setHeader("Referencia").setFooter("Filtrar");
        grid.getColumnByKey("cantidad").setHeader("Cantidad").setFooter("Filtrar");
        grid.getColumnByKey("tiempoEntregaDias").setHeader("Tiempo de Entrega (días)").setFooter("Filtrar");

        // Ajustar el tamaño de la tabla
        grid.setMaxWidth("100%");
        grid.setHeight("1200px");

        // Crear las pestañas
        Tabs tabs = new Tabs();
        Tab tab1 = new Tab("Lista de Productos");
        Tab tab2 = new Tab("Realizar Pedido");

        // Agregar las pestañas al layout
        tabs.add(tab1, tab2);
        add(tabs);

        // Agregar el buscador y la cuadrícula al diseño
        add(buscador, grid);

        // Listener para manejar el cambio de pestañas
        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab() == tab1) {
                UI.getCurrent().navigate("datos-ayuda-humanitaria-origen");
            } else if (event.getSelectedTab() == tab2) {
                UI.getCurrent().navigate("realizar-pedido");
            }
        });
    }

    private void filtrarPorProveedor(String proveedor) {
        List<DatosXLSX> listaFiltrada = productos.stream()
                .filter(producto -> producto.getProveedorOrigen().toLowerCase().contains(proveedor.toLowerCase()))
                .collect(Collectors.toList());
        grid.setItems(listaFiltrada);
    }

    // Método para actualizar la cuadrícula con los nuevos datos
    public void actualizarGrid() {
        grid.setItems(productos);
    }

    // Método para actualizar las cantidades en base a los pedidos realizados
    public void actualizarCantidades(List<DatosXLSX> listaCompletaProductos, Map<DatosXLSX, Integer> cantidadesSeleccionadas) {
        try {
            // Actualizar las cantidades en la lista completa de productos
            for (DatosXLSX producto : listaCompletaProductos) {
                Integer cantidadSeleccionada = cantidadesSeleccionadas.getOrDefault(producto, 0);
                int cantidadDisponible = (int) producto.getCantidad();
                producto.setCantidad(cantidadDisponible - cantidadSeleccionada);
            }
            // Actualizar la lista de productos
            productos = listaCompletaProductos;

            // Actualizar la cuadrícula con las nuevas cantidades
            actualizarGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear una lista de datos de productos de ayuda humanitaria de ejemplo
    private List<DatosXLSX> crearListaProductos() throws IOException, URISyntaxException {
        DataService dataService = new DataService();
        return dataService.ObtenerDatosXLSX();
    }
}


