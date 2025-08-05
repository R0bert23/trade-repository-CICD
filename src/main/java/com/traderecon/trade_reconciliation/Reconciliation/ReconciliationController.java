package com.traderecon.trade_reconciliation.Reconciliation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;

    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @PostMapping("/start")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ReconciliationRun> startReconciliation() {
        ReconciliationRun run = reconciliationService.startReconciliation();
        return ResponseEntity.status(HttpStatus.CREATED).body(run); // 201 Created
    }

    @GetMapping("/differences/{runId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<ReconciliationDifference>> getDifferences(@PathVariable Long runId) {
        List<ReconciliationDifference> differences = reconciliationService.getDifferences(runId);
        return ResponseEntity.ok(differences); // 200 OK
    }

    @GetMapping("/status/{runId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReconciliationRun> getStatus(@PathVariable Long runId) {
        return reconciliationService.getStatus(runId)
                .map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404 Not Found
    }
}
