package med.voll.API.infra.exception.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.API.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component/* esta classe esta sendo gerenciada pelo o spring com isso permite injetar/
instanciar outras classes*/
public class SecurityFilter extends OncePerRequestFilter {/*classe do spring
    onde o spring garante que o filter vai ser execultada uma vez por requisição*/

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //recuperar o token, atravez do cabeçalho de autenticação
        var token = recuperarToken(request);

        //validação, exibindo qual usuario disparou a solicitação de autenticação
        if (token != null) {//o token existe?
            var subject = tokenService.getSubject(token);
            //recuperando o usuario completo do banco de dados para autenticalo posteriormente
            var usuario = usuarioRepository.findByLogin(subject);
            //agora falando pro spring autenticar o usuario nesta requisição
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            //verificando se está autorizado, setando um objeto autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //System.out.println(subject);

        //System.out.println("Token "+tokenJWT);
        //checando se o token esta valido e pegando o subject para saber quem é o usuario
        //filterChain cadeia de filtros da aplicação

        filterChain.doFilter(request, response);//para continuar a requisição

    }

    private String recuperarToken(HttpServletRequest request) {
        //pegando o token enviado pelo front end atravez do cabeçalho de autorização
        //cabeçalho Authorization responsavel por enviar o token
        var authorizationHeader = request.getHeader("Authorization");//Authorization
        //validação
        if (authorizationHeader != null){
            //para tirar o nome token informado no sysout.println
            return authorizationHeader.replace("Bearer ", "");
        }
        //agora se for igual null retorne null
        return null;
    }
}
