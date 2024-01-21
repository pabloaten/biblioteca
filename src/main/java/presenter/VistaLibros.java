package presenter;

import entity.LibroDTO;


import java.util.List;

public interface VistaLibros extends VistaLibro{
    /**
     * Establece la lista de libros en la vista.
     *
     * @param listaLibros Lista de objetos LibroDTO que representan los libros a mostrar.
     */
    void setLibros(List<LibroDTO> listaLibros);
}
