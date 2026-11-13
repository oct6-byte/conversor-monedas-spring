package com.uaz.conversormonedas.Modelo.service;

import com.uaz.conversormonedas.Modelo.entity.Moneda;
import com.uaz.conversormonedas.Modelo.excepcion.RecursoNoEncontradoException;
import com.uaz.conversormonedas.Modelo.excepcion.ReglaNegocioException;
import com.uaz.conversormonedas.Modelo.repository.MonedaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.uaz.conversormonedas.Modelo.repository.TasaCambioRepository;
@Service
public class MonedaService {

    private final MonedaRepository monedaRepository;
    private final TasaCambioRepository tasaCambioRepository;

    public MonedaService(MonedaRepository monedaRepository, TasaCambioRepository tasaCambioRepository) {
        this.monedaRepository = monedaRepository;
        this.tasaCambioRepository = tasaCambioRepository;
    }

    public List<Moneda> listar() {
        return monedaRepository.findAll();
    }

    public Moneda buscarPorId(Long id) {
        return monedaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la moneda con id " + id));
    }

    @Transactional
    public Moneda crear(Moneda moneda) {
        if (monedaRepository.existsByCodigo(moneda.getCodigo())) {
            throw new ReglaNegocioException("Ya existe una moneda con el código " + moneda.getCodigo());
        }
        moneda.setId(null);
        return monedaRepository.save(moneda);
    }

    @Transactional
    public Moneda actualizar(Long id, Moneda datos) {
        Moneda existente = buscarPorId(id);
        if (monedaRepository.existsByCodigoAndIdNot(datos.getCodigo(), id)) {
            throw new ReglaNegocioException("Ya existe otra moneda con el código " + datos.getCodigo());
        }
        existente.setCodigo(datos.getCodigo());
        existente.setNombre(datos.getNombre());
        existente.setSimbolo(datos.getSimbolo());
        existente.setActiva(datos.getActiva());
        return monedaRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Moneda existente = buscarPorId(id);
        if (tasaCambioRepository.existsByMonedaOrigenOrMonedaDestino(existente, existente)) {
            throw new ReglaNegocioException("No se puede eliminar la moneda " + existente.getCodigo() + " porque se usa en tasas de cambio. Puede desactivarla en su lugar.");
        }
        monedaRepository.delete(existente);
    }
}