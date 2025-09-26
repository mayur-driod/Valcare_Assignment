package com.parkinglot.reservation.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorRequest {
    @NotBlank
    private String floorName;
}
