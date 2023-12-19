package med.voll.API.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosCancelamentoConsulta(
        @JsonAlias({"consulta_id", "id_consulta"})
        Long idConsulta,

        String motivo
) {

}
