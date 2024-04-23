package com.cursojava.cursoSpring.controllers;


import com.cursojava.cursoSpring.models.Usuario;
import com.cursojava.cursoSpring.dao.UsuarioDao;
import com.cursojava.cursoSpring.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> login(@RequestBody Usuario usuarioData) {
        try {
            Usuario usuarioDatabase = usuarioDao.userVerifyByCredentials(usuarioData);

            if (usuarioDatabase != null) {
                String TOKEN = jwtUtil.create(usuarioDatabase.getEmail(), usuarioDatabase.getNombre());
                System.out.println("TOKEN: " + TOKEN);

                // Devuelve un objeto JSON con la propiedad "token"
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("token", TOKEN);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(responseMap);
            } else {
                throw new RuntimeException();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }

}
