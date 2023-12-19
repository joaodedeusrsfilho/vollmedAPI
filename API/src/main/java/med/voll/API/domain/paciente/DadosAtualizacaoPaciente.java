package med.voll.API.domain.paciente;

import jakarta.validation.Valid;
import med.voll.API.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(

        Long id,
        Boolean ativo,
        String nome,
        String telefone,
        @Valid
        DadosEndereco endereco) {

}
