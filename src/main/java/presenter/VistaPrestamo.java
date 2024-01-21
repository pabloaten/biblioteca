package presenter;

import entity.CategoriaDTO;
import entity.PrestamosDTO;
import entity.UsuarioDTO;

import java.util.List;
/**
 * Interfaz que define la vista de la entidad 'Préstamo'.
 */
public interface VistaPrestamo {
    /**
     * Método para lanzar la vista del préstamo.
     */
    void lanzar();

    /**
     * Establece el presentador asociado a la vista.
     *
     * @param presentador Presentador que interactuará con la vista.
     * @throws Exception Si hay problemas al establecer el presentador.
     */
    void setPresentador(PresentadorPrestamos presentador) throws Exception;
    /**
     * Obtiene la información del préstamo desde la vista.
     *
     * @return Objeto PrestamosDTO con la información del préstamo.
     */
    PrestamosDTO getPrestamo();
    /**
     * Establece las categorías disponibles en la vista.
     *
     * @param categorias Lista de objetos CategoriaDTO con la información de las categorías.
     */
    void setCategorias(List<CategoriaDTO> categorias);
}
