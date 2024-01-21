package repository.helper;

import entity.CategoriaDTO;
import entity.LibroDTO;
import entity.PrestamosDTO;
import entity.UsuarioDTO;
import views.*;

import java.util.List;

public class PatronObserverImpl implements Observer {
    @Override
    public void update(String message) {

    }

    @Override
    public void actualizarListaUsuarios(List<UsuarioDTO> usuarios) {
        for (int i=0;i< FormMain.getInstance().getDesktopPane().getComponentCount();i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof  ListaUsuarios)
                ((ListaUsuarios) FormMain.getInstance().getDesktopPane().getComponent(i)).setUsuarios(usuarios);

    }

    @Override
    public void actualizarListaLibros(List<LibroDTO> libros) {
        for (int i = 0; i < FormMain.getInstance().getDesktopPane().getComponentCount(); i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof ListaLibros)
                ((ListaLibros) FormMain.getInstance().getDesktopPane().getComponent(i)).setLibros(libros);
    }

    @Override
    public void actualizarListaCategorias(List<CategoriaDTO> categorias) {
        for (int i = 0; i < FormMain.getInstance().getDesktopPane().getComponentCount(); i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof ListaCategorias)
                ((ListaCategorias) FormMain.getInstance().getDesktopPane().getComponent(i)).setCategorias(categorias);
    }

    @Override
    public void actualizarListaPrestamos(List<PrestamosDTO> prestamos) {
        for (int i=0;i< FormMain.getInstance().getDesktopPane().getComponentCount();i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof ListaPrestamos)
                ((ListaPrestamos) FormMain.getInstance().getDesktopPane().getComponent(i)).setPrestamos(prestamos);
    }
}
