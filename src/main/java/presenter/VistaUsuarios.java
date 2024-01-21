package presenter;

import entity.UsuarioDTO;


import java.util.List;

public interface VistaUsuarios extends VistaUsuario{
    /**
     * Establece la lista de usuarios en la vista.
     *
     * @param listaUsuarios Lista de objetos UsuarioDTO que representan los usuarios a mostrar.
     */
    void setUsuarios(List<UsuarioDTO> listaUsuarios);
}
