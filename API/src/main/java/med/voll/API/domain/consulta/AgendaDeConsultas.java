package med.voll.API.domain.consulta;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import med.voll.API.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.API.domain.medico.Medico;
import med.voll.API.domain.medico.MedicoRepository;
import med.voll.API.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service/*spring estou declarando uma classe serviço no caso serviço
de agendamento de consultas ou seja é uma classe que executa as regras de negócio
e validações da aplicação*/
public class AgendaDeConsultas {
    @Autowired //instanciando
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadoresAgendamentoConsultas;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadorCancelamentoDeConsulta;

    public DadosRetornoDaConsulta agendar(DadosAgendamentoConsulta dados){
        /*Documentação
        a escola do médico é opcional, sendo que nesse caso o sistema deve escolher
        aleatoriamente algum medico disponível na data
        hora preenchida*/

        //validação para verificar se o id existe no banco de dados
        //se não existe esse id faça o seguinte
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("O id do paciente informado nao existe");
        }

        //verificar se existe o id do medico
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("O id do medico informado nao existe");
        }

        /*
        *
        * É UM PADRÃO DE PROJETO CHAMADO = DESIGN PATTERN STRATEGY
        * AQUI ESTAMOS APLICANDO DE UMA VEZ 3 DE 5 PRINCIPIOS DO SOLID
        *
        * O PRIMEIRO É O S = SINGLE RESPOSABILITY PRINCIPLE (PRINCIPIO DA RESPONSABILIDADE
        * ÚNICA)
        *
        * O SEGUNDO É O = O OPEN-CLOSED PRINCIPLE (PRINCÍPIO ABERTO E FECHADO)
        * ESSA CLASSE SERVICE ESTA FECHADA PARA MODIFICAÇÃO POREM ESTA ABERTA PARA
        * EXTENÇÃO POSSO ADCIONAR NOVOS VALIDADORES SEM MECHER NESSA CLASSE SERVICE
        *
        * O TERCEIRO É D = QUE É O DEPENDENCY INVERSION PRINCIPLE
        * (PRINCIPIO DA INVERSAO DE DEPENDENCIA)
        * PQ A NOSSA CLASSE SERVICE DEPENDEDE DE UMA ABSTRAÇÃO QUE É A INTERFACE
        *
        * aqui vai percorrer todos os validadores passando os dados para cada um deles
         * muito util esse metodo posso alterar os validadores incluindo ou excluido
         * as classes de validadores dentro do pacote validações por exemplo, mantendo
         * o forEach aqui intacto.*/
        validadoresAgendamentoConsultas.forEach(listar->listar.validar(dados));
        /*agora que todos os validadores passou sem erros, vamos pegar os dados do paciente
         atraves do id*/

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        /*o medico é opcional, então o sistema irá oferecer um medico aleatorio
        de acordo com a hora e data da consulta*/
        var medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Nao existe medico disponivel nessa data");
        }

        var consulta = new Consulta(null, medico,paciente, dados.data());

        consultaRepository.save(consulta);

        return new DadosRetornoDaConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        //verificar se tem o id do medico
        if(dados.idMedico() != null){
            //getReferenceByID para pegar os dados do banco de dados com base no id
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        //se o idmedico for igual a null, tem q vir a especialidade, se não sera lançado
        //uma exception
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade obrigatoria quando medico nao for escolhido!");
        }
        //se especialidade não estiver nula
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

    public void cancelar(DadosCancelamentoConsulta dados){
        //validar
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("O id informado na consulta nao existe");
        }

        validadorCancelamentoDeConsulta.forEach(validar->validar.validar(dados));
        /*apos passar por todos os validadores continua a execução*/

        /*a variavel consulta vai receber os dados da tabela consulta do DB*/
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());

    }

}
