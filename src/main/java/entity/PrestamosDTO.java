package entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
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
}
