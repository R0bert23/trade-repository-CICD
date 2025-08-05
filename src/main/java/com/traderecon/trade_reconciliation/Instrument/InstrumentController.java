package com.traderecon.trade_reconciliation.Instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    // GET ALL
    @GetMapping
    public List<Instrument> getAll() {
        return instrumentService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Instrument> getById(@PathVariable Long id) {
        Optional<Instrument> instrument = instrumentService.getById(id);
        return instrument.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Instrument create(@RequestBody Instrument instrument) {
        return instrumentService.save(instrument);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Instrument> update(@PathVariable Long id, @RequestBody Instrument instrumentDetails) {
        Optional<Instrument> updated = instrumentService.update(id, instrumentDetails);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = instrumentService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
