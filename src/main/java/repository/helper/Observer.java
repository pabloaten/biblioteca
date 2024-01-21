package repository.helper;

import entity.CategoriaDTO;
import entity.LibroDTO;
import entity.PrestamosDTO;
import entity.UsuarioDTO;

import java.util.List;

interface Observer {
    void update(String message);
    void actualizarListaUsuarios(List<UsuarioDTO> usuarios);
    void actualizarListaLibros(List<LibroDTO> libros);
    void actualizarListaCategorias(List<CategoriaDTO> categorias);
    void actualizarListaPrestamos(List<PrestamosDTO> prestamos);
}
