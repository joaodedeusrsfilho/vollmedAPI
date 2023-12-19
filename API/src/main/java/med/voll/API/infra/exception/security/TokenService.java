package med.voll.API.infra.exception.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.API.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    //essa questão de senhas e dados sensiveis a gente chama de variaveis de ambiente
    @Value("${api.secret.token.secret}")/*senha do token*/
    private String secret;
    //nome da aplicação responsavel pelo o token
    private static final String ISSUER = "API Voll.med";

    /*metodo responsavel pela geração do token na API*/
    public String gerarToken(Usuario usuario) {
        //System.out.println(secret);
        //agora vamos utilizar a biblioteca que adicionamos no projeto
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    //JWT.create(), possue varios metodos staticos conhecidos por encadeados

                    //aqui é para identificar qual a aplicação que é dona do token
                    .withIssuer(ISSUER)
                    //qual usuario esta enviando o login
                    .withSubject(usuario.getLogin())
                    //.withClaim("id", usuario.getId())//pode chamar x vezes
                    //withExpiresAt = tempo para expirar o token
                    .withExpiresAt(dataExpiracao())
                    //sign para realizar a assinatura do token
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }
    //verificar se o token esta valido e devolver o usuario que esta dentro do token

    //Instante JAVA 8 API de datas
    private Instant dataExpiracao() {
        /*data atual e adicionar 2 horas de validade ao token
         * ZoneOffset.of para passar qual vai ser o time zone (fuso horario)*/
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)//exigir o algoritmo para verificação
                    .withIssuer(ISSUER)//verificando o nome da aplicação
                    .build()
                    .verify(tokenJWT)//verificar se o token esta valido
                    .getSubject();//pegando o subject login do usuário
        } catch (JWTVerificationException exception){
            throw new RuntimeException("TokenJWT invalido ou expirado");
        }
    }
}
