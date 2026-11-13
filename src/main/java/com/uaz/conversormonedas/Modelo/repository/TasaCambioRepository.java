package com.uaz.conversormonedas.Modelo.repository;

import com.uaz.conversormonedas.Modelo.entity.Moneda;
import com.uaz.conversormonedas.Modelo.entity.TasaCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TasaCambioRepository extends JpaRepository<TasaCambio, Long> {

    boolean existsByMonedaOrigenAndMonedaDestinoAndFechaVigencia(Moneda monedaOrigen, Moneda monedaDestino, LocalDate fechaVigencia);

    boolean existsByMonedaOrigenAndMonedaDestinoAndFechaVigenciaAndIdNot(Moneda monedaOrigen, Moneda monedaDestino, LocalDate fechaVigencia, Long id);

    boolean existsByMonedaOrigenOrMonedaDestino(Moneda monedaOrigen, Moneda monedaDestino);

    @Query("SELECT t FROM TasaCambio t " + "WHERE t.monedaOrigen = :origen AND t.monedaDestino = :destino " + "AND t.activa = true AND t.fechaVigencia <= :fecha " + "ORDER BY t.fechaVigencia DESC") List<TasaCambio> buscarVigentes(@Param("origen") Moneda origen, @Param("destino") Moneda destino, @Param("fecha") LocalDate fecha);
}