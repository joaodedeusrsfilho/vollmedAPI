package med.voll.API.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import med.voll.API.domain.usuario.DadosAutenticacao;
import med.voll.API.domain.usuario.Usuario;
import med.voll.API.infra.exception.security.DadosTokenJWT;
import med.voll.API.infra.exception.security.TokenService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired//para pedir pro spring injetar esse paramentro

    /*classe AuthenticationManager classe responsavel por disparar o processo de
    * autenticação do usuario*/
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    //classe q tem os metodos criação do token, getsubject, tempo de validade do token, senha da API, nome da API

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        try {
            /*a seguir a variavel token é criada para receber os dados vindo do DTO
            * porem esses dados são passado para a variavel token atraves do DTO do
            * proprio spring chamado por UsernamePasswordAuthenticationToken    */
            var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());

            //devolvendo um objeto que representa um usuario autenticado no sistema
            var authenticationToken = authenticationManager.authenticate(token);

            //Criando tokenJWT, fazendo um cast para a nossa classe Usuario
            var tokenJWT = tokenService.gerarToken((Usuario) authenticationToken.getPrincipal());

            //criando um DTO DadosTokenJWT para enviar na resposta o token gerado
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}