package com.cursojava.cursoSpring.dao;

import com.cursojava.cursoSpring.models.Usuario;
import java.util.List;

public interface UsuarioDao {
    //! Una interface es un archivo en el que indicamos las funciones que debería tener una clase.
    //? Si una clase implementa una interface, está obligada a utilizar esas funciones, si no, no es compilada.

    List<Usuario> obtenerUsuarios();

    void eliminarUsuario(String email);

    void registrarUsuario(Usuario usuarioData);

    Usuario userVerifyByCredentials(Usuario usuarioData);
}
