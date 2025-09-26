package com.parkinglot.reservation.controller;

import com.parkinglot.reservation.dto.FloorRequest;
import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/floors")
@RequiredArgsConstructor
public class FloorController {
    private final FloorService floorService;

    @PostMapping
    public ResponseEntity<Floor> createFloor(@Valid @RequestBody FloorRequest req) {
        Floor f = floorService.createFloor(req);
        return ResponseEntity.ok(f);
    }
}
