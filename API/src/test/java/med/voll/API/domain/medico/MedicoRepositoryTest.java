package med.voll.API.domain.medico;

import med.voll.API.domain.consulta.Consulta;
import med.voll.API.domain.endereco.DadosEndereco;
import med.voll.API.domain.paciente.DadosCadastroPaciente;
import med.voll.API.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest//testa a interface repository
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//testar DB da aplicação
    @ActiveProfiles("test")//nome da copia do arquivo application.propeties


class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager testEntityManager;/*EntityManager utilizados especificamente
    para testes automatizados*/

        @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado não esta disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {

        var proximaSegundaAs10 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).minusMinutes(0);
        var medico = cadastrarMedico("Junior", "jr@gmail.com","123456",Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Junior", "junior@gmail.com", "0000000054");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
            assertThat(medicoLivre).isNull();

    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data){
        testEntityManager.persist(new Consulta(null,medico, paciente,data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
           /*passando os dados para o DTO dadosCastroMedico*/
            var medico = new Medico(dadosCadastroMedico(nome, email, crm, especialidade));
            testEntityManager.persist(medico);//metodo para salvar no bando de dados
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        /*passando os dados para o DTO dadosCastroPaciente*/
        var paciente = new Paciente(dadosCadastroPaciente(nome, email, cpf));
        testEntityManager.persist(paciente);//metodo para salvar no bando de dados
        return paciente;
    }

    /*Abaixo estão os metodos que criam os DTO que representam o cadastro de medico, paciente e endereço*/
    private DadosCadastroMedico dadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade){
        return new DadosCadastroMedico(
                nome,
                email,
                "99981402154",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosCadastroPaciente(String nome, String email, String cpf){
        return new DadosCadastroPaciente(
                nome,
                email,
                "99981402157",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "Rua aaa",
                "Bairro",
                "65727000",
                "Trizidela",
                "MA",
                null,
                null
        );
    }

}