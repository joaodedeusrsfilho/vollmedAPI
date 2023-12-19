package med.voll.API.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosRetornoDaConsulta(
        /*não precisa validar os dados pois eles vão chegar via classe JPA*/
        Long id,
        Long idMedico,
        Long idPaciente,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")//personalizando a data
        LocalDateTime data,
        String motivo
) {
        public DadosRetornoDaConsulta(Consulta consulta) {
                this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData(),consulta.getMotivoCancelamento());
        }
}
