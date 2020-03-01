package de.demo.plangenerator.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Handlers for exceptions that deserve to be mapped to dedicated response format.
 *
 * I have played with it a bit, but decided that this time I like Spring defaults better.
 * I keep it just for documenting my usual way of addressing the error handling at API level.
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {
//    @ExceptionHandler({
//            HttpMediaTypeNotSupportedException.class,
//            HttpMessageConversionException.class
//    })
//    public ResponseEntity handleBadRequests(Exception e) {
//        Map<String, Object> simpleErrorBody = new HashMap<>();
//        simpleErrorBody.put("message", e.getMessage());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(simpleErrorBody);
//    }
}
