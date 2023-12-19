package med.voll.API.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.API.domain.endereco.DadosEndereco;

//classe DTO Record

public record DadosCadastroPaciente(//recendo dados do JSON e validando eles.
        @NotBlank/*não pode ser em branco*/
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
        String cpf,
        @NotNull //porque não é um string e sim um CTO Record
        @Valid //valid para validar os dados do no DTO DadosEndereco
        DadosEndereco endereco) {

}
