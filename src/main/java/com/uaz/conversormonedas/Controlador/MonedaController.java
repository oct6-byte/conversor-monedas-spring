package com.uaz.conversormonedas.Controlador;
import com.uaz.conversormonedas.Modelo.entity.Moneda;
import com.uaz.conversormonedas.Modelo.service.MonedaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monedas")
public class MonedaController {

    private final MonedaService monedaService;

    public MonedaController(MonedaService monedaService) {
        this.monedaService = monedaService;
    }

    @GetMapping
    public List<Moneda> listar() {
        return monedaService.listar();
    }

    @GetMapping("/{id}")
    public Moneda obtener(@PathVariable Long id) {
        return monedaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Moneda> crear(@Valid @RequestBody Moneda moneda) {
        Moneda creada = monedaService.crear(moneda);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public Moneda actualizar(@PathVariable Long id, @Valid @RequestBody Moneda moneda) {
        return monedaService.actualizar(id, moneda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        monedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}