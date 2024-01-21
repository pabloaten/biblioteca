package views.helper;


import repository.UsuarioRepository;
import entity.BusquedaUsuario;
import entity.UsuarioDTO;

import presenter.PresentadorUsuario;
import views.*;

import java.awt.*;
/**
 * Clase que proporciona métodos estáticos para la gestión de la interfaz de usuario relacionada con usuarios.
 */
public class Usuarios {
    /**
     * Abre una ventana que muestra una lista de usuarios.
     *
     * @return Instancia de ListaUsuarios que representa la ventana de lista de usuarios.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static ListaUsuarios listaUsuarios() throws Exception {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ListaUsuarios listaUsuarios =new ListaUsuarios();
        PresentadorUsuario presentadorUsuario =new PresentadorUsuario(usuarioRepository,listaUsuarios);
        listaUsuarios.setPresentador(presentadorUsuario);
        listaUsuarios.lanzar();
        return listaUsuarios;
    }

    /**
     * Abre una ventana que muestra la ficha de un usuario específico.
     *
     * @param usuario UsuarioDTO que representa la información del usuario a mostrar en la ficha.
     * @return Instancia de FichaUsuario que representa la ventana de ficha de usuario.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static FichaUsuario fichaUsuario(UsuarioDTO usuario) throws Exception {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        FichaUsuario fichaUsuario =new FichaUsuario(usuario);
        PresentadorUsuario presentadorUsuario=new PresentadorUsuario(usuarioRepository,fichaUsuario);
        fichaUsuario.setPresentador(presentadorUsuario);
        fichaUsuario.lanzar();
        return fichaUsuario;

    }
    /**
     * Abre una ventana que permite seleccionar un usuario de una lista.
     *
     * @param owner             Frame que representa el propietario de la ventana.
     * @param title             Título de la ventana.
     * @param modal             Indica si la ventana es modal o no.
     * @param busquedaUsuario  Instancia de BusquedaUsuario que define criterios de búsqueda para la selección.
     * @return Instancia de SeleccionaUsuario que representa la ventana de selección de usuario.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static SeleccionaUsuario seleccionaUsuario(Frame owner, String title, boolean modal, BusquedaUsuario busquedaUsuario) throws Exception {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        SeleccionaUsuario seleccionaUsuario=new SeleccionaUsuario(owner, title, modal,busquedaUsuario);
        PresentadorUsuario presentadorUsuario=new PresentadorUsuario(usuarioRepository,seleccionaUsuario);
        seleccionaUsuario.setPresentador(presentadorUsuario);
        seleccionaUsuario.lanzar();
        return seleccionaUsuario;
    }
}
