package med.voll.API.domain.consulta.validacoes.agendamento;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.ConsultaRepository;
import med.voll.API.domain.consulta.DadosAgendamentoConsulta;
import med.voll.API.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component//é um componente generico mas, poderia ser @Service
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsultas {

    @Autowired
    private ConsultaRepository repositoryConsulta;

    public void validar(DadosAgendamentoConsulta dados){

        var horaInicial = dados.data().withHour(7);
        var horaFinal = dados.data().withHour(18);
        var temConsulta = repositoryConsulta.existsByPacienteIdAndDataBetween(dados.idPaciente(),horaInicial, horaFinal);
        //validação se sim
        if(temConsulta){
            throw new ValidacaoException("Paciente ja possui uma consulta agendada neste dia");
        }
    }
}
