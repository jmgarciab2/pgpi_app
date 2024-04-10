package com.javier.Back;

import java.util.ArrayList;

public class DatosXLSX {
    private ArrayList<String> valores;

    public DatosXLSX() {
        this.valores = new ArrayList<>();
    }

    public ArrayList<String> getValores() {
        return valores;
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }

    @Override
    public String toString() {
        return "DatosXLSX{" +
                "valores=" + valores +
                '}';
    }
}
