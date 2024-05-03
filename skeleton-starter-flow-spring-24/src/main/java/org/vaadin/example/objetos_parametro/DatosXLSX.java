package org.vaadin.example.objetos_parametro;

public class DatosXLSX {
    private String proveedorOrigen;
    private String referencia;
    private double cantidad;
    private int tiempoEntregaDias;
    private int cantidadSeleccionada;

    public DatosXLSX(String proveedorOrigen, String referencia, double cantidad, int tiempoEntregaDias) {
        this.proveedorOrigen = proveedorOrigen;
        this.referencia = referencia;
        this.cantidad = cantidad;
        this.tiempoEntregaDias = tiempoEntregaDias;
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    public String getProveedorOrigen() {
        return proveedorOrigen;
    }

    public void setProveedorOrigen(String proveedorOrigen) {
        this.proveedorOrigen = proveedorOrigen;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getTiempoEntregaDias() {
        return tiempoEntregaDias;
    }

    public void setTiempoEntregaDias(int tiempoEntregaDias) {
        this.tiempoEntregaDias = tiempoEntregaDias;
    }

    @Override
    public String toString() {
        return "DatosXLSX{" +
                "proveedorOrigen='" + proveedorOrigen + '\'' +
                ", referencia='" + referencia + '\'' +
                ", cantidad=" + cantidad +
                ", tiempoEntregaDias=" + tiempoEntregaDias +
                '}';
    }
}