package com.parkinglot.reservation.controller;

import com.parkinglot.reservation.dto.SlotRequest;
import com.parkinglot.reservation.model.Slot;
import com.parkinglot.reservation.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class SlotController {
    private final SlotService slotService;

    @PostMapping
    public ResponseEntity<Slot> createSlot(@Valid @RequestBody SlotRequest req) {
        Slot s = slotService.createSlot(req);
        return ResponseEntity.ok(s);
    }
}
