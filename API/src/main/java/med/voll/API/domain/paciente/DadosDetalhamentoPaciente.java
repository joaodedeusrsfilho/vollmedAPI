package med.voll.API.domain.paciente;

import med.voll.API.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(
        /*não precisa validar os dados pois eles vão ser carregados
        * diretamente pela JPA Paciente*/
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
){

    public DadosDetalhamentoPaciente (Paciente dadosPaciente){
        /*passando os dados da JPA paciente
        * direto para o DTO DadosDetalhamentoPaciente*/
    this(dadosPaciente.getNome(),
            dadosPaciente.getEmail(),
            dadosPaciente.getTelefone(),
            dadosPaciente.getCpf(),
            dadosPaciente.getEndereco());
    }
}

