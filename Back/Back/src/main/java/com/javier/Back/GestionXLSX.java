package com.javier.Back;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.util.ArrayList;

public class GestionXLSX {
    public static ArrayList<DatosXLSX> leerDatosDesdeXLSX(String filePath) {
        ArrayList<DatosXLSX> listaDatos = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                DatosXLSX datos = new DatosXLSX();
                ArrayList<String> valoresFila = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            valoresFila.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            valoresFila.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            valoresFila.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case BLANK:
                            valoresFila.add("");
                            break;
                        default:
                            valoresFila.add("");
                            break;
                    }
                }
                // Asumiendo que DatosXLSX tiene un constructor que acepta una lista de valores
                datos.setValores(valoresFila);
                listaDatos.add(datos);
            }

            inputStream.close();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }
}

