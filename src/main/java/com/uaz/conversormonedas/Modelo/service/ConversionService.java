package com.uaz.conversormonedas.Modelo.service;

import com.uaz.conversormonedas.Modelo.entity.Moneda;
import com.uaz.conversormonedas.Modelo.entity.TasaCambio;
import com.uaz.conversormonedas.Modelo.excepcion.RecursoNoEncontradoException;
import com.uaz.conversormonedas.Modelo.excepcion.ReglaNegocioException;
import com.uaz.conversormonedas.Modelo.repository.MonedaRepository;
import com.uaz.conversormonedas.Modelo.repository.TasaCambioRepository;
import com.uaz.conversormonedas.Vista.ConversionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Lógica de negocio de la conversión de monedas.
 * Usa los dos catálogos: Moneda y TasaCambio.
 */
@Service
public class ConversionService {

    private final MonedaRepository monedaRepository;
    private final TasaCambioRepository tasaCambioRepository;

    public ConversionService(MonedaRepository monedaRepository, TasaCambioRepository tasaCambioRepository) {
        this.monedaRepository = monedaRepository;
        this.tasaCambioRepository = tasaCambioRepository;
    }

    @Transactional(readOnly = true)
    public ConversionResponse convertir(BigDecimal monto, String codigoOrigen, String codigoDestino) {

        // 1. Validar el monto
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El monto debe ser mayor que 0");
        }

        // 2. Buscar y validar las monedas (catálogo Moneda)
        Moneda origen = obtenerMonedaActiva(codigoOrigen, "origen");
        Moneda destino = obtenerMonedaActiva(codigoDestino, "destino");
        if (origen.getId().equals(destino.getId())) {
            throw new ReglaNegocioException("La moneda de origen y la de destino deben ser distintas");
        }

        // 3. Buscar la tasa vigente (catálogo TasaCambio)
        LocalDate hoy = LocalDate.now();
        BigDecimal tasaAplicada;
        String tipoTasa;
        LocalDate fechaVigencia;

        List<TasaCambio> directas = tasaCambioRepository.buscarVigentes(origen, destino, hoy);
        if (!directas.isEmpty()) {
            TasaCambio tasa = directas.get(0);
            tasaAplicada = tasa.getTasa();
            tipoTasa = "DIRECTA";
            fechaVigencia = tasa.getFechaVigencia();
        } else {
            List<TasaCambio> inversas = tasaCambioRepository.buscarVigentes(destino, origen, hoy);
            if (inversas.isEmpty()) {
                throw new ReglaNegocioException("No existe una tasa de cambio vigente entre "
                        + origen.getCodigo() + " y " + destino.getCodigo());
            }
            TasaCambio tasa = inversas.get(0);
            tasaAplicada = BigDecimal.ONE.divide(tasa.getTasa(), 6, RoundingMode.HALF_UP);
            tipoTasa = "INVERSA (1 / " + tasa.getTasa().stripTrailingZeros().toPlainString() + ")";
            fechaVigencia = tasa.getFechaVigencia();
        }

        // 4. Calcular el resultado
        BigDecimal resultado = monto.multiply(tasaAplicada).setScale(2, RoundingMode.HALF_UP);

        // 5. Armar el desglose
        String detalle = monto.setScale(2, RoundingMode.HALF_UP) + " " + origen.getCodigo()
                + " x " + tasaAplicada.stripTrailingZeros().toPlainString()
                + " = " + resultado + " " + destino.getCodigo();

        return new ConversionResponse(monto, origen.getCodigo(), destino.getCodigo(),
                tasaAplicada, tipoTasa, fechaVigencia, resultado, detalle);
    }

    private Moneda obtenerMonedaActiva(String codigo, String tipo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ReglaNegocioException("Debe indicar la moneda de " + tipo);
        }
        String codigoNormalizado = codigo.trim().toUpperCase();
        Moneda moneda = monedaRepository.findByCodigo(codigoNormalizado)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la moneda con código " + codigoNormalizado));
        if (!moneda.getActiva()) {
            throw new ReglaNegocioException("La moneda " + codigoNormalizado + " está inactiva");
        }
        return moneda;
    }
}
