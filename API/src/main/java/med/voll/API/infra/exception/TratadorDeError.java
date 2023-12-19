package med.voll.API.infra.exception;//onde tem os codigos de infraestrutura

import jakarta.persistence.EntityNotFoundException;

import med.voll.API.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice/*infomando que esta classe é de tratamento de erro pro spring*/
public class TratadorDeError {

    @ExceptionHandler(EntityNotFoundException.class)/*Aqui o spring vai saber que quando for lançada uma exception do tipo
    EntityNotFoundException, ele vai chamar esse metodo aqui*/
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();/*e aqui é para devolver o error 404 = notFound*/

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException ex){
        var error = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(DtoError400::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DtoError400(String campos, String mensagem){
        public DtoError400(FieldError fieldError){

            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
