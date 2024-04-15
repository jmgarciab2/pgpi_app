package org.vaadin.example;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private String nombreONG;
    private ArrayList<String> nombresProveedor;
    private String direcciones;
    private ArrayList<Double> cantidades;
    private ArrayList<String> productos;

    public Pedido(int id, String nombreONG, ArrayList<String> nombresProveedor, String direcciones, ArrayList<Double> cantidades, ArrayList<String> productos) {
        this.id = id;
        this.nombreONG = nombreONG;
        this.nombresProveedor = nombresProveedor;
        this.direcciones = direcciones;
        this.cantidades = cantidades;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreONG() {
        return nombreONG;
    }

    public void setNombreONG(String nombreONG) {
        this.nombreONG = nombreONG;
    }

    public ArrayList<String> getNombresProveedor() {
        return nombresProveedor;
    }

    public void setNombresProveedor(ArrayList<String> nombresProveedor) {
        this.nombresProveedor = nombresProveedor;
    }

    public String getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(String direcciones) {
        this.direcciones = direcciones;
    }

    public ArrayList<Double> getCantidades() {
        return cantidades;
    }

    public void setCantidades(ArrayList<Double> cantidades) {
        this.cantidades = cantidades;
    }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", nombreONG='" + nombreONG + '\'' +
                ", nombresProveedor=" + nombresProveedor +
                ", direcciones=" + direcciones +
                ", cantidades=" + cantidades +
                ", productos=" + productos +
                '}';
    }
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
