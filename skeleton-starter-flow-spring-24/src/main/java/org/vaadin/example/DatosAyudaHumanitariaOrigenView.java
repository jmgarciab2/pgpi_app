package org.vaadin.example;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import java.util.ArrayList;
import java.util.List;

@Route("datos-ayuda-humanitaria-origen")
@PWA(name = "Productos de Ayuda Humanitaria por Origen", shortName = "Productos de Ayuda Humanitaria")
@Theme(value = Lumo.class, variant = Lumo.DARK)

public class DatosAyudaHumanitariaOrigenView extends VerticalLayout {

    public DatosAyudaHumanitariaOrigenView() {
        // Configuración del diseño
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        // Crear la lista de datos de productos de ayuda humanitaria
        List<ProductoAyudaHumanitaria> productos = crearListaProductos();

        // Crear la cuadrícula para mostrar los datos
        Grid<ProductoAyudaHumanitaria> grid = new Grid<>(ProductoAyudaHumanitaria.class);
        grid.setItems(productos);

        // Configurar las columnas de la cuadrícula
        grid.setColumns("origen", "tipo", "descripcion", "cantidad");

        // Agregar la cuadrícula al diseño
        add(grid);
    }

    // Método para crear una lista de datos de productos de ayuda humanitaria de ejemplo
    private List<ProductoAyudaHumanitaria> crearListaProductos() {
        List<ProductoAyudaHumanitaria> productos = new ArrayList<>();
        productos.add(new ProductoAyudaHumanitaria("Origen1", "Tipo1", "Descripción1", 10));
        productos.add(new ProductoAyudaHumanitaria("Origen2", "Tipo2", "Descripción2", 20));
        productos.add(new ProductoAyudaHumanitaria("Origen3", "Tipo3", "Descripción3", 30));
        // Agregar más productos según sea necesario
        return productos;
    }


}
