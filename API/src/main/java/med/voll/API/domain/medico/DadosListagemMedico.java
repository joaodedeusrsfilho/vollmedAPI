package med.voll.API.domain.medico;

import med.voll.API.domain.endereco.Endereco;

//somente os campos que quero devolver para o front end, vindo da API
//aqui não tem Validation pq são dados que ja foram validados anteriormente
public record DadosListagemMedico(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosListagemMedico(Medico medico){//construtor que vai receber dados do
        // tipo JPA Medico
            this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
