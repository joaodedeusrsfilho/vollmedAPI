package med.voll.API.domain;

public class ValidacaoException extends RuntimeException{

    public ValidacaoException(String mensagemError){

        super(mensagemError);
    }
}
