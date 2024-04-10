package org.vaadin.example;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


//Se desarrollarán las dos pestañas requeridas
@Route("principal")
public class Principal extends VerticalLayout {

    public void pagPrincipal(){
        //Objetos comunes
        ArrayList<DatosJSON> listaDatos = new ArrayList<>();
        ArrayList<DatosJSON> listaDatosPest2 = new ArrayList<>();
        //Objeto a cambiar de DatosJSON
        DatosJSON datito = new DatosJSON();
        //Layouts pestaña 1

        Dialog dialog = new Dialog();
        dialog.setHeight("800");
        dialog.setWidth("300");
        dialog.getElement().setAttribute("aria-label", "Mostrar/editar Datos");
        Dialog dialog1 = new Dialog();

        H1 tit = new H1("Gestión de Datos");
        H3 tituloGrid = new H3("Lista de Datos");
        //Pestañas
        Tab datosGenerales = new Tab("Datos generales");
        datosGenerales.setId("datosGenerales");
        Tab datosAgrupados = new Tab("Datos agrupados por MsCode");
        datosAgrupados.setId("datosAgrupados");
        Tabs tabs = new Tabs(datosGenerales,datosAgrupados);

        tabs.addThemeVariants(TabsVariant.LUMO_HIDE_SCROLL_BUTTONS);
        //PESTAÑA 1

        //Layouts del dialog
        VerticalLayout vl1 = new VerticalLayout();
        VerticalLayout vl2 = new VerticalLayout();
        HorizontalLayout hl1 = new HorizontalLayout();
        VerticalLayout vlDialog = new VerticalLayout();

        //Layouts del dialog 2
        VerticalLayout vl12 = new VerticalLayout();
        VerticalLayout vl22 = new VerticalLayout();
        HorizontalLayout hl12 = new HorizontalLayout();
        VerticalLayout vl2Dialog = new VerticalLayout();

        //Campos del menu dialog
        TextField txtMs = new TextField();
        txtMs.setLabel("MsCode");
        TextField txtYear = new TextField();
        txtYear.setLabel("Year");
        TextField txtEst = new TextField();
        txtEst.setLabel("EstCode");
        TextField txtEstim = new TextField();
        txtEstim.setLabel("Estimate");
        TextField txtSE = new TextField();
        txtSE.setLabel("SE");
        TextField txtLow = new TextField();
        txtLow.setLabel("Lower CIB");
        TextField txtUp = new TextField();
        txtUp.setLabel("Upper CIB");
        TextField txtFlag = new TextField();
        txtFlag.setLabel("Flag");

        Button btnAceptar = new Button("Aceptar");
        Button btnCancelar = new Button("Cancelar", e -> dialog.close());
        Button btnEliminar = new Button("Eliminar");
        //Campos del menu dialog 2
        ComboBox comboMsC = new ComboBox<>("MsCode");
        comboMsC.setItems("MEASA","MEASB","MEASC","MEASD","MEASE","MEASF","MEASG","MEASH","MEASI","MEASJ");
        comboMsC.setPlaceholder("Elija una opción...");
        comboMsC.setAllowCustomValue(false);
        TextField txtYearNew = new TextField();
        txtYearNew.setLabel("Year");
        ComboBox comboEst = new ComboBox<>("EstCode");
        comboEst.setItems("_ChangeInNumber","_Population","_Number","_ChangeInProportion","_Proportion");
        comboEst.setPlaceholder("Elija una opción...");
        comboEst.setAllowCustomValue(false);
        TextField txtEstimNew = new TextField();
        txtEstimNew.setLabel("Estimate");
        TextField txtSENew = new TextField();
        txtSENew.setLabel("SE");
        TextField txtLowNew = new TextField();
        txtLowNew.setLabel("Lower CIB");
        TextField txtUpNew = new TextField();
        txtUpNew.setLabel("Upper CIB");
        TextField txtFlagNew = new TextField();
        txtFlagNew.setLabel("Flag");

        //Setear los campos como requeridos
        comboMsC.setRequiredIndicatorVisible(true);
        txtYearNew.setRequiredIndicatorVisible(true);
        comboEst.setRequiredIndicatorVisible(true);
        txtEstimNew.setRequiredIndicatorVisible(true);
        txtSENew.setRequiredIndicatorVisible(true);
        txtLowNew.setRequiredIndicatorVisible(true);
        txtUpNew.setRequiredIndicatorVisible(true);
        txtFlagNew.setRequiredIndicatorVisible(true);

        Button btnAceptarNew = new Button("Añadir");
        Button btnCancelarNew = new Button("Cancelar", e -> dialog1.close());
        Button btnAdd = new Button("Nuevo", e -> dialog1.open());
        //Personalización
        btnAceptar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        //Botones dialogo 2
        btnAceptarNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        btnCancelarNew.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //Añadimos elementos del dialog
        vl1.add(txtMs,txtYear,txtEst,txtEstim);
        vl2.add(txtSE,txtLow,txtUp,txtFlag);
        hl1.add(vl1,vl2);
        hl1.setAlignItems(Alignment.CENTER);
        dialog.getFooter().add(btnAceptar);
        dialog.getFooter().add(btnCancelar);
        dialog.getFooter().add(btnEliminar);
        vlDialog.setAlignItems(Alignment.CENTER);
        dialog.setHeaderTitle("Datos");
        vlDialog.add(hl1);
        dialog.add(vlDialog);

        //Añadimos elementos del dialog
        vl12.add(comboMsC,txtYearNew,comboEst,txtEstimNew);
        vl22.add(txtSENew,txtLowNew,txtUpNew,txtFlagNew);
        hl12.add(vl12,vl22);
        hl12.setAlignItems(Alignment.CENTER);
        vl2Dialog.setAlignItems(Alignment.CENTER);
        dialog1.getFooter().add(btnAceptarNew);
        dialog1.getFooter().add(btnCancelarNew);
        dialog1.setHeaderTitle("Añadir datos");
        vl2Dialog.add(hl12);
        dialog1.add(vl2Dialog);

        //PESTAÑA 2
        VerticalLayout vlPest2 = new VerticalLayout();
        H3 tituloPest2 = new H3("Lista de datos agrupados por MsCode");
        ComboBox comboPest2 = new ComboBox<>("MsCode");
        comboPest2.setItems("MEASA","MEASB","MEASC","MEASE","MEASF","MEASG","MEASH","MEASI","MEASJ");
        comboPest2.setPlaceholder("Elija una opción...");
        comboPest2.setAllowCustomValue(false);
        //Tabla Grid de la pestaña 2 que contendrá la lista de Datos solicitados al Back
        // Generar la tabla con los campos
        Grid<DatosJSON> gridPest2 = new Grid<>(DatosJSON.class, false);
        gridPest2.addColumn(DatosJSON::getMsCode).setHeader("MsCode");
        gridPest2.addColumn(DatosJSON::getYear).setHeader("Year");
        gridPest2.addColumn(DatosJSON::getEstCode).setHeader("EstCode");
        gridPest2.addColumn(DatosJSON::getEstimate).setHeader("Estimate");
        gridPest2.addColumn(DatosJSON::getSe).setHeader("SE");
        gridPest2.addColumn(DatosJSON::getLowerCIB).setHeader("Lower CIB");
        gridPest2.addColumn(DatosJSON::getUpperCIB).setHeader("Upper CIB");
        gridPest2.addColumn(DatosJSON::getFlag).setHeader("Flag");
        //Rellenno el arraylist, con los datos recibidos
        try {
            listaDatosPest2 = DataService.obtenerListaDatos();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gridPest2.setItems(listaDatosPest2);
        vlPest2.add(tituloPest2,comboPest2,gridPest2);
        vlPest2.setAlignItems(Alignment.CENTER);
        //Lo predefinimos como invisible porque no se puede ver hasta que pulses la pestaña correspondiente
        vlPest2.setVisible(false);
//----------------------------------------------------------------------------------------------------------------------
        //Tabla Grid que contendrá la lista de Datos solicitados al Back
        // Generar la tabla con los campos arriba puestos.
        Grid<DatosJSON> grid = new Grid<>(DatosJSON.class, false);
        grid.addColumn(DatosJSON::getMsCode).setHeader("MsCode");
        grid.addColumn(DatosJSON::getYear).setHeader("Year");
        grid.addColumn(DatosJSON::getEstCode).setHeader("EstCode");
        grid.addColumn(DatosJSON::getEstimate).setHeader("Estimate");
        grid.addColumn(DatosJSON::getSe).setHeader("SE");
        grid.addColumn(DatosJSON::getLowerCIB).setHeader("Lower CIB");
        grid.addColumn(DatosJSON::getUpperCIB).setHeader("Upper CIB");
        grid.addColumn(DatosJSON::getFlag).setHeader("Flag");

        //Rellenno el arraylist, con los datos recibidos
        try {
            listaDatos = DataService.obtenerListaDatos();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        grid.setItems(listaDatos);

        //Para hacer el PUT
        grid.addItemDoubleClickListener(new ComponentEventListener<ItemDoubleClickEvent<DatosJSON>>() {
            @Override
            public void onComponentEvent(ItemDoubleClickEvent<DatosJSON> event) {
                ArrayList <DatosJSON> listaaux = new ArrayList<>();
                try {
                    listaaux = DataService.obtenerListaDatos();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                datito.set_id(event.getItem().get_id());
                datito.setMsCode(event.getItem().getMsCode());
                datito.setYear(event.getItem().getYear());
                datito.setEstCode(event.getItem().getEstCode());
                datito.setEstimate(event.getItem().getEstimate());
                datito.setSe(event.getItem().getSe());
                datito.setLowerCIB(event.getItem().getLowerCIB());
                datito.setUpperCIB(event.getItem().getUpperCIB());
                datito.setFlag(event.getItem().getFlag());

            }
        });

        grid.addItemDoubleClickListener(event -> dialog.open());

        //Para que al abrir un objeto se rellenen los campos
        grid.addItemDoubleClickListener(new ComponentEventListener<ItemDoubleClickEvent<DatosJSON>>() {
            @Override
            public void onComponentEvent(ItemDoubleClickEvent<DatosJSON> jefeEstablecimientoItemDoubleClickEvent) {
                txtMs.setValue(datito.getMsCode());
                txtYear.setValue(datito.getYear());
                txtEst.setValue(datito.getEstCode());
                txtEstim.setValue(String.valueOf(datito.getEstimate()));
                txtSE.setValue(String.valueOf(datito.getSe()));
                txtLow.setValue(String.valueOf(datito.getLowerCIB()));
                txtUp.setValue(String.valueOf(datito.getUpperCIB()));
                txtFlag.setValue(datito.getFlag());

            }
        });

        btnAceptar.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                ArrayList<DatosJSON> listaDatossaux = new ArrayList<>();
                ArrayList<DatosJSON> listaDatosActualizados = new ArrayList<>();
                //Objeto modificado
                DatosJSON nuevo_dato = new DatosJSON();
                nuevo_dato.set_id(datito.get_id());//Será el mismo, ya que estamos modificando el dato en sí
                nuevo_dato.setMsCode(txtMs.getValue());
                nuevo_dato.setYear(txtYear.getValue());
                nuevo_dato.setEstCode(txtEst.getValue());
                nuevo_dato.setEstimate(Float.parseFloat(txtEstim.getValue()));
                nuevo_dato.setSe(Float.parseFloat(txtSE.getValue()));
                nuevo_dato.setLowerCIB(Float.parseFloat(txtLow.getValue()));
                nuevo_dato.setUpperCIB(Float.parseFloat(txtUp.getValue()));
                nuevo_dato.setFlag(txtFlag.getValue());

                if(datito.toString().equals(nuevo_dato.toString())){
                    System.out.printf("Es el mismo objeto");
                }
                else{
                    listaDatossaux.add(datito);
                    listaDatossaux.add(nuevo_dato);
                    try {
                        listaDatosActualizados = DataService.actualizarDatos(listaDatossaux);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    grid.setItems(listaDatosActualizados);
                    dialog.close();

                }

            }
        });

        //Metodo del boton eliminar del dialog
        btnEliminar.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                //Lista auxiliar que recibirá la actualizada
                ArrayList<DatosJSON> listaEstablecimientosaux = new ArrayList<>();
                //Llamamos al método y le pasamos el id para que lo busque el Back en el fichero y lo elimine
                listaEstablecimientosaux = DataService.eliminarDato(datito.get_id());
                //Seteamos la nueva lista actualizada
                grid.setItems(listaEstablecimientosaux);
                dialog.close();
            }
        });

        //Método del boton añadir del dialog de nuevo Dato
        btnAceptarNew.addClickListener( event -> {
            //Recogeremos los datos del formulario, crearemos un nuevo objeto y lo pasaremos al metodo back
            DatosJSON newDato = new DatosJSON(UUID.randomUUID(), String.valueOf(comboMsC.getValue()), txtYearNew.getValue(), String.valueOf(comboEst.getValue()), Float.parseFloat(txtEstimNew.getValue()), Float.parseFloat(txtSENew.getValue()), Float.parseFloat(txtLowNew.getValue()), Float.parseFloat(txtUpNew.getValue()), txtFlagNew.getValue());
            if(DataService.addDato(newDato)){
                System.out.println("Dato añadido correctamente.");
                //Ahora llamaremos al método de leer el json para actualizar la lista
                try {
                    grid.setItems(DataService.obtenerListaDatos());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dialog1.close();
            } else {
                System.out.println("Ocurrio algun error al hacer la peticion al Back.");
            }

        });

        //Se ejecuta cada vez que seleccionas una sección diferente
        tabs.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {
            //SI ESTÁS EN LA PESTAÑA DE DATOS GENERALES
            @Override
            public void onComponentEvent(Tabs.SelectedChangeEvent event) {

                if(event.getSelectedTab().getId().toString().equals("Optional[datosGenerales]")){
                    //Debemos hacer visibles todos los objetos de la primera pestaña y no visibles los de la segundo
                    vlPest2.setVisible(false);
                    //Hacemos visibles los objetos de la primera pestaña
                    tituloGrid.setVisible(true);
                    grid.setVisible(true);
                    btnAdd.setVisible(true);

                }
                else{ //SI ESTÁS EN LA PESTAÑA DE DATOS AGRUPADOS
                    //Debemos hacer lo contrario
                    tituloGrid.setVisible(false);
                    grid.setVisible(false);
                    btnAdd.setVisible(false);

                    //Hacemos visibles los objetos de la segunda pestaña
                    vlPest2.setVisible(true);
                }
            }
        });

        //Método para el cambio del ComboBOX de la PESTAÑA 2
        // Listener para el ComboBox
        comboPest2.addValueChangeListener(event -> {
            // Obtener el valor seleccionado en el ComboBox
            String MsCodeSeleccionado = String.valueOf(event.getValue());

            // Llamar al servicio para obtener la lista de datos según el MsCode seleccionado
            ArrayList<DatosJSON> listaMsCode = null;
            try {
                listaMsCode = DataService.obtenerListaDatosPorMsCode(MsCodeSeleccionado);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            // Actualizar el contenido del Grid con la nueva lista de datos
            gridPest2.setItems(listaMsCode);
        });

        //Añadimos todo a la pagina principal
        this.setAlignItems(Alignment.CENTER);
        this.setHeightFull();
        this.add(tit,tabs,tituloGrid,grid,btnAdd,vlPest2);

    }


}
