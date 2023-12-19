package med.voll.API.domain.consulta.validacoes.agendamento;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.DadosAgendamentoConsulta;
import med.voll.API.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component//é um componente generico mas, poderia ser @Service
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsultas {

    public void validar(DadosAgendamentoConsulta dados){
        /*Documentação
        * o horario de funcionamento da clinica é de seg a sab das 07 as 19 */
        //pegar a data
        var data = dados.data();
        //verificar se é domingo
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        //clinica antes de abrir
        var clinicaAntesDeAbrir = data.getHour()<7;
        //clinica depois do horario de encerramento
        var clinicaDepoisDeFechar = data.getHour()>18;
        //verificar data e horario de funcionamento
        if(domingo||clinicaAntesDeAbrir||clinicaDepoisDeFechar){
        throw new ValidacaoException("Horario de funcionamento é de seg-sabado das 07 às 19");
        }
    }
}
