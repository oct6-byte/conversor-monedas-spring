package com.uaz.conversormonedas.Controlador;

import com.uaz.conversormonedas.Modelo.service.ConversionService;
import com.uaz.conversormonedas.Vista.ConversionRequest;
import com.uaz.conversormonedas.Vista.ConversionResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Recibe la petición de conversión y delega toda la lógica a ConversionService.
 */
@RestController
@RequestMapping("/api/conversiones")
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping
    public ConversionResponse convertir(@Valid @RequestBody ConversionRequest solicitud) {
        return conversionService.convertir(solicitud.getMonto(), solicitud.getOrigen(), solicitud.getDestino());
    }
}
