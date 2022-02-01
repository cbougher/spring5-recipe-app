package guru.springframework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such recipe")
public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException(String s) {
        super(s);
    }
}
