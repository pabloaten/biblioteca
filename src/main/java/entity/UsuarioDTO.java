package entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
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
}
