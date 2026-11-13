package com.uaz.conversormonedas.Controlador;

import com.uaz.conversormonedas.Modelo.entity.TasaCambio;
import com.uaz.conversormonedas.Modelo.service.TasaCambioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasas")
public class TasaCambioController {

    private final TasaCambioService tasaCambioService;

    public TasaCambioController(TasaCambioService tasaCambioService) {
        this.tasaCambioService = tasaCambioService;
    }

    @GetMapping
    public List<TasaCambio> listar() {
        return tasaCambioService.listar();
    }

    @GetMapping("/{id}")
    public TasaCambio obtener(@PathVariable Long id) {
        return tasaCambioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<TasaCambio> crear(@Valid @RequestBody TasaCambio tasaCambio) {
        TasaCambio creada = tasaCambioService.crear(tasaCambio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public TasaCambio actualizar(@PathVariable Long id, @Valid @RequestBody TasaCambio tasaCambio) {
        return tasaCambioService.actualizar(id, tasaCambio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tasaCambioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}