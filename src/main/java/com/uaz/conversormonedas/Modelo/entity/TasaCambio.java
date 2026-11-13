package com.uaz.conversormonedas.Modelo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tasa_cambio")
public class TasaCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La moneda de origen es obligatoria")
    @ManyToOne(optional = false)
    @JoinColumn(name = "moneda_origen_id", nullable = false)
    private Moneda monedaOrigen;

    @NotNull(message = "La moneda de destino es obligatoria")
    @ManyToOne(optional = false)
    @JoinColumn(name = "moneda_destino_id", nullable = false)
    private Moneda monedaDestino;

    @NotNull(message = "La tasa es obligatoria")
    @Positive(message = "La tasa debe ser mayor que 0")
    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal tasa;

    @NotNull(message = "La fecha de vigencia es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaVigencia;

    @NotNull(message = "Debe indicar si la tasa está activa")
    @Column(nullable = false)
    private Boolean activa = true;

    public TasaCambio() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Moneda getMonedaOrigen() { return monedaOrigen; }
    public void setMonedaOrigen(Moneda monedaOrigen) { this.monedaOrigen = monedaOrigen; }

    public Moneda getMonedaDestino() { return monedaDestino; }
    public void setMonedaDestino(Moneda monedaDestino) { this.monedaDestino = monedaDestino; }

    public BigDecimal getTasa() { return tasa; }
    public void setTasa(BigDecimal tasa) { this.tasa = tasa; }

    public LocalDate getFechaVigencia() { return fechaVigencia; }
    public void setFechaVigencia(LocalDate fechaVigencia) { this.fechaVigencia = fechaVigencia; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}