package com.company.error;

import com.company.model.dto.ServiceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public
    @ResponseBody
    ServiceResponseDTO generalServerError(Throwable exception) {
        log.error("Request raised at " + exception.getClass().getSimpleName(), exception);
        return ServiceResponseDTO.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(exception.toString()).build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public
    @ResponseBody
    ServiceResponseDTO illegalArgument(Throwable exception) {
        log.error("Request raised at " + exception.getClass().getSimpleName(), exception);
        return ServiceResponseDTO.builder().code(HttpStatus.NOT_ACCEPTABLE.value()).message(exception.toString()).build();
    }
}
