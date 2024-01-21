package presenter;

import repository.CategoriaRepository;
import repository.PrestamoRepository;
/**
 * Clase que actúa como presentador para la entidad 'Prestamo', gestionando la interacción entre la vista y el controlador.
 */
public class PresentadorPrestamos {
    private PrestamoRepository prestamoRepository;
    private CategoriaRepository categoriaRepository;
    private VistaPrestamo vistaPrestamo;

    /**
     * Constructor que inicializa el PresentadorPrestamos con un controlador de préstamos, un controlador de categorías y una vista.
     *
     * @param prestamoRepository   Controlador de la entidad 'Prestamo'.
     * @param categoriaRepository  Controlador de la entidad 'Categoria'.
     * @param vistaPrestamo        Vista asociada al presentador.
     */
    public PresentadorPrestamos(PrestamoRepository prestamoRepository, CategoriaRepository categoriaRepository, VistaPrestamo vistaPrestamo) {
        this.prestamoRepository = prestamoRepository;
        this.categoriaRepository = categoriaRepository;
        this.vistaPrestamo = vistaPrestamo;
    }

    /**
     * Elimina un préstamo existente utilizando el controlador de préstamos.
     *
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borra() throws Exception {
        prestamoRepository.borrar(vistaPrestamo.getPrestamo().getIdPrestamo());
    }

    /**
     * Inserta un nuevo préstamo utilizando el controlador de préstamos.
     *
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta() throws Exception {
        System.out.println(vistaPrestamo.getPrestamo().toString());

        prestamoRepository.inserta(vistaPrestamo.getPrestamo());
    }

    /**
     * Modifica un préstamo existente utilizando el controlador de préstamos.
     *
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modifica() throws Exception {
        prestamoRepository.modificar(vistaPrestamo.getPrestamo());
    }

    /**
     * Obtiene y muestra todos los préstamos utilizando el controlador de préstamos y actualiza la vista correspondiente.
     *
     * @throws Exception Si hay problemas durante el proceso de obtención de los préstamos.
     */
    public void listaAllPrestamos() throws Exception {
        VistaPrestamos vistaPrestamos = (VistaPrestamos) vistaPrestamo;
        vistaPrestamos.setPrestamos(prestamoRepository.leerPrestamos());
    }

    /**
     * Obtiene y muestra todas las categorías utilizando el controlador de categorías y actualiza la vista correspondiente.
     */
    public void listaAllCategorias() {
        try {
            vistaPrestamo.setCategorias(categoriaRepository.leerTodasCategorias());
        } catch (Exception e){
            vistaPrestamo.setCategorias(null);
        }
    }
}
