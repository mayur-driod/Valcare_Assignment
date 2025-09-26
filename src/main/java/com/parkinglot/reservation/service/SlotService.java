package com.parkinglot.reservation.service;

import com.parkinglot.reservation.dto.SlotRequest;
import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.model.Slot;
import com.parkinglot.reservation.repository.FloorRepository;
import com.parkinglot.reservation.repository.SlotRepository;
import com.parkinglot.reservation.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final SlotRepository slotRepository;
    private final FloorRepository floorRepository;

    public Slot createSlot(SlotRequest req) {
        Floor floor = floorRepository.findById(req.getFloorId())
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found: " + req.getFloorId()));

        Slot slot = new Slot();
        slot.setSlotNumber(req.getSlotNumber());
        slot.setVehicleType(req.getVehicleType());
        slot.setFloor(floor);

        return slotRepository.save(slot);
    }
}
