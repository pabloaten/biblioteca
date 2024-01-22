package views.helper;

import entity.CategoriaDTO;
import entity.LibroDTO;
import entity.PrestamosDTO;
import entity.UsuarioDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import presenter.PresentadorUsuario;
import repository.CategoriaRepository;
import repository.LibroRepository;
import repository.PrestamoRepository;
import repository.UsuarioRepository;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportadorXLSX {
    public static void ExportarXLSXUsuarios() {
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("DatosHibernate");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Apellidos");
            UsuarioRepository ur = new UsuarioRepository();

            List<UsuarioDTO> data = ur.leerUsuarios();
            int rowNum = 1;
            for (UsuarioDTO entity : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getNombre());
                row.createCell(1).setCellValue(entity.getApellidos());
            }

            // Guardar el archivo Excel
            try (FileOutputStream fileOut = new FileOutputStream("ficheros/usuarios.xlsx")) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente a la carpeta ficheros");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ExportarXLSXCategorias() {
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("DatosHibernate");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Categoria");
            CategoriaRepository cr = new CategoriaRepository();

            // Recuperar datos de Hibernate y agregar al archivo Excel
            List<CategoriaDTO> data = cr.leerTodasCategorias();
            int rowNum = 1;
            for (CategoriaDTO entity : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getCategoria());
            }

            // Guardar el archivo Excel
            try (FileOutputStream fileOut = new FileOutputStream("ficheros/categorias.xlsx")) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente a la carpeta ficheros");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ExportarXLSXPrestamos() {
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("DatosHibernate");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Libro");
            headerRow.createCell(1).setCellValue("Usuario");
            PrestamoRepository pr = new PrestamoRepository();
            // Recuperar datos de Hibernate y agregar al archivo Excel
            List<PrestamosDTO> data = pr.leerPrestamos();
            int rowNum = 1;
            for (PrestamosDTO entity : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getLibroByIdLibro().getNombre());
                row.createCell(1).setCellValue(entity.getUsuarioByIdUsuario().getNombre());
            }

            // Guardar el archivo Excel
            try (FileOutputStream fileOut = new FileOutputStream("ficheros/prestamos.xlsx")) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente a la carpeta ficheros");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ExportarXLSXLibros() {
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("DatosHibernate");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Autor");
            headerRow.createCell(2).setCellValue("Editorial");
            LibroRepository lr = new LibroRepository();
            // Recuperar datos de Hibernate y agregar al archivo Excel
            List<LibroDTO> data = lr.leerLibros();
            int rowNum = 1;
            for (LibroDTO entity : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getNombre());
                row.createCell(1).setCellValue(entity.getAutor());
                row.createCell(2).setCellValue(entity.getEditorial());
            }

            // Guardar el archivo Excel
            try (FileOutputStream fileOut = new FileOutputStream("ficheros/libros.xlsx")) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente a la carpeta ficheros");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
