package med.voll.API.domain.paciente;

import jakarta.validation.Valid;
import med.voll.API.domain.endereco.Endereco;

public record DadosListagemPaciente(
        /*não precisa validar os dados pois estes ja foram validados
        * quando foram inseridos no banco de dados*/
        Long id,
        String nome,
        String email,
        String cpf,
        @Valid
        Endereco endereco
        ) {

    /*o construtor abaixo tem este this diferente pois ele esta pegando os dados
    * da class JPA Paciente*/
    public DadosListagemPaciente(Paciente dados){
        this(dados.getId(), dados.getNome(), dados.getEmail(),
                dados.getCpf(),dados.getEndereco());
    }

}
