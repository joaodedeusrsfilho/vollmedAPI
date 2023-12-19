package med.voll.API.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.API.domain.consulta.AgendaDeConsultas;
import med.voll.API.domain.consulta.ConsultaRepository;
import med.voll.API.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
/*coloquei essa anotação aqui para que dessa
forma todos os metodos do controller paciente sejam restritos pelo bearer-key
assim será preciso fazer login enviando o token pelo spring doc*/
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta){
        //System.out.println(dadosAgendamentoConsulta);
        var AgendaConsulta = agendaDeConsultas.agendar(dadosAgendamentoConsulta);
        return ResponseEntity.ok(AgendaConsulta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var consulta = consultaRepository.getReferenceById(id);
        consultaRepository.delete(consulta);
        return ResponseEntity.noContent().build();
    }

}