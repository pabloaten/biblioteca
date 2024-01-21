package entity;

import entity.LibroDTO;

import javax.persistence.*;
import java.util.Collection;

@Entity
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
}
