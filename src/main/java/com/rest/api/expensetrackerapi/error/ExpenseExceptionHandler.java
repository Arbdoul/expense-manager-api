package com.rest.api.expensetrackerapi.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExpenseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResoureNotFoundException.class)
    public ResponseEntity<ErrorObject> handleException(ResoureNotFoundException exc, WebRequest request){

        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(new Date());

        //return resonse entity
        return new ResponseEntity<ErrorObject>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(MethodArgumentTypeMismatchException exc, WebRequest request){

        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(new Date());

        //return resonse entity
        return new ResponseEntity<ErrorObject>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleException(Exception exc){

        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(new Date());

        //return resonse entity
        return new ResponseEntity<ErrorObject>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
       Map<String, Object> body = new HashMap<String, Object>();
       body.put("timestamp", new Date());
       body.put("statusCode", HttpStatus.BAD_REQUEST.value());

      List<String> errors = ex.getBindingResult()
                       .getFieldErrors()
                       .stream()
                       .map(x->x.getDefaultMessage())
                       .collect(Collectors.toList());
       body.put("message", errors);

       return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemAlreadyExitException.class)
    public ResponseEntity<ErrorObject> handleItemAlreadyExitException(ItemAlreadyExitException exc){

        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(new Date());

        //return resonse entity
        return new ResponseEntity<ErrorObject>(error, HttpStatus.CONFLICT);
    }
}
