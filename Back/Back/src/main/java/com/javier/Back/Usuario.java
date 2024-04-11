package com.javier.Back;

import com.javier.Back.Pedido;

import java.util.ArrayList;
import java.util.Date;

public class Usuario {
    private String usuario;
    private String contraseña;
    private String email;
    private Date fechaNacimiento;
    private ArrayList<Pedido> pedidos;

    // Constructor
    public Usuario(String usuario, String contraseña, String email, Date fechaNacimiento) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.pedidos = new ArrayList<>();
    }

    // Getters y setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
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
