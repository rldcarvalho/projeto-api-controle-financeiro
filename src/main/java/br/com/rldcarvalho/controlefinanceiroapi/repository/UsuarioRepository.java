package br.com.rldcarvalho.controlefinanceiroapi.repository;

import br.com.rldcarvalho.controlefinanceiroapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsuario(String usuario);

}
