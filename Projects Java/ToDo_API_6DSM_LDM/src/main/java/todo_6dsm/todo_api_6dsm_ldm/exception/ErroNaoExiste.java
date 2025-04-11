package todo_6dsm.todo_api_6dsm_ldm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ErroNaoExiste extends RuntimeException {

    public ErroNaoExiste(String mensagem) {
        super(mensagem);
    }

}
