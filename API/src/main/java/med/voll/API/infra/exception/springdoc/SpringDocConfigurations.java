package med.voll.API.infra.exception.springdoc;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//classe de configurações do spring doc
//essa classe é necessaria para que o token possa ser informado na interface do
//swagger UI
public class SpringDocConfigurations {
    /*aqui está sendo exposto um objeto do tipo OpenAPI, esse objeto vai ser carregado
    * pelo o spring doc e vai seguir as configurações que foram configuradas aqui no
    * objeto*/

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .components(new Components()
                            .addSecuritySchemes("bearer-key",
                                    new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")))
                    .info(new Info()
                            .title("Voll.med API")
                            .description("API Rest da aplicação Voll.med, contendo as funcionalidades de CRUD de médicos e de pacientes, além de agendamento e cancelamento de consultas")
                            .contact(new Contact()
                                    .name("Email: do Time Backend")
                                    .email("backend@voll.med"))
                            .license(new License()
                                    .name("Licença: Apache 2.0")
                                    .url("http://voll.med/api/licenca")));
        }
    }
    /*E, por fim, precisará adicionar a seguinte anotação em cima das classes
     PacienteController, MedicoControllereConsultaController`:*/

