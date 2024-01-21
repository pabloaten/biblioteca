package presenter;

import entity.PrestamosDTO;
import entity.UsuarioDTO;

import java.util.List;

public interface VistaPrestamos extends VistaPrestamo {
    /**
     * Establece la lista de préstamos en la vista.
     *
     * @param listaPrestamos Lista de objetos PrestamosDTO que representan los préstamos a mostrar.
     */
    void setPrestamos(List<PrestamosDTO> listaPrestamos);
}
