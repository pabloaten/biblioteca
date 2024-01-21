# Gestion de una biblioteca usando *hibernate*

## Patron Observer

Para la implementación del patrón observer lo primero que hemos echo ha sido crear una interfaz llamada Observer con los metodos para actualizar nuestras entidades

```java 
interface Observer {
    void update(String message);
    void actualizarListaUsuarios(List<UsuarioDTO> usuarios);
    void actualizarListaLibros(List<LibroDTO> libros);
    void actualizarListaCategorias(List<CategoriaDTO> categorias);
    void actualizarListaPrestamos(List<PrestamosDTO> prestamos);
}
```
Tambien creamos una clase llamda PatronObserver que va a implementar los métodos de actualizar. Una vez llamados actualizara la vista del FormMain. Aqui un ejemplo
```java
  @Override
    public void actualizarListaUsuarios(List<UsuarioDTO> usuarios) {
        for (int i=0;i< FormMain.getInstance().getDesktopPane().getComponentCount();i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof  ListaUsuarios)
                ((ListaUsuarios) FormMain.getInstance().getDesktopPane().getComponent(i)).setUsuarios(usuarios);

    }
    
```
Cada vez que queramos actualizar cualquier entidad en nuestra ventana se llamara a la clase que controlara el contenido a mostrar y se actualizara
 ```java
 public static void actualizaListaUsuarios() throws SQLException, CampoVacioExcepcion, IOException {
        List<UsuarioDTO> usuarios = Entidades.leerAllUsuarios();
        patron.actualizarListaUsuarios(usuarios);

    }
 ```

## Dificultades encontradas durante el desarrollo

### -JPA
A la hora de implementar hibernate en nuestro proyecto, surgieron varias dificultades, entre ellas, la inclusión de JPA.
Intentamos utilizar la API **EntityManager**, pero a la hora de realizar las operaciones *CRUD* simplemente no encotraba las entidades y nos mostraba diversos errores en el *persistence.xml*, por más que buscasemos la solución a estos errores, se hacía muy complejo e ibamos a invertir demasiado tiempo en arreglarlo, asi que al final nos decantamos por usar Hibernate manejando las *Sesiones*.

### -Errores de sesiones
El implementar las sesiones no fue extento de errores, las operaciones de *insercion*, *actualizacion* y *seleccion* funcionaban correctamente, pero las operaciones de *borrado* no funcionaban ya que la sesion se cerraba. La solucion que encontramos fue implementar la apertura de la sesion en uno de nuestros metodos que obtenía una cierta entidad, por ejemplo, una categoría especifica, ya que el metodo de borrado hacia de la utilización de este otro método de obtención.

**Codigo de obtencion de la categoria**

```java
    public CategoriaDTO getCategoria(int id) throws Exception {
        CategoriaDTO categoriaDTO = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            categoriaDTO = session.get(CategoriaDTO.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas obteniendo la categoría con id: %d", id));
        }
        LogFile.saveLOG("Categoria seleccionada: " + categoriaDTO.getCategoria() + " | con id: " + categoriaDTO.getId());

        return categoriaDTO;
    }
```

**Codigo de borrado con el metodo de obtención implementado**

```java
  public void borrar(int id) throws Exception {
        CategoriaDTO categoriaDTO = getCategoria(id);
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(categoriaDTO);
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas borrando la categoría: %s", categoriaDTO));
        }
        LogFile.saveLOG("Categoria eliminada: " + categoriaDTO.toString());
    }
```

### -Implementacion de los datos 
Surgieron diversos errores cuando haciamos operaciones de inserción y modificación en algunas de las entidades, por ejemplo, entre libro y categoria. El error en cuestión, era el siguiente:
*repeated column in mapping for entity column (should be mapped with insert= false update= false)*
Este error ocurre cuando estás utilizando Hibernate y tienes una entidad con dos referencias a otra entidad, ambas mapeadas a la misma columna en la base de datos. Hibernate intenta insertar y actualizar esta columna dos veces, lo que causa el error. La solución a esto, fue en colocar los atributos de inserción y actualización a "false" en una de las dos referencias, y de esta manera hibernate solo actualiza una vez esa columna.

**Codigo xml referenciando a categoria en el documento LibroDTO.hbm.xml**

```xml
 <property name="categoria" insert="false" update="false">
            <column name="categoria" sql-type="int" not-null="true"/>
        </property>
```
