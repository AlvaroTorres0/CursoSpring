package com.cursojava.cursoSpring.controllers;

import com.cursojava.cursoSpring.dao.UsuarioDao;
import com.cursojava.cursoSpring.models.Usuario;
import com.cursojava.cursoSpring.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController

public class UsuarioController {

    //? Esta notación nos indica la inyección de dependencias
    //? Hace que UsuarioDaoImp cree un objeto y se guarde automáticamente dentro de la varible
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value ="/usuarios/obtener/{email}")
    public Usuario getUser(@PathVariable String email){
        Usuario usuario = new Usuario();
        usuario.setNombre("Karen Itxel");
        usuario.setApellidos("Medina Vargas");
        usuario.setEmail(email);
        usuario.setPassword("123456");

        return usuario;
    }

    @RequestMapping(value = "/usuarios/obtener/todos")
    public List<Usuario> getAllUsers(@RequestHeader(value = "Authorization") String token){
        String userEmail = jwtUtil.getKey(token);
        if(userEmail == null){
            return null;
        }
        return usuarioDao.obtenerUsuarios();
    }


    @RequestMapping(value = "/usuarios/eliminar/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String email){
        try {
            usuarioDao.eliminarUsuario(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/registrar", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@RequestBody Usuario usuarioData){
        try{
            usuarioDao.registrarUsuario(usuarioData);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Usuario registrado correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario: " + e.getMessage());

        }
    }

}
