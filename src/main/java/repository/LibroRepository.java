package repository;

import repository.helper.LogFile;
import entity.LibroDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import singleton.HibernateUtil;

import java.util.List;
/**
 * Clase controladora para manejar operaciones relacionadas con la entidad 'Libro'.
 */
public class LibroRepository {
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();


    /**
     * Obtiene una lista de todos los libros en la base de datos.
     *
     * @return Lista de objetos LibroDTO.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public List<LibroDTO> leerLibros() throws Exception {
        List<LibroDTO> lista = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM LibroDTO";
            Query<LibroDTO> query = session.createQuery(hql, LibroDTO.class);

            lista = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception("Problemas obteniendo todos los libros");
        }
        LogFile.saveLOG("Seleccionados todos los libros. ");
        return lista;
    }

    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * @param libroDTO El objeto LibroDTO que se va a insertar.
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta(LibroDTO libroDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(libroDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas creando el libro: %s", libroDTO));
        }
        LogFile.saveLOG("Libro insertado: " + libroDTO.toString());
    }

    /**
     * Obtiene un libro de la base de datos según su ID.
     *
     * @param id El ID del libro que se va a obtener.
     * @return El objeto LibroDTO con el ID especificado.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public LibroDTO getLibro(int id) throws Exception {
        LibroDTO libroDTO = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            libroDTO = session.get(LibroDTO.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas obteniendo el libro con id: %d", id));
        }
        LogFile.saveLOG("Libro seleccionado: " + libroDTO.getNombre() + " | con id: " + libroDTO.getId());
        return libroDTO;
    }

    /**
     * Modifica un libro existente en la base de datos.
     *
     * @param libroDTO El objeto LibroDTO que se va a modificar.
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modificar(LibroDTO libroDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(libroDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas actualizando el libro: %s", libroDTO));
        }
        LogFile.saveLOG("Libro modificado: " +  libroDTO.getNombre() + " | con id: " + libroDTO.getId());
    }

    /**
     * Elimina un libro de la base de datos según su ID.
     *
     * @param id El ID del libro que se va a eliminar.
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borrar(int id) throws Exception {
        LibroDTO libroDTO = getLibro(id);
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(libroDTO);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas borrando el libro: %s", libroDTO));
        }

        LogFile.saveLOG("Libro eliminado: " + libroDTO.toString());
    }

    /**
     * Obtiene una lista de libros de la base de datos utilizando una consulta con condiciones opcionales (OR).
     *
     * @param id        El ID del libro (opcional, 0 si no se proporciona).
     * @param titulo    El título del libro (opcional, nulo o cadena vacía si no se proporciona).
     * @param autor     El autor del libro (opcional, nulo o cadena vacía si no se proporciona).
     * @param editorial La editorial del libro (opcional, nulo o cadena vacía si no se proporciona).
     * @param categoria El ID de la categoría del libro (opcional, 0 si no se proporciona).
     * @return Lista de objetos LibroDTO que cumplen con las condiciones especificadas.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public List<LibroDTO> leerLibrosOR(int id, String titulo, String autor, String editorial, int categoria) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM LibroDTO l WHERE 1=1";
            if (id != 0) {
                hql += " AND l.id = :id";
            }
            if (titulo != null && !titulo.trim().isEmpty()) {
                hql += " AND l.nombre LIKE :titulo";
            }
            if (autor != null && !autor.trim().isEmpty()) {
                hql += " AND l.autor LIKE :autor";
            }
            if (editorial != null && !editorial.trim().isEmpty()) {
                hql += " AND l.editorial LIKE :editorial";
            }
            if (categoria != 0) {
                hql += " AND l.categoria = :categoria";
            }

            org.hibernate.query.Query<LibroDTO> query = session.createQuery(hql, LibroDTO.class);
            if (id != 0) {
                query.setParameter("id", id);
            }
            if (titulo != null && !titulo.trim().isEmpty()) {
                query.setParameter("titulo", "%" + titulo + "%");
            }
            if (autor != null && !autor.trim().isEmpty()) {
                query.setParameter("autor", "%" + autor + "%");
            }
            if (editorial != null && !editorial.trim().isEmpty()) {
                query.setParameter("editorial", "%" + editorial + "%");
            }
            if (categoria != 0) {
                query.setParameter("categoria", categoria);
            }

            List<LibroDTO> lista = query.list();
            transaction.commit();
            LogFile.saveLOG("Seleccionados todos los atributos de libro.");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error leyendo los libros.", e);
        }
    }
}
