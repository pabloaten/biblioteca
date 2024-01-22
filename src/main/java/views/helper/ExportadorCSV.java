package views.helper;

import entity.CategoriaDTO;
import entity.LibroDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import singleton.HibernateUtil;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * Clase que proporciona métodos para exportar datos a un archivo CSV.
 */
public class ExportadorCSV {

    private static final String RUTA_ARCHIVO_CSV = "ficheros/fichero.csv";

    /**
     * Exporta los datos de libros a un archivo CSV.
     */
    public static void exportarLibrosCSV() {
        Transaction transaction = null;
        Session session = null;
        FileWriter csvWriter = null;

        try {

            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();


            String hql = "FROM LibroDTO";

            Query<LibroDTO> query = session.createQuery(hql, LibroDTO.class);

            List<LibroDTO> libros = query.getResultList();

            // Abre el archivo CSV para escritura
            csvWriter = new FileWriter(RUTA_ARCHIVO_CSV);

            // Escribe el encabezado del CSV (nombres de columnas)
            csvWriter.append("Id,Autor,Nombre,Editorial,Categoria");
            csvWriter.append("\n");

            // Escribe los datos de los libros en el archivo CSV
            for (LibroDTO libro : libros) {
                csvWriter.append(libro.getId()+"").append(",");
                csvWriter.append(libro.getAutor()).append(",");
                csvWriter.append(libro.getNombre()).append(",");
                csvWriter.append(libro.getEditorial()).append(",");
                csvWriter.append(libro.getCategoria()+"").append(",");

                csvWriter.append("\n");
            }

            JOptionPane.showMessageDialog(null, "Datos de libros exportados exitosamente a " + RUTA_ARCHIVO_CSV,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }
            if (csvWriter != null) {
                try {
                    csvWriter.flush();
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
