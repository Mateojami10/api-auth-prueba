package uce.edu.web.api.auth.interfaces;

import java.time.Instant;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.auth.domain.Usuario;
import uce.edu.web.api.auth.infraestructure.UserRepository;

@Path("/auth")
public class AuthResource {
    
    @Inject
    UserRepository userRepository;
    
    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(
            @QueryParam("user") String user,
            @QueryParam("password") String password) {

        // Validaciones
        if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Usuario y contraseña son requeridos\"}")
                    .build();
        }

        // Buscar el usuario en la base de datos comparando credenciales
        Usuario usuario = userRepository.findByCredentials(user, password);
        
        // Si el usuario no existe o las credenciales son incorrectas
        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Credenciales inválidas\"}")
                    .build();
        }
        
        // Obtener el rol del usuario desde la base de datos (acceso directo, sin getter)
        String role = usuario.rol;
        
        // Configuración del token
        String issuer = "matricula-auth";
        long ttl = 3600;
 
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttl);
 
        // Generar el JWT con el rol obtenido de la base de datos
        String jwt = Jwt.issuer(issuer)
                .subject(user)
                .groups(Set.of(role))     // roles: user / admin
                .issuedAt(now)
                .expiresAt(exp)
                .sign();
 
        TokenResponse tokenResponse = new TokenResponse(jwt, exp.getEpochSecond(), role);
        return Response.ok(tokenResponse).build();
    }

    public static class TokenResponse {
        public String accessToken;
        public long expiresAt;
        public String role;
 
        public TokenResponse() {}
        public TokenResponse(String accessToken, long expiresAt, String role) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
            this.role = role;
        }
    }
}
