package presenter;


import entity.UsuarioDTO;
/**
 * Interfaz que define la vista de la entidad 'Usuario'.
 */
public interface VistaUsuario {
    /**
     * Método para lanzar la vista del usuario.
     */
    void lanzar();
    /**
     * Establece el presentador asociado a la vista.
     *
     * @param presentador Presentador que interactuará con la vista.
     * @throws Exception Si hay problemas al establecer el presentador.
     */
    void setPresentador(PresentadorUsuario presentador) throws Exception;
    /**
     * Obtiene la información del usuario desde la vista.
     *
     * @return Objeto UsuarioDTO con la información del usuario.
     */
    UsuarioDTO getUsuario();
}
