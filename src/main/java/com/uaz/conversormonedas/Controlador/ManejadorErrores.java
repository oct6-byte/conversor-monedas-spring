package com.uaz.conversormonedas.Controlador;

import com.uaz.conversormonedas.Modelo.excepcion.RecursoNoEncontradoException;
import com.uaz.conversormonedas.Modelo.excepcion.ReglaNegocioException;
import com.uaz.conversormonedas.Vista.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> noEncontrado(RecursoNoEncontradoException ex) {
        return construir(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponse> reglaNegocio(ReglaNegocioException ex) {
        return construir(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex) {
        List<String> mensajes = new ArrayList<>();
        for (FieldError campo : ex.getBindingResult().getFieldErrors()) {
            mensajes.add(campo.getField() + ": " + campo.getDefaultMessage());
        }
        return construir(HttpStatus.BAD_REQUEST, mensajes);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> jsonInvalido(HttpMessageNotReadableException ex) {
        return construir(HttpStatus.BAD_REQUEST, List.of("El cuerpo de la petición no es un JSON válido"));
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus estado, List<String> mensajes) {
        ErrorResponse cuerpo = new ErrorResponse(estado.value(), estado.getReasonPhrase(), mensajes);
        return ResponseEntity.status(estado).body(cuerpo);
    }
}