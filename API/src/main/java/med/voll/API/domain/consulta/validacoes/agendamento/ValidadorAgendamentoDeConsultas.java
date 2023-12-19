package med.voll.API.domain.consulta.validacoes.agendamento;


import med.voll.API.domain.consulta.DadosAgendamentoConsulta;

//não precisa injetar anotation o spring carrega interface automaticamente
public interface ValidadorAgendamentoDeConsultas{

    /*Todos os metodos de uma interface são publicos por isso o metodo
    * validar é declarado somente com void*/
    void validar(DadosAgendamentoConsulta dados);
}
