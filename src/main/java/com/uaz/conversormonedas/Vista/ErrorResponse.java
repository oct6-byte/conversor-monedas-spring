package com.uaz.conversormonedas.Vista;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private final LocalDateTime fecha;
    private final int estado;
    private final String error;
    private final List<String> mensajes;

    public ErrorResponse(int estado, String error, List<String> mensajes) {
        this.fecha = LocalDateTime.now();
        this.estado = estado;
        this.error = error;
        this.mensajes = mensajes;
    }

    public LocalDateTime getFecha() { return fecha; }
    public int getEstado() { return estado; }
    public String getError() { return error; }
    public List<String> getMensajes() { return mensajes; }
}