package med.voll.API.domain.consulta.validacoes.cancelamento;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.ConsultaRepository;

import med.voll.API.domain.consulta.DadosCancelamentoConsulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")/*PARA RENOMEAR O NOME DA CLASSE
EVITANDO ASSIM CLASSE COM NOMES IGUAIS NO SPRING*/
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        /*pegando todos os dados da consulta referente ao id passando pelo DTO*/
        var horarioDaConsulta = consultaRepository.getReferenceById(dados.idConsulta());
        /*pegando o horario atual da consulta ou seja o horario da execução do metodo validar*/
        var horarioAtual = LocalDateTime.now();
        /*calculando a diferença do horario da consulta e do horario da execução do metodo
        * validar*/
        var diferencaEmHoras = Duration.between(horarioDaConsulta.getData(), horarioAtual).toHours();

        //validação
        if(diferencaEmHoras < 24){
            throw new ValidacaoException("Consulta so pode ser cancelada com antecedencia de 24 horas");
        }
    }

}
