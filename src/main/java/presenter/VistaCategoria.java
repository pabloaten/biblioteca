package presenter;


import entity.CategoriaDTO;

/**
 * Interfaz que define la vista de la entidad 'Categoría'.
 */
public interface VistaCategoria {
    /**
     * Método para lanzar la vista de la categoría.
     */
    void lanzar();
    /**
     * Establece el presentador asociado a la vista.
     *
     * @param presentador Presentador que interactuará con la vista.
     * @throws Exception Si hay problemas al establecer el presentador.
     */
    void setPresentador(PresentadorCategoria presentador) throws Exception;
    /**
     * Obtiene la información de la categoría desde la vista.
     *
     * @return Objeto CategoriaDTO con la información de la categoría.
     */
    CategoriaDTO getCategoria();
}
