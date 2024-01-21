package repository.helper;

import repository.CategoriaRepository;
import repository.LibroRepository;
import repository.PrestamoRepository;
import repository.UsuarioRepository;
import entity.CategoriaDTO;
import entity.LibroDTO;
import entity.PrestamosDTO;
import entity.UsuarioDTO;

import java.util.List;

public class Entidades {
    public static CategoriaDTO categoria(int id) {
        try {
            return new CategoriaRepository().getCategoria(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<CategoriaDTO> leerAllCategorias() {
        try {
            return new CategoriaRepository().leerTodasCategorias();
        } catch (Exception e) {
            return null;
        }
    }

    /*public static Libro libro(int id){
        try{
            return new LibroDAOImpl().getLibro(id);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static List<Libro> leerLibrosOR(int id, String titulo, String autor, String editorial, int categoria) {
        try{
            return new LibroDAOImpl().leerLibrosOR(id,titulo,autor,editorial,categoria);
        }
        catch (Exception e) {
            return null;
        }
    }
    */
    public static List<LibroDTO> leerAllLibros() {
        try {
            return new LibroRepository().leerLibros();
        } catch (Exception e) {
            return null;
        }
    }

    public static UsuarioDTO usuario(int id) {
        try {
            //return new UsuarioController().get(id);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<UsuarioDTO> leerAllUsuarios() {
        try {
            return new UsuarioRepository().leerUsuarios();
        } catch (Exception e) {
            return null;
        }
    }
    /*
    public static List<Usuario> leerUsuariosOR(int id,String nombre,String apellidos){
        try {
            return new UsuarioDAOImpl().leerUsuariosOR(id,nombre,apellidos);
        } catch (Exception e)  {
            return null;
        }
    }
    }*/


    public static PrestamosDTO prestamo(int id) {
        try {
            return new PrestamoRepository().getPrestamo(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<PrestamosDTO> leerAllPrestamos() {
        try {
            return new PrestamoRepository().leerPrestamos();
        } catch (Exception e) {
            return null;
        }
    }

}
