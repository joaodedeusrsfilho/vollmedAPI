package med.voll.API.domain.medico;

import med.voll.API.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(
        /*não precisa validar os dados pois eles viram direto da JPA Medico*/
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco

) {
 public DadosDetalhamentoMedico(Medico dadosMedico) {/*pegando os dados direto
 da JPA Medico e passando para o DTO, para enviar via ResponseEntity*/
  this(dadosMedico.getNome(), dadosMedico.getEmail(),
          dadosMedico.getTelefone(), dadosMedico.getCrm(),
          dadosMedico.getEspecialidade(), dadosMedico.getEndereco());

 }
}
