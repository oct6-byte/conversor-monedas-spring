package com.uaz.conversormonedas.Modelo.excepcion;

public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}