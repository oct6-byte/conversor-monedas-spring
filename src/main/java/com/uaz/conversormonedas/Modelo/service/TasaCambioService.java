package com.uaz.conversormonedas.Modelo.service;

import com.uaz.conversormonedas.Modelo.entity.Moneda;
import com.uaz.conversormonedas.Modelo.entity.TasaCambio;
import com.uaz.conversormonedas.Modelo.excepcion.RecursoNoEncontradoException;
import com.uaz.conversormonedas.Modelo.excepcion.ReglaNegocioException;
import com.uaz.conversormonedas.Modelo.repository.TasaCambioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TasaCambioService {

    private final TasaCambioRepository tasaCambioRepository;
    private final MonedaService monedaService;

    public TasaCambioService(TasaCambioRepository tasaCambioRepository, MonedaService monedaService) {
        this.tasaCambioRepository = tasaCambioRepository;
        this.monedaService = monedaService;
    }

    public List<TasaCambio> listar() {
        return tasaCambioRepository.findAll();
    }

    public TasaCambio buscarPorId(Long id) {
        return tasaCambioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la tasa de cambio con id " + id));
    }

    @Transactional
    public TasaCambio crear(TasaCambio datos) {
        Moneda origen = obtenerMoneda(datos.getMonedaOrigen(), "origen");
        Moneda destino = obtenerMoneda(datos.getMonedaDestino(), "destino");
        validarMonedasDistintas(origen, destino);

        if (tasaCambioRepository.existsByMonedaOrigenAndMonedaDestinoAndFechaVigencia(
                origen, destino, datos.getFechaVigencia())) {throw new ReglaNegocioException("Ya existe una tasa " + origen.getCodigo() + " -> " + destino.getCodigo() + " con fecha de vigencia " + datos.getFechaVigencia());
        }

        TasaCambio nueva = new TasaCambio();
        nueva.setMonedaOrigen(origen);
        nueva.setMonedaDestino(destino);
        nueva.setTasa(datos.getTasa());
        nueva.setFechaVigencia(datos.getFechaVigencia());
        nueva.setActiva(datos.getActiva());
        return tasaCambioRepository.save(nueva);
    }

    @Transactional
    public TasaCambio actualizar(Long id, TasaCambio datos) {
        TasaCambio existente = buscarPorId(id);
        Moneda origen = obtenerMoneda(datos.getMonedaOrigen(), "origen");
        Moneda destino = obtenerMoneda(datos.getMonedaDestino(), "destino");
        validarMonedasDistintas(origen, destino);

        if (tasaCambioRepository.existsByMonedaOrigenAndMonedaDestinoAndFechaVigenciaAndIdNot(origen, destino, datos.getFechaVigencia(), id)) {throw new ReglaNegocioException("Ya existe otra tasa " + origen.getCodigo() + " -> " + destino.getCodigo() + " con fecha de vigencia " + datos.getFechaVigencia());
        }

        existente.setMonedaOrigen(origen);
        existente.setMonedaDestino(destino);
        existente.setTasa(datos.getTasa());
        existente.setFechaVigencia(datos.getFechaVigencia());
        existente.setActiva(datos.getActiva());
        return tasaCambioRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        TasaCambio existente = buscarPorId(id);
        tasaCambioRepository.delete(existente);
    }

    private Moneda obtenerMoneda(Moneda moneda, String tipo) {
        if (moneda.getId() == null) {
            throw new ReglaNegocioException("Debe indicar el id de la moneda de " + tipo);
        }
        return monedaService.buscarPorId(moneda.getId());
    }

    private void validarMonedasDistintas(Moneda origen, Moneda destino) {
        if (origen.getId().equals(destino.getId())) {
            throw new ReglaNegocioException("La moneda de origen y la de destino deben ser distintas");
        }
    }
}