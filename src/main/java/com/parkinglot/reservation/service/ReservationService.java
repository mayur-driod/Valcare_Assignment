package com.parkinglot.reservation.service;

import com.parkinglot.reservation.dto.*;
import com.parkinglot.reservation.model.*;
import com.parkinglot.reservation.repository.*;
import com.parkinglot.reservation.exception.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;

    // 30 rupees for 4wheelers and 20 rupees for 2wheelers
    private final Map<String, Double> RATE_PER_HOUR = new HashMap<>(Map.of(
            "4W", 30.0,
            "2W", 20.0
    ));

    public ReservationResponse reserve(ReservationRequest req) {
        // start and end
        LocalDateTime start = req.getStartTime();
        LocalDateTime end = req.getEndTime();

        if (!start.isBefore(end)) {
            throw new BadRequestException("Start time must be before end time");
        }

        long minutes = Duration.between(start, end).toMinutes();
        double hours = Math.ceil(minutes / 60.0);

        if (hours > 24.0) {
            throw new BadRequestException("Reservation duration cannot exceed 24 hours");
        }

        Slot slot = slotRepository.findById(req.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found: " + req.getSlotId()));

        // cross check if there are overlaps
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(slot.getId(), start, end);
        if (!overlapping.isEmpty()) {
            throw new ReservationConflictException("Slot already reserved for the given time range");
        }

        // Calculate cost
        Double rate = RATE_PER_HOUR.get(req.getVehicleType());
        if (rate == null) {
            throw new BadRequestException("Unknown vehicle type: " + req.getVehicleType());
        }
        double cost = hours * rate;

        // Save reservation
        Reservation res = new Reservation();
        res.setVehicleNumber(req.getVehicleNumber());
        res.setVehicleType(req.getVehicleType());
        res.setStartTime(start);
        res.setEndTime(end);
        res.setCost(cost);
        res.setSlot(slot);

        Reservation saved = reservationRepository.save(res);

        // Build response DTO
        ReservationResponse resp = new ReservationResponse(
                saved.getId(),
                saved.getVehicleNumber(),
                saved.getVehicleType(),
                saved.getSlot().getId(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getCost()
        );

        return resp;
    }

    public List<Slot> getAvailableSlots(LocalDateTime start, LocalDateTime end) {
        return slotRepository.findAvailableSlots(start, end);
    }

    public ReservationResponse getReservation(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found: " + id));
        return new ReservationResponse(
                r.getId(), r.getVehicleNumber(), r.getVehicleType(),
                r.getSlot().getId(), r.getStartTime(), r.getEndTime(), r.getCost()
        );
    }

    public void cancelReservation(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found: " + id));
        reservationRepository.delete(r);
    }
}
