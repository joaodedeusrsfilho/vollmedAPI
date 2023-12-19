package med.voll.API.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.API.domain.medico.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("medicos")//quando tiver uma requisição para a url medicos
/*coloquei essa anotação aqui para que dessa
forma todos os metodos do controller paciente sejam restritos pelo bearer-key
assim será preciso fazer login enviando o token pelo spring doc*/
@SecurityRequirement(name = "bearer-key")
public class MedicoController{
    @Autowired//olha springboot é vc que vai instanciar o repository ok (injeção de dependencia)
    private MedicoRepository medicoRepository;//repository é um objeto que isola objetos ou
    //entidates do dominio do codigo que acessa o bando de dados

    @PostMapping //aqui estou informando  o seguinte, Spring se chegar uma requisão
                // do tipo POST para a URL medicos é para você chamar o metodo cadastrar
                // da classe MedicoController
    @Transactional//como é um metodo de escrita no banco de dados ou seja vai fazer um insert de dados, precisa declarar a transaction
    //@Valid para pedir pro spring se integrar com o validation para validar os dados do DTO
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriComponentsBuilder){
        //criando objeto medico
        var medico = new Medico(dadosCadastroMedico);
        //salvando medico
        medicoRepository.save(medico);
        //criando uri que vai informar a url do local que foi salvo
        var uri = uriComponentsBuilder.path("{id}").buildAndExpand(medico.getId()).toUri();
        //retornando o ResponseEntity, retornando o endereco URI e o corpo do dado cadastrado
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }
    @GetMapping
    //o metodo List vai devolver somente o que queremos da API para o frontEnd.
    //vamos usar paginação no metodo listar

    /*public List<DadosListagemMedico> listar(Pageable paginacao){
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    }*/

    /*por padrao a pagina é mostrar 20 registros em ordem de inserção.
     * para mudar esse padão usamos a anotation @PageableDefault onde podemos colocar
     * os parametros (size=10, sort={"nome"}) por exemplo. Mas só vai funcionar caso os
     * os parametros de ordenação não tenham cido especificados na url*/
    public ResponseEntity<Page<DadosListagemMedico>>listar(@PageableDefault(size = 10,sort = {"nome"})Pageable paginacao){
        var page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok().body(page);
    }

    @PutMapping //atualizar
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        /*buscando dados ja existentes no banco de dados pela referencia do ID*/
        var medico = medicoRepository.getReferenceById(dados.id());
        //pronto a variavel medico ja possui os dados atuais no BD.
        //agora vamos atualizar as informações que chegaram por id
        medico.atualizarDadosMedico(dados);
        //quando a jpa percebe que aconteceu uma transactional ela automaticamente
        //faz a atualização dos dados.
        return ResponseEntity.ok().body(new DadosDetalhamentoMedico(medico));

    }

    @DeleteMapping("/{id}")//"/{id}" paramentro dinamico de numero, pegando o
                           // id direto na url do browser
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){//@PatchVariable informando que vai pegar a url {id}
        /*agora vamos carregar a entidade do banco de dados, depois inativa-lá setar
        * aquele atributo ativo de true para false, e disparar o update no DB*/

        var medico = medicoRepository.getReferenceById(id);//recuperando do DB.
        /*vamos fazer uma exclusão logica ou seja não vamos excluir de fato mas,
         * vamos marcar o dado como inativo, para que ele não seja exibido para o usuário*/
        //agora vamos criar um metodo para excluir
        medico.excluirAtivoForFalse();//vai setar o atributo ativo de true para false

        return ResponseEntity.noContent().build();/* vai retornar status 204, sem
        conteudo. chama o metodo build para que ele construa o responseEntity*/
    }

    //retornando um medico especifico
    @GetMapping("/{id}")//pegando id
    //não vai ter transactional
    public ResponseEntity detalhar(@PathVariable Long id){//lendo o id da url
        //criando objeto
        var medico = medicoRepository.getReferenceById(id);//pegando medido pelo seu id
        //retornando o corpo da responseEntity criada
        return ResponseEntity.ok().body(new DadosDetalhamentoMedico(medico));
    }


}
