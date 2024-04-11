package com.javier.Back;

import java.util.ArrayList;

public class Usuario{
    private String id;
    private String contrasena;
    private String email;
    private String fechaNacimiento;
    private ArrayList<Pedido> pedidos;

    // Constructor
    public Usuario(){

    }
    public Usuario(String id, String contrasena, String email, String fechaNacimiento, ArrayList<Pedido> pedidos) {
        this.id = id;
        this.contrasena = contrasena;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.pedidos = pedidos;
    }


    // Getters y setters
    public String getUsuario() {
        return id;
    }

    public void setUsuario(String id) {
        this.id = id;
    }

    public String getContraseña() {
        return contrasena;
    }

    public void setContraseña(String contraseña) {
        this.contrasena = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    // Método para agregar un pedido a la lista de pedidos
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }
}
