package repository;

import repository.helper.LogFile;
import entity.UsuarioDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import singleton.HibernateUtil;
import views.helper.HistoricoHelper;

import java.util.List;
/**
 * Clase controladora para manejar operaciones relacionadas con la entidad 'Usuario'.
 */
public class UsuarioRepository {
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    /**
     * Obtiene una lista de todos los usuarios en la base de datos.
     *
     * @return Lista de objetos UsuarioDTO.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public List<UsuarioDTO> leerUsuarios() throws Exception {
        List<UsuarioDTO> lista = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM UsuarioDTO";
            Query<UsuarioDTO> query = session.createQuery(hql, UsuarioDTO.class);

            lista = query.getResultList();

            transaction.commit();
            HistoricoHelper.guardarMensaje("OBTENIENDO LOS USUARIOS");
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception("Problemas obteniendo todas las categorías");
        }
        LogFile.saveLOG("Seleccionados todos los usuarios.");
        return lista;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuarioDTO El objeto UsuarioDTO que se va a insertar.
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta(UsuarioDTO usuarioDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(usuarioDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas creando la categoria: %s", usuarioDTO));
        }
        LogFile.saveLOG("Usuario insertado: " + usuarioDTO.toString());
    }

    /**
     * Obtiene un usuario de la base de datos según su ID.
     *
     * @param id El ID del usuario que se va a obtener.
     * @return El objeto UsuarioDTO con el ID especificado.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public UsuarioDTO getUsuario(int id) throws Exception {
        UsuarioDTO usuarioDTO = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            usuarioDTO = session.get(UsuarioDTO.class, id);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas obteniendo el departamento con id: %d", id));
        }
        LogFile.saveLOG("Usuario seleccionado: " + usuarioDTO.getNombre() + " " + usuarioDTO.getApellidos() + " | con id: " + usuarioDTO.getId());
        return usuarioDTO;
    }

    /**
     * Modifica un usuario existente en la base de datos.
     *
     * @param usuarioDTO El objeto UsuarioDTO que se va a modificar.
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modificar(UsuarioDTO usuarioDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(usuarioDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas actualizando la Categoria: %s", usuarioDTO));
        }
        LogFile.saveLOG("Usuario modificado: " +  usuarioDTO.getNombre() + " " + usuarioDTO.getApellidos() + " | con id: " + usuarioDTO.getId());
    }

    /**
     * Elimina un usuario de la base de datos según su ID.
     *
     * @param id El ID del usuario que se va a eliminar.
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borrar(int id) throws Exception {
        UsuarioDTO usuarioDTO = getUsuario(id);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(usuarioDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas borrando la categoria: %s", usuarioDTO));
        }
        LogFile.saveLOG("Usuario eliminado: " + usuarioDTO.toString());
    }

    /**
     * Obtiene una lista de usuarios con filtrado opcional por ID, nombre y apellidos.
     *
     * @param id         ID del usuario (0 si no se desea filtrar por ID).
     * @param nombre     Nombre del usuario (puede ser nulo o vacío si no se desea filtrar por nombre).
     * @param apellidos  Apellidos del usuario (puede ser nulo o vacío si no se desea filtrar por apellidos).
     * @return Lista de objetos UsuarioDTO que cumplen con los criterios de filtrado.
     * @throws Exception Si hay problemas durante el proceso de obtención.
     */
    public List<UsuarioDTO> leerUsuariosOR(int id, String nombre, String apellidos) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM UsuarioDTO u WHERE 1=1";
            if (id != 0) {
                hql += " AND u.id = :id";
            }
            if (nombre != null && !nombre.trim().isEmpty()) {
                hql += " AND u.nombre LIKE :nombre";
            }
            if (apellidos != null && !apellidos.trim().isEmpty()) {
                hql += " AND u.apellidos LIKE :apellidos";
            }

            org.hibernate.query.Query<UsuarioDTO> query = session.createQuery(hql, UsuarioDTO.class);
            if (id != 0) {
                query.setParameter("id", id);
            }
            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (apellidos != null && !apellidos.trim().isEmpty()) {
                query.setParameter("apellidos", "%" + apellidos + "%");
            }

            List<UsuarioDTO> lista = query.list();
            transaction.commit();
            LogFile.saveLOG("Seleccionados todos los atributos de usuario.");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error leyendo los usuarios.", e);
        }
    }
}
