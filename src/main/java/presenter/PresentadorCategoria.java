package presenter;

import repository.CategoriaRepository;

/**
 * Clase que actúa como presentador para la entidad 'Categoria', gestionando la interacción entre la vista y el controlador.
 */
public class PresentadorCategoria {
    private CategoriaRepository categoriaRepository;
    private VistaCategoria vistaCategoria;

    /**
     * Constructor que inicializa el PresentadorCategoria con un controlador y una vista.
     *
     * @param categoriaRepository Controlador de la entidad 'Categoria'.
     * @param vistaCategoria      Vista asociada al presentador.
     */
    public PresentadorCategoria(CategoriaRepository categoriaRepository, VistaCategoria vistaCategoria) {
        this.categoriaRepository = categoriaRepository;
        this.vistaCategoria = vistaCategoria;
    }

    /**
     * Elimina una categoría utilizando el controlador.
     *
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borra() throws Exception {
        categoriaRepository.borrar(vistaCategoria.getCategoria().getId());
    }

    /**
     * Inserta una nueva categoría utilizando el controlador.
     *
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta() throws Exception {
        categoriaRepository.inserta(vistaCategoria.getCategoria());
    }

    /**
     * Modifica una categoría existente utilizando el controlador.
     *
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modifica() throws Exception {
        categoriaRepository.modificar(vistaCategoria.getCategoria());
    }

    /**
     * Obtiene y muestra todas las categorías utilizando el controlador y actualiza la vista correspondiente.
     *
     * @throws Exception Si hay problemas durante el proceso de obtención de las categorías.
     */
    public void listaAllCategorias() throws Exception {
        VistaCategorias vistaCategorias = (VistaCategorias) vistaCategoria;
        vistaCategorias.setCategorias(categoriaRepository.leerTodasCategorias());
    }
}
