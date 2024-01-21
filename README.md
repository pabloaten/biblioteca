# Gestion de una biblioteca usando *hibernate*

## Correspondencia entre clases y tablas

A continuación, vamos a detallar la correspondencia entre las clases de nuestro proyecto de Hibernate y las tablas de la base de datos haciendo también incampié en las clases controladoras con sus métodos:

### 1. Tabla `Categorias`

#### Clase `CategoriaDTO`

La clase `CategoriaDTO` representa una categoría de libros. La tabla asociada, llamada `Categorias`, almacena la información detallada de cada categoría.

'@Entity
@Table(name = "categoria", schema = "BIBLIOTECA", catalog = "")
public class CategoriaDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "categoria", nullable = true, length = -1)
    private String categoria;
    @OneToMany(mappedBy = "categoriaByCategoria",fetch = FetchType.EAGER)
    private Collection<LibroDTO> librosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Collection<LibroDTO> getLibrosById() {
        return librosById;
    }

    public void setLibrosById(Collection<LibroDTO> librosById) {
        this.librosById = librosById;
    }

    @Override
    public String toString() {
        return categoria;
    }
}'

#### Clase `CategoriaController`

La clase `CategoriaController` contiene los métodos necesarios para cumplir con el CRUD de la tabla Categoria.

'
public class CategoriaController {

    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public void inserta(CategoriaDTO categoriaDTO) throws Exception {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(categoriaDTO);
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas creando la categoría: %s", categoriaDTO));
        }
        LogFile.saveLOG("Categoria insertada: " + categoriaDTO.toString());
    }

    public void modificar(CategoriaDTO categoriaDTO) throws Exception {
        try {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(categoriaDTO);
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception(String.format("Problemas actualizando la categoría: %s", categoriaDTO));
        }
        LogFile.saveLOG("Categoria modificada: " +  categoriaDTO.getCategoria() + " | con id: " + categoriaDTO.getId());
    }

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

    public List<CategoriaDTO> leerTodasCategorias() throws Exception {
        List<CategoriaDTO> lista = null;
        try {
            Transaction transaction = session.beginTransaction();
            String hql = "FROM CategoriaDTO";
            Query<CategoriaDTO> query = session.createQuery(hql, CategoriaDTO.class);
            lista = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            HibernateUtil.printException(e);
            throw new Exception("Problemas obteniendo todas las categorías");
        }
        LogFile.saveLOG("Seleccionadas todas las categorias. ");
        return lista;
    }
}'

### 2. Tabla `Libros`

#### Clase `LibroDTO`

La clase `LibroDTO` modela la entidad de libro en nuestra aplicación. La información de cada libro se guarda en la tabla `Libros`, que incluye detalles como autor, nombre, editorial y categoría.

'@Entity
@Table(name = "libro", schema = "BIBLIOTECA", catalog = "")
public class LibroDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = true, length = -1)
    private String nombre;
    @Basic
    @Column(name = "autor", nullable = true, length = -1)
    private String autor;
    @Basic
    @Column(name = "editorial", nullable = true, length = -1)
    private String editorial;
    @Basic
    @Column(name = "categoria", nullable = true)
    private Integer categoria;
    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private CategoriaDTO categoriaByCategoria;
    @OneToMany(mappedBy = "libroByIdLibro",fetch = FetchType.EAGER)
    private Collection<PrestamosDTO> prestamosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public CategoriaDTO getCategoriaByCategoria() {
        return categoriaByCategoria;
    }

    public void setCategoriaByCategoria(CategoriaDTO categoriaByCategoria) {
        this.categoriaByCategoria = categoriaByCategoria;
    }

    public Collection<PrestamosDTO> getPrestamosById() {
        return prestamosById;
    }

    public void setPrestamosById(Collection<PrestamosDTO> prestamosById) {
        this.prestamosById = prestamosById;
    }

    @Override
    public String toString() {
        return nombre;
    }
}'

#### Clase `LibroController`

La clase `LibroController` contiene los métodos necesarios para cumplir con el CRUD de la tabla Libro.

'
public class LibroController {
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public List<LibroDTO> leerLibros() throws Exception {
        List<LibroDTO> lista = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM LibroDTO";
            Query<LibroDTO> query = session.createQuery(hql, LibroDTO.class);

            lista = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception("Problemas obteniendo todos los libros");
        }
        LogFile.saveLOG("Seleccionados todos los libros. ");
        return lista;
    }

    public void inserta(LibroDTO libroDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(libroDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas creando el libro: %s", libroDTO));
        }
        LogFile.saveLOG("Libro insertado: " + libroDTO.toString());
    }

    public LibroDTO getLibro(int id) throws Exception {
        LibroDTO libroDTO = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            libroDTO = session.get(LibroDTO.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas obteniendo el libro con id: %d", id));
        }
        LogFile.saveLOG("Libro seleccionado: " + libroDTO.getNombre() + " | con id: " + libroDTO.getId());
        return libroDTO;
    }

    public void modificar(LibroDTO libroDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(libroDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas actualizando el libro: %s", libroDTO));
        }
        LogFile.saveLOG("Libro modificado: " +  libroDTO.getNombre() + " | con id: " + libroDTO.getId());
    }

    public void borrar(int id) throws Exception {
        LibroDTO libroDTO = getLibro(id);
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(libroDTO);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas borrando el libro: %s", libroDTO));
        }

        LogFile.saveLOG("Libro eliminado: " + libroDTO.toString());
    }

    public List<LibroDTO> leerLibrosOR(int id, String titulo, String autor, String editorial, int categoria) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM LibroDTO l WHERE 1=1";
            if (id != 0) {
                hql += " AND l.id = :id";
            }
            if (titulo != null && !titulo.trim().isEmpty()) {
                hql += " AND l.nombre LIKE :titulo";
            }
            if (autor != null && !autor.trim().isEmpty()) {
                hql += " AND l.autor LIKE :autor";
            }
            if (editorial != null && !editorial.trim().isEmpty()) {
                hql += " AND l.editorial LIKE :editorial";
            }
            if (categoria != 0) {
                hql += " AND l.categoria = :categoria";
            }

            org.hibernate.query.Query<LibroDTO> query = session.createQuery(hql, LibroDTO.class);
            if (id != 0) {
                query.setParameter("id", id);
            }
            if (titulo != null && !titulo.trim().isEmpty()) {
                query.setParameter("titulo", "%" + titulo + "%");
            }
            if (autor != null && !autor.trim().isEmpty()) {
                query.setParameter("autor", "%" + autor + "%");
            }
            if (editorial != null && !editorial.trim().isEmpty()) {
                query.setParameter("editorial", "%" + editorial + "%");
            }
            if (categoria != 0) {
                query.setParameter("categoria", categoria);
            }

            List<LibroDTO> lista = query.list();
            transaction.commit();
            LogFile.saveLOG("Seleccionados todos los atributos de libro.");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error leyendo los libros.", e);
        }
    }
}'

### 3. Tabla `Usuarios`

#### Clase `UsuarioDTO`

La clase `UsuarioDTO` corresponde a la entidad de usuario. Los detalles de los usuarios se mantienen en la tabla `Usuarios`, que incluye información como nombre y apellidos.

'@Entity
@Table(name = "usuario", schema = "BIBLIOTECA", catalog = "")
public class UsuarioDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = true, length = -1)
    private String nombre;
    @Basic
    @Column(name = "apellidos", nullable = true, length = -1)
    private String apellidos;
    @OneToMany(mappedBy = "usuarioByIdUsuario")
    private Collection<PrestamosDTO> prestamosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Collection<PrestamosDTO> getPrestamosById() {
        return prestamosById;
    }

    public void setPrestamosById(Collection<PrestamosDTO> prestamosById) {
        this.prestamosById = prestamosById;
    }

    @Override
    public String toString() {
        return nombre;
    }
}'

#### Clase `UsuarioController`

La clase `UsuarioController` contiene los métodos necesarios para cumplir con el CRUD de la tabla Usuario.

'
public class UsuarioController {
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public List<UsuarioDTO> leerUsuarios() throws Exception {
        List<UsuarioDTO> lista = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM UsuarioDTO";
            Query<UsuarioDTO> query = session.createQuery(hql, UsuarioDTO.class);

            lista = query.getResultList();

            transaction.commit();
            HistoricoHelper.guardarMensaje("OBTENIENDO LOS USUARIOS");
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception("Problemas obteniendo todas las categorías");
        }
        LogFile.saveLOG("Seleccionados todos los usuarios.");
        return lista;
    }

    public void inserta(UsuarioDTO usuarioDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(usuarioDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas creando la categoria: %s", usuarioDTO));
        }
        LogFile.saveLOG("Usuario insertado: " + usuarioDTO.toString());
    }

    public UsuarioDTO getUsuario(int id) throws Exception {
        UsuarioDTO usuarioDTO = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            usuarioDTO = session.get(UsuarioDTO.class, id);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas obteniendo el departamento con id: %d", id));
        }
        LogFile.saveLOG("Usuario seleccionado: " + usuarioDTO.getNombre() + " " + usuarioDTO.getApellidos() + " | con id: " + usuarioDTO.getId());
        return usuarioDTO;
    }

    public void modificar(UsuarioDTO usuarioDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(usuarioDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas actualizando la Categoria: %s", usuarioDTO));
        }
        LogFile.saveLOG("Usuario modificado: " +  usuarioDTO.getNombre() + " " + usuarioDTO.getApellidos() + " | con id: " + usuarioDTO.getId());
    }

    public void borrar(int id) throws Exception {
        UsuarioDTO usuarioDTO = getUsuario(id);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(usuarioDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas borrando la categoria: %s", usuarioDTO));
        }
        LogFile.saveLOG("Usuario eliminado: " + usuarioDTO.toString());
    }

    public List<UsuarioDTO> leerUsuariosOR(int id, String nombre, String apellidos) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM UsuarioDTO u WHERE 1=1";
            if (id != 0) {
                hql += " AND u.id = :id";
            }
            if (nombre != null && !nombre.trim().isEmpty()) {
                hql += " AND u.nombre LIKE :nombre";
            }
            if (apellidos != null && !apellidos.trim().isEmpty()) {
                hql += " AND u.apellidos LIKE :apellidos";
            }

            org.hibernate.query.Query<UsuarioDTO> query = session.createQuery(hql, UsuarioDTO.class);
            if (id != 0) {
                query.setParameter("id", id);
            }
            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (apellidos != null && !apellidos.trim().isEmpty()) {
                query.setParameter("apellidos", "%" + apellidos + "%");
            }

            List<UsuarioDTO> lista = query.list();
            transaction.commit();
            LogFile.saveLOG("Seleccionados todos los atributos de usuario.");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error leyendo los usuarios.", e);
        }
    }
}'

### 4. Tabla `Prestamos`

#### Clase `PrestamosDTO`

La clase `PrestamosDTO` gestiona los préstamos de libros. La tabla asociada, `Prestamos`, almacena detalles como el libro prestado, el usuario que realizó el préstamo y la fecha correspondiente.

'@Entity
@Table(name = "prestamos", schema = "BIBLIOTECA", catalog = "")
public class PrestamosDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPrestamo", nullable = false)
    private int idPrestamo;
    @Basic
    @Column(name = "idLibro", nullable = true)
    private Integer idLibro;
    @Basic
    @Column(name = "idUsuario", nullable = true)
    private Integer idUsuario;
    @Basic
    @Column(name = "fechaPrestamo", nullable = true)
    private Timestamp fechaPrestamo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLibro", referencedColumnName = "id")
    private LibroDTO libroByIdLibro;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private UsuarioDTO usuarioByIdUsuario;

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Timestamp fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LibroDTO getLibroByIdLibro() {
        return libroByIdLibro;
    }

    public void setLibroByIdLibro(LibroDTO libroByIdLibro) {
        this.libroByIdLibro = libroByIdLibro;
    }

    public UsuarioDTO getUsuarioByIdUsuario() {
        return usuarioByIdUsuario;
    }

    @Override
    public String toString() {
        return
                "IdPrestamo: " + idPrestamo +
                ", IdLibro: " + idLibro +
                ", IdUsuario: " + idUsuario +
                ", FechaPrestamo: " + fechaPrestamo;
    }

    public void setUsuarioByIdUsuario(UsuarioDTO usuarioByIdUsuario) {
        this.usuarioByIdUsuario = usuarioByIdUsuario;
    }
}'

#### Clase `PrestamoController`

La clase `PrestamoController` contiene los métodos necesarios para cumplir con el CRUD de la tabla Prestamo.

'
public class PrestamoController {
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public List<PrestamosDTO> leerPrestamos() throws Exception {
        List<PrestamosDTO> lista = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM PrestamosDTO";
            Query<PrestamosDTO> query = session.createQuery(hql, PrestamosDTO.class);

            lista = query.getResultList();

            transaction.commit();
            HistoricoHelper.guardarMensaje("OBTENIENDO LOS PRÉSTAMOS");
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception("Problemas obteniendo los préstamos");
        }
        LogFile.saveLOG("Lista de prestamos: ");
        for (PrestamosDTO prestamo : lista) {
            LogFile.saveLOG("  -" + prestamo.toString());
        }
        return lista;
    }

    public void inserta(PrestamosDTO prestamosDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(prestamosDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas creando el préstamo: %s", prestamosDTO));
        }
        LogFile.saveLOG("Prestamo insertado: " + prestamosDTO.toString());
    }

    public void modificar(PrestamosDTO prestamosDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(prestamosDTO);

            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas actualizando el Préstamo: %s", prestamosDTO));
        }
        LogFile.saveLOG("Prestamo modificado: " +  prestamosDTO.toString());
    }

    public PrestamosDTO getPrestamo(int id) throws Exception {
        PrestamosDTO prestamosDTO = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            prestamosDTO = session.get(PrestamosDTO.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas obteniendo el préstamo con id: %d", id));
        }
        LogFile.saveLOG("Prestamo seleccionado: " + prestamosDTO.toString());
        return prestamosDTO;
    }

    public void borrar(int id) throws Exception {
        PrestamosDTO prestamosDTO = getPrestamo(id);
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(prestamosDTO);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            HibernateUtil.printException(e); // para el archivo log
            throw new Exception(String.format("Problemas borrando el préstamo: %s", prestamosDTO));
        }
        LogFile.saveLOG("Prestamo eliminado: " + prestamosDTO.toString());
    }
}'

### 5. Tabla `Historico`

#### Clase `HistoricoDTO`

La clase `HistoricoDTO` hace un seguimiento de las acciones del usuario. La tabla asociada, `Historico`, almacena detalles como el usuario que realizó la acción, la fecha en la que se realizó dicha acción y la información correspondiente.

'@Entity
@Table(name = "historico", schema = "BIBLIOTECA", catalog = "")
public class HistoricoDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idHistorico", nullable = false)
    private int idHistorico;
    @Basic
    @Column(name = "user", nullable = true, length = -1)
    private String user;
    @Basic
    @Column(name = "fecha", nullable = true)
    private Timestamp fecha;
    @Basic
    @Column(name = "info", nullable = true, length = -1)
    private String info;

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}'

#### Clase `HistoricoHelper`

La clase `HistoricoHelper` contiene un único método con el que guardar los datos a insertar en la tabla Historico.

'
public class HistoricoHelper {

    private static Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public static void guardarMensaje( String mensaje) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             Transaction transaction = session.beginTransaction();
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            // Crear una instancia de HistoricoDTO
            HistoricoDTO historico = new HistoricoDTO();
            // Obtiene la configuración a partir de la sesión

            historico.setUser(configuration.getProperty("hibernate.connection.username"));
            historico.setFecha(new Timestamp(System.currentTimeMillis()));
            historico.setInfo(mensaje);

            // Guardar la instancia en la base de datos
            session.save(historico);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }
}'

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
