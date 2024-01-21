package entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
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
}
