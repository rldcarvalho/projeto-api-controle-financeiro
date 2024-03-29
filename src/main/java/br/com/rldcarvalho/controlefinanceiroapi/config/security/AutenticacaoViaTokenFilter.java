package br.com.rldcarvalho.controlefinanceiroapi.config.security;

import br.com.rldcarvalho.controlefinanceiroapi.model.Usuario;
import br.com.rldcarvalho.controlefinanceiroapi.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);

        boolean valido = tokenService.isTokenValido(token);

        if (valido){
            autenticarUsuario(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) {

        String username = tokenService.getUsuario(token);

        Usuario usuario = usuarioRepository.findByUsuario(username).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,
                null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7);
    }
}
