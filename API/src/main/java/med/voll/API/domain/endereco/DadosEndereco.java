package med.voll.API.domain.endereco;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;

public record DadosEndereco(//que vai receber os dados vindo do JSON
        @NotBlank//Beans Validations
        String logradouro,
        @NotBlank
        String bairro,
        @NotBlank
        @Pattern(regexp = "\\d{8}")//expressão regular, 8 digitos obrigatório
        String cep,
        @NotBlank
        String cidade,
        @NotBlank
        String uf,
        //complemento é opcional
        String complemento,
        //numero é opcional
        String numero) {

}
