package com.javier.Back;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GestionXLSX {
    public static ArrayList<DatosXLSX> leerDatosDesdeXLSX(String filePath) {
        ArrayList<DatosXLSX> listaDatos = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Empezamos desde la fila 1 para evitar la fila de encabezados
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String proveedorOrigen = row.getCell(0).getStringCellValue();
                    String referencia = row.getCell(1).getStringCellValue();
                    double cantidad = row.getCell(2).getNumericCellValue();
                    int tiempoEntregaDias = (int) row.getCell(3).getNumericCellValue();

                    DatosXLSX datos = new DatosXLSX(proveedorOrigen, referencia, cantidad, tiempoEntregaDias);
                    listaDatos.add(datos);
                }
            }

            inputStream.close();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }
}
