package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SIMRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SIMResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.SIMService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sims")
@RequiredArgsConstructor
public class SIMController {

    private final SIMService simService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SIMResponse createSIM(
            @Valid @RequestBody SIMRequest request) {

        return simService.createSIM(request);
    }

    @GetMapping
    public List<SIMResponse> getAllSIMs() {

        return simService.getAllSIMs();
    }

    @GetMapping("/{simId}")
    public SIMResponse getSIM(
            @PathVariable Long simId) {

        return simService.getSIM(simId);
    }

    @PutMapping("/{simId}")
    public SIMResponse updateSIM(
            @PathVariable Long simId,
            @Valid @RequestBody SIMRequest request) {

        return simService.updateSIM(
                simId,
                request
        );
    }

    @DeleteMapping("/{simId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSIM(
            @PathVariable Long simId) {

        simService.deleteSIM(simId);
    }
}