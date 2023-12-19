package med.voll.API.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.API.domain.endereco.DadosEndereco;

//class DTO
public record DadosCadastroMedico(
        @NotBlank//Campo obrigatorio e verificar se o dado vindo do banco de dados esta vazio ou nulo
        String nome,
        @NotBlank//Beans Validations
        @Email//verifica se o formato é de e-mail
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")//expressão regular de 4 à 6 digitos
        String crm,
        @NotNull//campo ENUM, @NotBlank só para strings obrigadorio não pode
        // ser nulo nem vazio
        Especialidade especialidade,
        @NotNull
        @Valid //validar esse DTO e tb é para validar outro DTO chamado DadosEndereço
        // que está dentro do DTO DadosCadastroMedico
        DadosEndereco endereco) {
}
