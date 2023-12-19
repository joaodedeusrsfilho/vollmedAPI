package med.voll.API.domain.consulta;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.API.domain.medico.Medico;
import med.voll.API.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")//coluna da tabela Consulta
    //fazendo referencia com a coluna id da tabela medico
    private Medico medico;//é relacionamento com as outras entidates Medico

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")//coluna da tabela Consulta fazendo refencia
    //com a coluna id da tabela Paciente
    private Paciente paciente;//Aqui é feito um relacionamento com a entidade Paciente

    private LocalDateTime data;

    private String motivoCancelamento;

    public Consulta(Object id, Medico medico, Paciente paciente, LocalDateTime data) {

    }
    public void cancelar(String motivo) {

        this.motivoCancelamento = motivo;
    }
}
