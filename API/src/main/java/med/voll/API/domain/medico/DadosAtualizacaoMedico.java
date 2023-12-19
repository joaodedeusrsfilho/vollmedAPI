package med.voll.API.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.API.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull//não pode ser nulo ou seja é um campo obrigatorio
        Long id,//campo obrigatorio
        String nome,
        String telefone,

        Boolean ativo,
        DadosEndereco endereco) {

}
