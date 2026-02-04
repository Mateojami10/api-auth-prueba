package uce.edu.web.api.auth.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.auth.domain.Usuario;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Usuario> {
    
    public Usuario findByCredentials(String nombre, String password) {
        return find("nombre = ?1 and password = ?2", nombre, password).firstResult();
    }
    
    public Usuario findByNombre(String nombre) {
        return find("nombre", nombre).firstResult();
    }
}
