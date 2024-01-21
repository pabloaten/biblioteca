# Gestion de una biblioteca usando _hibernate_

## Patron Observer

Para la implementación del patrón Observer lo primero que hemos hecho ha sido crear una interfaz llamada Observer con los métodos para actualizar nuestras entidades.

```java
interface Observer {
    void update(String message);
    void actualizarListaUsuarios(List<UsuarioDTO> usuarios);
    void actualizarListaLibros(List<LibroDTO> libros);
    void actualizarListaCategorias(List<CategoriaDTO> categorias);
    void actualizarListaPrestamos(List<PrestamosDTO> prestamos);
}
```

Tambien creamos una clase llamada PatronObserver que va a implementar los métodos de actualizar. Una vez llamados esto permitirá actualizar la vista del FormMain. Aqui un ejemplo:

```java
    @Override
    public void actualizarListaUsuarios(List<UsuarioDTO> usuarios) {
        for (int i=0;i< FormMain.getInstance().getDesktopPane().getComponentCount();i++)
            if (FormMain.getInstance().getDesktopPane().getComponent(i) instanceof  ListaUsuarios)
                ((ListaUsuarios) FormMain.getInstance().getDesktopPane().getComponent(i)).setUsuarios(usuarios);

    }

```

Cada vez que queramos actualizar cualquier entidad en nuestra ventana se llamara a la clase controladora del contenido a mostrar y esta se podrá actualizar.

```java
    public static void actualizaListaUsuarios() throws SQLException, CampoVacioExcepcion, IOException {
        List<UsuarioDTO> usuarios = Entidades.leerAllUsuarios();
        patron.actualizarListaUsuarios(usuarios);
    }
```

## Dificultades encontradas durante el desarrollo

### -JPA

A la hora de implementar hibernate en nuestro proyecto, surgieron varias dificultades, entre ellas, la inclusión de JPA.
Intentamos utilizar la API **EntityManager**, pero a la hora de realizar las operaciones _CRUD_ simplemente no encontraba las entidades y nos mostraba diversos errores en el _persistence.xml_, por más que buscasemos la solución a estos errores, se hacía muy complejo e ibamos a invertir demasiado tiempo en arreglarlo, asi que al final nos decantamos por usar Hibernate manejando las _Sesiones_.

### -Errores de sesiones

El implementar las sesiones no fue extento de errores, las operaciones de _inserción_, _actualización_ y _selección_ funcionaban correctamente, pero las operaciones de _borrado_ no funcionaban ya que la sesión se cerraba. La solución que encontramos fue implementar la apertura de la sesión en uno de nuestros métodos que obtenía una cierta entidad, por ejemplo, una categoría específica, ya que el método de borrado hacia de la utilización de este otro método de obtención.

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

**Codigo de borrado con el método de obtención implementado**

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
_repeated column in mapping for entity column (should be mapped with insert= false update= false)_
Este error ocurre cuando estás utilizando Hibernate y tienes una entidad con dos referencias a otra entidad, ambas mapeadas a la misma columna en la base de datos. Hibernate intenta insertar y actualizar esta columna dos veces, lo que causa el error. La solución a esto, fue en colocar los atributos de inserción y actualización a "false" en una de las dos referencias, y de esta manera hibernate solo actualiza una vez esa columna.

**Codigo xml referenciando a categoria en el documento LibroDTO.hbm.xml**

```xml
    <property name="categoria" insert="false" update="false">
        <column name="categoria" sql-type="int" not-null="true"/>
    </property>
```

