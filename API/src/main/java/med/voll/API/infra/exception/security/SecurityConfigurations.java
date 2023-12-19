package med.voll.API.infra.exception.security;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.flywaydb.core.api.migration.baseline.BaselineResolvedMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.reflect.Method;

//class de configuração
//indicando ao spring que vamos personalizar as configuração de segurança


@Configuration
@EnableWebSecurity//configurações de segurança
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;
    /* para devolver um objeto para o spring, ou objeto que eu posso
    injetar em algum controller*/
    @Bean
    /*Objeto SecurityFilterChain, usado para configurar coisas relacionados a processos
    de autenticação e tambem de autorização*/
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /*desabilitando a proteção contra ataques
        do tipo: Cross-Site Request Forgery , estamos desabilitando porque o proprio
        token que vamos utilizar ja protege contra esse tipo de ataque.
        com isso também desabilitamos o processo padrão do spring para autentificação*/
        return httpSecurity.csrf(csrf->csrf.disable())
                //Politica de criação é Stateless
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req->{
                    //permitir todos os acesso a pagina login
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    /*acessar a documentação da API*/
                    req.requestMatchers("/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**").permitAll();
                    //req.requestMatchers(HttpMethod.DELETE, "medicos").hasRole("ADMIN");
                    //req.requestMatchers(HttpMethod.DELETE, "pacientes").hasRole("ADMIN");
                    //e para as outras paginas somente se estiver autenticado o usuário
                    req.anyRequest().authenticated();
                })
                /*Medoto addFilterBefore, usado para determinar a ordem dos filtros utilizados
                * na linha abaixo esta da seguite forma, primeiro vai ser o filtro
                * securityFilter, e depois o filter do proprio string chamado de:
                * UsernamePasswordAuthenticationFilter.class*/
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean /*serve para exportar uma classe para o spring fazendo com que ele
    consiga carregá-la e realizar a sua injeção de dependencia em outras classes*/
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean/*ensinando o spring a usar o BCryptPasswordEncoder algoritmo de hash de senha*/
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


}






    /*processo de autenticação Stateless*/













