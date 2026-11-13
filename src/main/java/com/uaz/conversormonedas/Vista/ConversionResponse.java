package com.uaz.conversormonedas.Vista;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Desglose del resultado de una conversión.
 */
public class ConversionResponse {

    private final BigDecimal montoOriginal;
    private final String monedaOrigen;
    private final String monedaDestino;
    private final BigDecimal tasaAplicada;
    private final String tipoTasa;
    private final LocalDate fechaVigenciaTasa;
    private final BigDecimal resultado;
    private final String detalle;

    public ConversionResponse(BigDecimal montoOriginal, String monedaOrigen, String monedaDestino,
                              BigDecimal tasaAplicada, String tipoTasa, LocalDate fechaVigenciaTasa,
                              BigDecimal resultado, String detalle) {
        this.montoOriginal = montoOriginal;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tasaAplicada = tasaAplicada;
        this.tipoTasa = tipoTasa;
        this.fechaVigenciaTasa = fechaVigenciaTasa;
        this.resultado = resultado;
        this.detalle = detalle;
    }

    public BigDecimal getMontoOriginal() { return montoOriginal; }
    public String getMonedaOrigen() { return monedaOrigen; }
    public String getMonedaDestino() { return monedaDestino; }
    public BigDecimal getTasaAplicada() { return tasaAplicada; }
    public String getTipoTasa() { return tipoTasa; }
    public LocalDate getFechaVigenciaTasa() { return fechaVigenciaTasa; }
    public BigDecimal getResultado() { return resultado; }
    public String getDetalle() { return detalle; }
}
