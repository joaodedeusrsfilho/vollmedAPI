package med.voll.API.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.API.domain.endereco.Endereco;

//classe entidade JPA

/*A anotação @Entity é utilizada para informar que uma classe também é uma entidade.
A partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo
 nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos.*/
@Entity(name = "Medico")//torna a class uma entidade JPA
@Table(name = "medicos")

//vamos usar o lombok para que ele gere os getter e setters e construtor
@Getter //gerar os getters
@NoArgsConstructor //gerar construtor sem argumento
@AllArgsConstructor //gerar construtor com todos os argumentos
@EqualsAndHashCode(of = "id") //equalsandhashcode em id

/*as variaves da classe medico seram campos na tabela medicos no DB.*/
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//chave primaria
    //os mesmos atributos do DTO record
    private String nome;
    private String email;

    private String telefone;

    private String crm;
    @Enumerated(EnumType.STRING)//aqui é um objeto do tipo ENUM. constantes.
    private Especialidade especialidade;

    @Embedded // @Embedded (Intregada) Endereço não sera criado uma tabela no banco de dados para fazer
    // os relacionamento mas, um Embeddable attribute,
    // para ele ficar em uma classe separa mais no banco de dados ele considera
    // que os campos da classe endereço fazem parte da mesma tabela, da tabela de medicos

    private Endereco endereco;//conforme a anotation @Embedded as variaveis desse objeto
    //serão campos na tabela medicos

    /*variavel ativo sera usada para determinar se um medico esta visivel
    ou não no sistema*/
    private boolean ativo;

    public Medico(DadosCadastroMedico dados) {/*passando os dados do JSON atraves do DTO
        para a entidade Medico*/
        this.ativo = true;//quando cadastrar ja deixa o atributo ativo em true
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());

    }

    /*metodo para desativar o status de um medico tornando ele entre excluido para o
    * sistemas entre aspas */
    public void excluirAtivoForFalse() {
        this.ativo = false;
    }

    public void atualizarDadosMedico(DadosAtualizacaoMedico dados) {
        //é para atualizar somente se o campo estiver vindo pelo JSON
        if(dados.nome()!= null){
            this.nome = dados.nome();
        }
        if(dados.telefone()!=null){
            this.telefone = dados.telefone();
        }
        if (dados.endereco()!=null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
        if(dados.ativo()!=null){
            this.ativo=dados.ativo();
        }
    }
}
