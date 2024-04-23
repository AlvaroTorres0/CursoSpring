package com.cursojava.cursoSpring.dao;

import com.cursojava.cursoSpring.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository //* Nos indica que es posible hacer una conexión a la base de datos.
@Transactional //* Es la manera en la que las queries de SQL van a ser ejecutadas.


//! DAO hace referencia a Data Access Object
//? En este paquete van todas las clases que van a hacer la conexión a la base de datos
//? Cada clase representa una tabla
public class UsuarioDaoImp implements UsuarioDao {
    @PersistenceContext //* Nos indica que vamos a hacer uso de la conexión a la base de datos.
    //! El EntityManager es una interfaz que nos permite hacer operaciones con la base de datos.
    private EntityManager entityManager;
    @Override
    public List<Usuario> obtenerUsuarios() {
        String queryGetAllUsers = "FROM Usuario";
        return entityManager.createQuery(queryGetAllUsers).getResultList();
    }

    public void eliminarUsuario2(String email) {
        try {
            String queryDeleteUser = "DELETE FROM Usuario u WHERE u.email = :email";
            entityManager.createQuery(queryDeleteUser)
                    .setParameter("email", email)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(String email){
        try {
            Usuario usuario = entityManager.find(Usuario.class, email);
            if (usuario != null) entityManager.remove(usuario);
            else throw new RuntimeException("El usuario con email " + email + " no existe");

        }catch (Exception e){
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public void registrarUsuario(Usuario usuarioData) {
        try{
            String hashedPassword = hashPassword(usuarioData.getPassword());
            usuarioData.setPassword(hashedPassword);
            entityManager.merge(usuarioData);

        }catch (Exception e){
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage());
        }
    }

    public String hashPassword(String password){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(10, 1024, 1, password);
    }

    @Override
    public Usuario userVerifyByCredentials(Usuario usuarioDataLoginForm) {
        try {
            Usuario usuarioDatabase = (Usuario) getUser(usuarioDataLoginForm.getEmail());

            if ((usuarioDatabase != null) && (validatePassword(usuarioDatabase.getPassword(), usuarioDataLoginForm.getPassword()))) {;
                return usuarioDatabase;
            }else{
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    //* Compara la contraseña del formulario con la contraseña hasheada de la base de datos
    public boolean validatePassword(String passwordUserDatabase, String passwordUserForm){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(passwordUserDatabase, passwordUserForm);
    }

    //* Obtiene el usuario de la base de datos
    public Object getUser(String email){
        try{
            String queryVerifyCredentials = "FROM Usuario WHERE email = :email";

            return entityManager.createQuery(queryVerifyCredentials)
                    .setParameter("email", email)
                    .getSingleResult();

        }catch(Exception e){
            return null;
        }

    }
}
