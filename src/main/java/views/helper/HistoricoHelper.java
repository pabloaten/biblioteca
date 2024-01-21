package views.helper;

import entity.HistoricoDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import singleton.HibernateUtil;

import java.sql.Timestamp;

/**
 * Clase que proporciona métodos para gestionar el historial de mensajes en la base de datos.
 */
public class HistoricoHelper {

    private static Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    /**
     * Guarda un mensaje en el historial de la base de datos.
     *
     * @param mensaje Mensaje a ser registrado en el historial.
     */
    public static void guardarMensaje( String mensaje) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             Transaction transaction = session.beginTransaction();
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            // Crear una instancia de HistoricoDTO
            HistoricoDTO historico = new HistoricoDTO();
            // Obtiene la configuración a partir de la sesión

            historico.setUser(configuration.getProperty("hibernate.connection.username"));
            historico.setFecha(new Timestamp(System.currentTimeMillis()));
            historico.setInfo(mensaje);

            // Guardar la instancia en la base de datos
            session.save(historico);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }
}
