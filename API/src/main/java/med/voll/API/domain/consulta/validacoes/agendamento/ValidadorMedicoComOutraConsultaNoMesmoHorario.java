package med.voll.API.domain.consulta.validacoes.agendamento;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.ConsultaRepository;
import med.voll.API.domain.consulta.DadosAgendamentoConsulta;
import med.voll.API.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component//é um componente generico mas, poderia ser @Service
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsultas {
    @Autowired
    private ConsultaRepository repositoryConsulta;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoConsultaNoMesmoHorario = repositoryConsulta.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (medicoConsultaNoMesmoHorario){
            throw new ValidacaoException("Medico ja possui uma consulta neste mesmo horario");

        }
    }
}
