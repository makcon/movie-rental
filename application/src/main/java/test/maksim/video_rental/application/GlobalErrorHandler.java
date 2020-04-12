package test.maksim.video_rental.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.maksim.video_rental.domain.customer.exception.CustomerNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String globalServerDownHandler(Exception exception) {
        log.error("The server could not be reached due to error: ", exception);
        return "The server could not be reached. Try again later.";
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(CustomerNotFoundException exception) {
        log.error("{}", exception.getMessage());
        return exception.getMessage();
    }
}
