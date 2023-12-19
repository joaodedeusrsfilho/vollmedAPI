package med.voll.API.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService{/*classe UserDetailsService
    para autenticar o usuário*/

    @Autowired
    private UsuarioRepository repositoryUsuario;

    @Override
    /*toda vez que o usuário fizer login o spring vai chamar a classe AutenticacaoService
    * pois ela implemanta a classe UserDetailsService, e ele vai chamar o metodo a seguir
    * passando o username la do formulario de login*/
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositoryUsuario.findByLogin(username);/*este é o metodo
        que vai fazer a consulta do usuário la no banco de dados*/
    }
}

