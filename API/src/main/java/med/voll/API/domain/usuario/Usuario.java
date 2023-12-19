package med.voll.API.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.RoleList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")//entidade JPA
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

/*para o spring saber que os campos da classe Usuario são os campos do banco de dados
* da tabela usuarios precisa implementar a interface UserDetails*/
public class Usuario implements UserDetails {//interface para o spring security
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//chave primaria
    private String login;
    private String senha;

    /*se o seu projeto tiver um controle de permissão de acesso para usuários diferentes,
    * aqui neste projeto não teremos controle de perfil mas temos que devolver um
    * objeto valido para o spring*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));//perfil fixo
    }

    @Override
    public String getPassword() {

        return this.senha;//informando para o spring
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
