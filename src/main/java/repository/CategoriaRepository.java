package repository;

import repository.helper.LogFile;
import entity.CategoriaDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import singleton.HibernateUtil;

import java.util.List;
/**
 * Clase controladora para manejar operaciones relacionadas con la entidad 'Categoria'.
 */
public class CategoriaRepository {

    /**private static final String sqlINSERT="INSERT INTO categoria (categoria) VALUES (?)";
    private static final String sqlUPDATE="UPDATE categoria SET categoria=? WHERE id = ?";
    private static final String sqlDELETE="DELETE FROM categoria WHERE id = ?";**/

    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    /**
     * Inserta una nueva categoría en la base de datos.
     *
     * @param categoriaDTO El objeto CategoriaDTO que se va a insertar.
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta(CategoriaDTO categoriaDTO) throws Exception {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(categoriaDTO);
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas creando la categoría: %s", categoriaDTO));
        }
        LogFile.saveLOG("Categoria insertada: " + categoriaDTO.toString());
    }

    /**
     * Modifica una categoría existente en la base de datos.
     *
     * @param categoriaDTO El objeto CategoriaDTO que se va a modificar.
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modificar(CategoriaDTO categoriaDTO) throws Exception {
        try {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(categoriaDTO);
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas actualizando la categoría: %s", categoriaDTO));
        }
        LogFile.saveLOG("Categoria modificada: " +  categoriaDTO.getCategoria() + " | con id: " + categoriaDTO.getId());
    }

    /**
     * Elimina una categoría de la base de datos según su ID.
     *
     * @param id El ID de la categoría que se va a eliminar.
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borrar(int id) throws Exception {
        CategoriaDTO categoriaDTO = getCategoria(id);
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(categoriaDTO);
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas borrando la categoría: %s", categoriaDTO));
        }
        LogFile.saveLOG("Categoria eliminada: " + categoriaDTO.toString());
    }

    /**
     * Obtiene una categoría de la base de datos según su ID.
     *
     * @param id El ID de la categoría que se va a obtener.
     * @return El objeto CategoriaDTO con el ID especificado.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public CategoriaDTO getCategoria(int id) throws Exception {
        CategoriaDTO categoriaDTO = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            categoriaDTO = session.get(CategoriaDTO.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas obteniendo la categoría con id: %d", id));
        }
        LogFile.saveLOG("Categoria seleccionada: " + categoriaDTO.getCategoria() + " | con id: " + categoriaDTO.getId());

        return categoriaDTO;
    }

    /**
     * Obtiene todas las categorías de la base de datos.
     *
     * @return Lista de objetos CategoriaDTO.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public List<CategoriaDTO> leerTodasCategorias() throws Exception {
        List<CategoriaDTO> lista = null;
        try {
            Transaction transaction = session.beginTransaction();
            String hql = "FROM CategoriaDTO";
            Query<CategoriaDTO> query = session.createQuery(hql, CategoriaDTO.class);
            lista = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception("Problemas obteniendo todas las categorías");
        }
        LogFile.saveLOG("Seleccionadas todas las categorias. ");
        return lista;
    }
}
