package med.voll.API.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import med.voll.API.domain.paciente.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController//informando que é uma class Controller
@RequestMapping("pacientes")//mapear requisições para essa url
@SecurityRequirement(name = "bearer-key")
/*coloquei essa anotação aqui para que dessa
forma todos os metodos do controller paciente sejam restritos pelo bearer-key
assim será preciso fazer login enviando o token pelo spring doc*/
public class PacienteController {

    @Autowired //spring que vai instanciar o objeto
    private PacienteRepository pacienteRepository;

    @PostMapping//cadastrar salvar no DB
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente, UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente(dadosCadastroPaciente);//passando os dados
        pacienteRepository.save(paciente);//salvando no DB
        var uri = uriComponentsBuilder.path("/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping//metodo para listar pacientes
    public ResponseEntity<Page<DadosListagemPaciente>>listar(@PageableDefault(page = 0,size = 10,sort = {"nome"}) Pageable paginacao){
        var page = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok().body(page);
    }

    @PutMapping//atualizar
    @Transactional//transação de dados
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente){
        var paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atualizarInformacoes(dadosAtualizacaoPaciente);
        return ResponseEntity.ok().body(new DadosDetalhamentoPaciente(paciente));

    }

    @DeleteMapping("/{id}")//deletando de forma inativo
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativar();
        return ResponseEntity.noContent().build();/*sem conteudo e o build é para
        criar o responseEntity vazio*/

    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoPaciente(paciente));
    }

}
