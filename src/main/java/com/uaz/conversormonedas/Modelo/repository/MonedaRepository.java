package com.uaz.conversormonedas.Modelo.repository;

import com.uaz.conversormonedas.Modelo.entity.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    Optional<Moneda> findByCodigo(String codigo);
}