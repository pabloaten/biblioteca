package presenter;



import entity.CategoriaDTO;
import entity.LibroDTO;

import java.util.List;

/**
 * Interfaz que define la vista de la entidad 'Libro'.
 */
public interface VistaLibro {
    /**
     * Método para lanzar la vista del libro.
     */
    void lanzar();
    /**
     * Establece el presentador asociado a la vista.
     *
     * @param presentador Presentador que interactuará con la vista.
     * @throws Exception Si hay problemas al establecer el presentador.
     */
    void setPresentador(PresentadorLibro presentador) throws Exception;
    /**
     * Obtiene la información del libro desde la vista.
     *
     * @return Objeto LibroDTO con la información del libro.
     */
    LibroDTO getLibro();

    /**
     * Establece las categorías disponibles en la vista.
     *
     * @param categorias Lista de categorías disponibles.
     */
    void setCategorias(List<CategoriaDTO> categorias);
}
