# biblioteca
App para gestionar una biblioteca
## Patron Observer
Para la implementación del patrón observer lo primero que hemos echo ha sido crear una interfaz llamada Observer con los metodos para actualizar nuestras entidades

```
interface Observer {
    void update(String message);
    void actualizarListaUsuarios(List<UsuarioDTO> usuarios);
    void actualizarListaLibros(List<LibroDTO> libros);
    void actualizarListaCategorias(List<CategoriaDTO> categorias);
    void actualizarListaPrestamos(List<PrestamosDTO> prestamos);
}
```
Tambien creamos una clase llamda PatronObserver que va a implementar los métodos de actualizar. Una vez llamados actualizara la vista del FormMain. Aqui un ejemplo
```
  @Override
    public void actualizarListaUsuarios(List<UsuarioDTO> usuarios) {
        for (int i=0;i< FormMain.getInstance().getDesktopPane().getComponentCount();i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof  ListaUsuarios)
                ((ListaUsuarios) FormMain.getInstance().getDesktopPane().getComponent(i)).setUsuarios(usuarios);

    }
    
```
Cada vez que queramos actualizar cualquier entidad en nuestra ventana se llamara a la clase que controlara el contenido a mostrar y se actualizara
 ```
 public static void actualizaListaUsuarios() throws SQLException, CampoVacioExcepcion, IOException {
        List<UsuarioDTO> usuarios = Entidades.leerAllUsuarios();
        patron.actualizarListaUsuarios(usuarios);

    }
    ```
