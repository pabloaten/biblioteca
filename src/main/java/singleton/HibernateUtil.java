package singleton;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

import java.util.logging.Level;
/**
 * Clase de utilidad para la gestión de sesiones de Hibernate.
 */
public class HibernateUtil {
    /**
     * Constructor privado para evitar la instanciación de la clase.
     */
    private HibernateUtil() {

    }

    private static SessionFactory sessionFactory=null;

    /**
     * Obtiene la fábrica de sesiones de Hibernate.
     *
     * @return Fábrica de sesiones de Hibernate.
     */
    public static SessionFactory getSessionFactory() {
        return getSessionFactory(false);
    }
    /**
     * Obtiene la fábrica de sesiones de Hibernate con opción para mostrar registros de log.
     *
     * @param showLog Indica si se deben mostrar registros de log.
     * @return Fábrica de sesiones de Hibernate.
     */
    public static SessionFactory getSessionFactory(boolean showLog) {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory(showLog);
            Runtime.getRuntime().addShutdownHook(new ThreadOff());
        }
        return sessionFactory;
    }
    private static void showLog(boolean showLog) {


    }
    /**
     * Construye la fábrica de sesiones de Hibernate.
     *
     * @param show Indica si se deben mostrar registros de log.
     * @return Fábrica de sesiones de Hibernate.
     */
    private static SessionFactory buildSessionFactory(boolean show) {
        try {
            // Crea la configuración desde hibernate.cfg.xml
            showLog(show);
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al construir SessionFactory: " + e.getMessage(), e);
        }
    }

    /**
     * Cierra la fábrica de sesiones de Hibernate si no está cerrada.
     */
    private static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    /**
     * Imprime la pila de excepciones y detalles específicos de las excepciones de Hibernate.
     *
     * @param e Excepción a imprimir.
     */
    public static void printException(Exception e){
        System.err.println("---- Pila de excepciones INICIO ----");
        System.err.println("[superior]" + e.getClass().toString());
        Throwable th = e;
        int i=1;
        while ((th != null) && !(th instanceof ConstraintViolationException)) {
            th = th.getCause();
            if (th != null)
                System.err.println("[-" + (i++) + "]" + th.getClass().toString());
        }
        System.err.println("---- Pila de excepciones: FIN   ----");
        if (th !=null && th instanceof ConstraintViolationException ) {
            ConstraintViolationException cve = (ConstraintViolationException) th;
            System.err.println("===================================");
            System.err.println("Excepción de Hibernate de tipo " + cve.getClass().getName() + ": [" + cve.getMessage() + "]");
            System.err.println("Sentencia SQL: " + cve.getSQL());
            System.err.println("Restricción violada: " + cve.getConstraintName());
            System.err.print("Error de la excepción SQLException: ");
            System.err.println(cve.getSQLException().getMessage());
            System.err.println("===================================");
        } else {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Clase interna para manejar el cierre de la fábrica de sesiones al apagar la aplicación.
     */
    private static class ThreadOff extends Thread {
        @Override
        public void run() {
            super.run();
            closeSessionFactory();
        }
    }
}
