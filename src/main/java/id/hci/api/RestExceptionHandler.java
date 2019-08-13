package id.hci.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity handleError(String msg) {
        Map<String,Object> map = new HashMap<>();
        map.put("error", msg);
        map.put("status", false);

        return ResponseEntity
                .badRequest()
                .body(map);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity parseException(Exception e) {
        System.out.println("----> runtime error:" + e.getMessage());
        String msg = e.getMessage();
        return handleError(msg);
    }



    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleError(ex.getMessage());
    }
}
