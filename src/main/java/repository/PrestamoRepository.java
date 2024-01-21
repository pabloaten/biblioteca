package repository;

import repository.helper.LogFile;
import entity.PrestamosDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import singleton.HibernateUtil;
import views.helper.HistoricoHelper;

import java.util.List;
/**
 * Clase controladora para manejar operaciones relacionadas con la entidad 'Prestamo'.
 */
public class PrestamoRepository {
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    /**
     * Obtiene una lista de todos los préstamos en la base de datos.
     *
     * @return Lista de objetos PrestamosDTO.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public List<PrestamosDTO> leerPrestamos() throws Exception {
        List<PrestamosDTO> lista = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM PrestamosDTO";
            Query<PrestamosDTO> query = session.createQuery(hql, PrestamosDTO.class);

            lista = query.getResultList();

            transaction.commit();
            HistoricoHelper.guardarMensaje("OBTENIENDO LOS PRÉSTAMOS");
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception("Problemas obteniendo los préstamos");
        }
        LogFile.saveLOG("Lista de prestamos: ");
        for (PrestamosDTO prestamo : lista) {
            LogFile.saveLOG("  -" + prestamo.toString());
        }
        return lista;
    }

    /**
     * Inserta un nuevo préstamo en la base de datos.
     *
     * @param prestamosDTO El objeto PrestamosDTO que se va a insertar.
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta(PrestamosDTO prestamosDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(prestamosDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas creando el préstamo: %s", prestamosDTO));
        }
        LogFile.saveLOG("Prestamo insertado: " + prestamosDTO.toString());
    }

    /**
     * Modifica un préstamo existente en la base de datos.
     *
     * @param prestamosDTO El objeto PrestamosDTO que se va a modificar.
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modificar(PrestamosDTO prestamosDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(prestamosDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas actualizando el Préstamo: %s", prestamosDTO));
        }
        LogFile.saveLOG("Prestamo modificado: " +  prestamosDTO.toString());
    }

    /**
     * Obtiene un préstamo de la base de datos según su ID.
     *
     * @param id El ID del préstamo que se va a obtener.
     * @return El objeto PrestamosDTO con el ID especificado.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public PrestamosDTO getPrestamo(int id) throws Exception {
        PrestamosDTO prestamosDTO = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            prestamosDTO = session.get(PrestamosDTO.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas obteniendo el préstamo con id: %d", id));
        }
        LogFile.saveLOG("Prestamo seleccionado: " + prestamosDTO.toString());
        return prestamosDTO;
    }

    /**
     * Elimina un préstamo de la base de datos según su ID.
     *
     * @param id El ID del préstamo que se va a eliminar.
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borrar(int id) throws Exception {
        PrestamosDTO prestamosDTO = getPrestamo(id);
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(prestamosDTO);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas borrando el préstamo: %s", prestamosDTO));
        }
        LogFile.saveLOG("Prestamo eliminado: " + prestamosDTO.toString());
    }
}
