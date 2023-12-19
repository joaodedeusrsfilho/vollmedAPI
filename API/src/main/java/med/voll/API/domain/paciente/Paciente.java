package med.voll.API.domain.paciente;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.API.domain.endereco.Endereco;

//classe entidade JPA, as variaveis serão campos na tabela do DB.
@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor

public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//primari key

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded//as variaves dessa classe serão campos na tabela lá no DB
    private Endereco endereco;

    private Boolean ativo;

    /*construtor*/
    public Paciente(DadosCadastroPaciente dados) {//recebendo dados que chegaram via JSON
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }


    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.ativo() != null) {
            this.ativo = dados.ativo();
        }
        if(dados.nome()!=null){
            this.nome= dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone= dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void inativar(){//metodo para inativar um paciente no sistema.
        this.ativo = false;
    }



}