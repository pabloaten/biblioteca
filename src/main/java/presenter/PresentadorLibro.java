package presenter;


import repository.CategoriaRepository;
import repository.LibroRepository;

/**
 * Clase que actúa como presentador para la entidad 'Libro', gestionando la interacción entre la vista y el controlador.
 */
public class PresentadorLibro {
    private LibroRepository libroRepository;
    private VistaLibro vistaLibro;

    /**
     * Constructor que inicializa el PresentadorLibro con un controlador y una vista.
     *
     * @param libroRepository Controlador de la entidad 'Libro'.
     * @param vistaLibro      Vista asociada al presentador.
     */
    public PresentadorLibro(LibroRepository libroRepository, VistaLibro vistaLibro) {
        this.libroRepository = libroRepository;
        this.vistaLibro = vistaLibro;

    }

    /**
     * Obtiene y muestra todas las categorías utilizando un controlador de categorías y actualiza la vista correspondiente.
     */
    public void listaAllCategorias() {
        CategoriaRepository c = new CategoriaRepository();
        try {
            vistaLibro.setCategorias(c.leerTodasCategorias());
        } catch (Exception e) {
            vistaLibro.setCategorias(null);
        }
    }

    /**
     * Obtiene y muestra todos los libros utilizando el controlador de libros y actualiza la vista correspondiente.
     *
     * @throws Exception Si hay problemas durante el proceso de obtención de los libros.
     */
    public void listaAllLibros() throws Exception {
        VistaLibros vistaLibros = (VistaLibros) vistaLibro;
        vistaLibros.setLibros(libroRepository.leerLibros());
    }

    /**
     * Modifica un libro existente utilizando el controlador.
     *
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modifica() throws Exception {
        libroRepository.modificar(vistaLibro.getLibro());
    }

    /**
     * Elimina un libro utilizando el controlador.
     *
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borra() throws Exception {
        libroRepository.borrar(vistaLibro.getLibro().getId());
    }

    /**
     * Inserta un nuevo libro utilizando el controlador.
     *
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta() throws Exception {
        libroRepository.inserta(vistaLibro.getLibro());
    }

    /**
     * Obtiene y muestra los libros que cumplen con ciertos criterios utilizando el controlador y actualiza la vista correspondiente.
     *
     * @param id        Identificador del libro.
     * @param titulo    Título del libro.
     * @param autor     Autor del libro.
     * @param editorial Editorial del libro.
     * @param categoria Categoría del libro.
     * @throws Exception Si hay problemas durante el proceso de obtención de los libros.
     */
    public void leerLibrosOR(int id, String titulo, String autor, String editorial, int categoria) throws Exception {
        VistaLibros vistaLibros = (VistaLibros) vistaLibro;
        vistaLibros.setLibros(libroRepository.leerLibrosOR(id,titulo,autor,editorial,categoria));
    }
}
