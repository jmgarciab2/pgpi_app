package org.vaadin.example.views;

import org.vaadin.example.objetos_parametro.DatosXLSX;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.DataService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Route("datos-ayuda-humanitaria-origen")

public class DatosAyudaHumanitariaOrigenLayout extends VerticalLayout {

    public DatosAyudaHumanitariaOrigenLayout() throws IOException, URISyntaxException {
        // Configuración del diseño
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        H1 titulo = new H1("Mostrando Proveedores de Ayuda Humanitaria");
        add(titulo);
        // Crear la lista de datos de productos de ayuda humanitaria
        List<DatosXLSX> productos = crearListaProductos();

        // Crear la cuadrícula para mostrar los datos
        Grid<DatosXLSX> grid = new Grid<>(DatosXLSX.class);
        grid.setItems(productos);

        // Configurar las columnas de la cuadrícula
        grid.setColumns("proveedorOrigen", "referencia", "cantidad", "tiempoEntregaDias");

        // Agregar la cuadrícula al diseño
        add(grid);
    }

    // Método para crear una lista de datos de productos de ayuda humanitaria de ejemplo
    private List<DatosXLSX> crearListaProductos() throws IOException, URISyntaxException {
        List<DatosXLSX> productos = new ArrayList<>();
        DataService dataService = new DataService();
        productos = dataService.ObtenerDatosXLSX();

        // Agregar más productos según sea necesario
        return productos;
    }
}
