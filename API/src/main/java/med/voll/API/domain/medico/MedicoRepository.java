package med.voll.API.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    /*devolvendo um page de medico*/
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    //se o metodo não esta escrito em um padrão em ingles temos que usar a anotation @Query
    //utilizando JAVA 14 com 3 """ para organização = text block

    /*A anotation embaixo quer dizer, traga para mim todos os medicos ativos que são
    * dessa especialidade e que não são dessa data e que sejam exibidos de forma aleatoria
    * e com limite de exibição para apenas 1 valor ou (medico)*/
    @Query("""
            select m from Medico m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
                and
                c.motivoCancelamento is Null
            )
            order by Rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    /*consulta personalizada buscando somente o atributo do campo ativo do medico
    * que foi passado pelo id*/
    @Query("""
            select m.ativo from Medico m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);
}
