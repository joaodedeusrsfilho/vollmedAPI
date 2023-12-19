package med.voll.API.domain.consulta.validacoes.agendamento;

import med.voll.API.domain.ValidacaoException;
import med.voll.API.domain.consulta.DadosAgendamentoConsulta;
import med.voll.API.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import med.voll.API.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component//é um componente generico mas, poderia ser @Service
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsultas {
    @Autowired
    private MedicoRepository repositoryMedico;

    public void validar(DadosAgendamentoConsulta dados) {
        //escolha medico (opcional)
        if (dados.idMedico() == null) {
            //se for nulo ignora execulta um return só para sair do metodo
            return;
        }

        //medito esta ativo ?
        var medicoAtivo = repositoryMedico.findAtivoById(dados.idMedico());
        if(!medicoAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com medico excluido ");
        }

    }
}
