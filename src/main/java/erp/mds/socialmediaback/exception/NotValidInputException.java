package erp.mds.socialmediaback.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidInputException extends RuntimeException{

    public NotValidInputException(String input){
        super(String.format("%s Not valid input Value",input));
    }
}
