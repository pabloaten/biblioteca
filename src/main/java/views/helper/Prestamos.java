package views.helper;


import repository.CategoriaRepository;
import repository.LibroRepository;
import repository.PrestamoRepository;
import repository.UsuarioRepository;
import entity.PrestamosDTO;
import presenter.PresentadorPrestamos;
import views.FichaPrestamo;
import views.ListaPrestamos;

/**
 * Clase que proporciona métodos estáticos para la gestión de la interfaz de usuario relacionada con préstamos.
 */
public class Prestamos {
    /**
     * Abre una ventana que muestra una lista de préstamos.
     *
     * @return Instancia de ListaPrestamos que representa la ventana de lista de préstamos.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static ListaPrestamos listaPrestamos() throws Exception {
        PrestamoRepository prestamoRepository = new PrestamoRepository();
        CategoriaRepository categoriaRepository =new CategoriaRepository();

        ListaPrestamos listaPrestamos=new ListaPrestamos();
        PresentadorPrestamos presentadorPrestamos=new PresentadorPrestamos(prestamoRepository, categoriaRepository,listaPrestamos);
        listaPrestamos.setPresentador(presentadorPrestamos);
        listaPrestamos.lanzar();
        return listaPrestamos;
    }

    /**
     * Abre una ventana que muestra la ficha de un préstamo específico.
     *
     * @param prestamo PrestamosDTO que representa la información del préstamo a mostrar en la ficha.
     * @return Instancia de FichaPrestamo que representa la ventana de ficha de préstamo.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static FichaPrestamo fichaPrestamo(PrestamosDTO prestamo) throws Exception {
        PrestamoRepository prestamoRepository =new PrestamoRepository();
        CategoriaRepository categoriaRepository =new CategoriaRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        LibroRepository libroDAO=new LibroRepository();
        FichaPrestamo fichaPrestamo=new FichaPrestamo(prestamo);
        PresentadorPrestamos presentadorPrestamo=new PresentadorPrestamos(prestamoRepository, categoriaRepository,fichaPrestamo);
        fichaPrestamo.setPresentador(presentadorPrestamo);
        fichaPrestamo.lanzar();
        return fichaPrestamo;
    }
}
