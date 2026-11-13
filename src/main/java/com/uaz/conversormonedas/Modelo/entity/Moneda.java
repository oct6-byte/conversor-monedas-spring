package com.uaz.conversormonedas.Modelo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.uaz.conversormonedas.Modelo.repository.TasaCambioRepository;
@Entity
@Table(name = "moneda")
public class Moneda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^[A-Z]{3}$", message = "El código debe tener exactamente 3 letras mayúsculas (ej. USD)")
    @Column(nullable = false, unique = true, length = 3)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El símbolo es obligatorio")
    @Size(max = 5, message = "El símbolo no puede tener más de 5 caracteres")
    @Column(nullable = false, length = 5)
    private String simbolo;

    @NotNull(message = "Debe indicar si la moneda está activa")
    @Column(nullable = false)
    private Boolean activa = true;

    public Moneda() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSimbolo() { return simbolo; }
    public void setSimbolo(String simbolo) { this.simbolo = simbolo; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}