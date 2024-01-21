package presenter;



import repository.UsuarioRepository;
/**
 * Clase que actúa como presentador para la entidad 'Usuario', gestionando la interacción entre la vista y el controlador.
 */
public class PresentadorUsuario {
    private UsuarioRepository usuarioRepository;
    private VistaUsuario vistaUsuario;

    /**
     * Constructor que inicializa el PresentadorUsuario con un controlador de usuarios y una vista.
     *
     * @param usuarioRepository Controlador de la entidad 'Usuario'.
     * @param vistaUsuario       Vista asociada al presentador.
     */
    public PresentadorUsuario(UsuarioRepository usuarioRepository, VistaUsuario vistaUsuario) {
        this.usuarioRepository = usuarioRepository;
        this.vistaUsuario = vistaUsuario;
    }

    /**
     * Obtiene y muestra todos los usuarios utilizando el controlador de usuarios y actualiza la vista correspondiente.
     *
     * @throws Exception Si hay problemas durante el proceso de obtención de los usuarios.
     */
    public void listaAllUsuarios() throws Exception {
        VistaUsuarios vistaCategorias = (VistaUsuarios) vistaUsuario;
        vistaCategorias.setUsuarios(usuarioRepository.leerUsuarios());
    }

    /**
     * Modifica un usuario existente utilizando el controlador de usuarios.
     *
     * @throws Exception Si hay problemas durante el proceso de modificación.
     */
    public void modifica() throws Exception {
       usuarioRepository.modificar(vistaUsuario.getUsuario());
    }

    /**
     * Elimina un usuario existente utilizando el controlador de usuarios.
     *
     * @throws Exception Si hay problemas durante el proceso de eliminación.
     */
    public void borra() throws Exception {
        usuarioRepository.borrar(vistaUsuario.getUsuario().getId());
    }

    /**
     * Inserta un nuevo usuario utilizando el controlador de usuarios.
     *
     * @throws Exception Si hay problemas durante el proceso de inserción.
     */
    public void inserta() throws Exception {
        usuarioRepository.inserta(vistaUsuario.getUsuario());
    }

    /**
     * Obtiene y muestra usuarios según los parámetros especificados utilizando el controlador de usuarios y actualiza la vista correspondiente.
     *
     * @param id        Identificador del usuario.
     * @param nombre    Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @throws Exception Si hay problemas durante el proceso de obtención de los usuarios.
     */
    public void leerUsuariosOR(int id,String nombre,String apellidos) throws Exception {
        VistaUsuarios vistaUsuarios = (VistaUsuarios) vistaUsuario;
        vistaUsuarios.setUsuarios(usuarioRepository.leerUsuariosOR(id,nombre,apellidos));
    }
}
