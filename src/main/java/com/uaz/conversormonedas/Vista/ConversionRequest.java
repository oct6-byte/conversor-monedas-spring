package com.uaz.conversormonedas.Vista;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Datos que el cliente envía para solicitar una conversión.
 */
public class ConversionRequest {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor que 0")
    private BigDecimal monto;

    @NotBlank(message = "La moneda de origen es obligatoria")
    private String origen;

    @NotBlank(message = "La moneda de destino es obligatoria")
    private String destino;

    public ConversionRequest() {
    }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
}
