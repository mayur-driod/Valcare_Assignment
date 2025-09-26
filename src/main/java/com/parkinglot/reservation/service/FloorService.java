package com.parkinglot.reservation.service;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.dto.FloorRequest;
import com.parkinglot.reservation.repository.FloorRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final FloorRepository floorRepository;

    public Floor createFloor(FloorRequest req) {
        Floor f = new Floor();
        f.setFloorName(req.getFloorName());
        return floorRepository.save(f);
    }
}
