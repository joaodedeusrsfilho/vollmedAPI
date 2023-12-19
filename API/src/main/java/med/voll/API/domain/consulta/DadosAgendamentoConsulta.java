package med.voll.API.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.API.domain.medico.Especialidade;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        /*@JsonAlias, A anotação @JsonAlias serve para mapear “apelidos” alternativos para os
         campos que serão recebidos no JSON, sendo possível atribuir múltiplos alias:*/
        @JsonAlias({"medico_id", "id_medico"})//o nome tem que vir assim do front ou do app mobile
        Long idMedico,
        @NotNull
        @JsonAlias({"paciente_id","id_paciente"})
        Long idPaciente,
        @NotNull
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//personalizando a hora
        LocalDateTime data,

        String motivoCancelamento,

        Especialidade especialidade) {

       }
