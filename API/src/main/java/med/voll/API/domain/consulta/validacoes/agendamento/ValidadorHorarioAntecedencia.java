package med.voll.API.domain.consulta.validacoes.agendamento;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.DadosAgendamentoConsulta;
import med.voll.API.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaAgendamento")//é um componente generico mas, poderia ser @Service
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas {
    /*validar horario da consulta lembrando que cada agendamento de consulta
    * tem q ser no minimo 30 minutos antes da consulta*/

    public void validar(DadosAgendamentoConsulta dados){
        //data
        var data = dados.data();
        //horario local atual
        var agora = LocalDateTime.now();
        //diferenca entre horario do agendamento e horario local atual
        var diferencaEmMinutos = Duration.between(agora, data).toMinutes();
        //validação
        if(diferencaEmMinutos<30){
            throw new ValidacaoException("Error, horario de agendamento tem que ser de no minumo 30 minutos da consulta");
        }
    }
}
