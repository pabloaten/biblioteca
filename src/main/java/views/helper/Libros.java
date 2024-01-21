package views.helper;


import repository.LibroRepository;
import entity.BusquedaLibro;
import entity.LibroDTO;
import presenter.PresentadorLibro;
import views.*;

import java.awt.*;
/**
 * Clase que proporciona métodos estáticos para la gestión de la interfaz de usuario relacionada con libros.
 */
public class Libros {
    /**
     * Abre una ventana que muestra una lista de libros.
     *
     * @return Instancia de ListaLibros que representa la ventana de lista de libros.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static ListaLibros listaLibros() throws Exception {
        LibroRepository libroRepository = new LibroRepository();
        ListaLibros listaLibros =new ListaLibros();
        PresentadorLibro presentadorLibro =new PresentadorLibro(libroRepository,listaLibros);
        listaLibros.setPresentador(presentadorLibro);
        listaLibros.lanzar();
        return listaLibros;
    }

    /**
     * Abre una ventana que muestra la ficha de un libro específico.
     *
     * @param libro LibroDTO que representa la información del libro a mostrar en la ficha.
     * @return Instancia de FichaLibro que representa la ventana de ficha de libro.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static FichaLibro fichaLibro(LibroDTO libro) throws Exception {
       LibroRepository libroRepository = new LibroRepository();
        FichaLibro fichaLibro =new FichaLibro(libro);
        PresentadorLibro presentadorLibro=new PresentadorLibro(libroRepository,fichaLibro);
        fichaLibro.setPresentador(presentadorLibro);
        fichaLibro.lanzar();
        return fichaLibro;

    }
    /**
     * Abre una ventana de selección de libro para buscar y seleccionar un libro.
     *
     * @param owner          Marco padre de la ventana.
     * @param title          Título de la ventana.
     * @param modal          Indica si la ventana es modal o no.
     * @param busquedaLibro  Instancia de BusquedaLibro utilizada para realizar búsquedas de libros.
     * @return Instancia de SeleccionaLibro que representa la ventana de selección de libro.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    public static SeleccionaLibro seleccionaLibro(Frame owner, String title, boolean modal, BusquedaLibro busquedaLibro) throws Exception {
        LibroRepository libroRepository = new LibroRepository();
        SeleccionaLibro seleccionaLibro=new SeleccionaLibro(owner, title, modal,busquedaLibro);
        PresentadorLibro presentadorLibro =new PresentadorLibro(libroRepository,seleccionaLibro);
        seleccionaLibro.setPresentador(presentadorLibro);
        seleccionaLibro.lanzar();
        return seleccionaLibro;
    }
}
