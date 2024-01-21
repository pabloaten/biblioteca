package presenter;

import entity.CategoriaDTO;

import java.util.List;

public interface VistaCategorias extends VistaCategoria{
    /**
     * Establece la lista de categorías en la vista.
     *
     * @param listaCategorias Lista de objetos CategoriaDTO que representan las categorías a mostrar.
     */
    void setCategorias(List<CategoriaDTO> listaCategorias);
}
