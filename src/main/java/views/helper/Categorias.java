package views.helper;

import entity.CategoriaDTO;
import repository.CategoriaRepository;
import presenter.PresentadorCategoria;
import views.FichaCategoria;
import views.ListaCategorias;
/**
 * Clase de utilidad que proporciona métodos estáticos para interactuar con categorías.
 * Permite obtener listas de categorías y fichas de categorías utilizando controladores y presentadores.
 */
public class Categorias {
    /**
     * Obtiene una lista de categorías.
     *
     * @return Lista de categorías.
     * @throws Exception Si ocurre un error durante la obtención de la lista de categorías.
     */
    public static ListaCategorias listaCategorias() throws Exception {
        CategoriaRepository categoriaRepository = new CategoriaRepository();
        ListaCategorias listaCategorias=new ListaCategorias();
        PresentadorCategoria presentadorCategoria=new PresentadorCategoria(categoriaRepository,listaCategorias);
        listaCategorias.setPresentador(presentadorCategoria);
        listaCategorias.lanzar();
        return listaCategorias;
    }

    /**
     * Obtiene una ficha de categoría para la categoría proporcionada.
     *
     * @param categoria Categoría para la cual se obtendrá la ficha.
     * @return Ficha de categoría.
     * @throws Exception Si ocurre un error durante la obtención de la ficha de categoría.
     */
    public static FichaCategoria fichaCategoria(CategoriaDTO categoria) throws Exception {
        CategoriaRepository categoriaRepository = new CategoriaRepository();
        FichaCategoria fichaCategoria=new FichaCategoria(categoria);
        PresentadorCategoria presentadorCategoria=new PresentadorCategoria(categoriaRepository,fichaCategoria);
        fichaCategoria.setPresentador(presentadorCategoria);
        fichaCategoria.lanzar();
        return fichaCategoria;
    }
}
