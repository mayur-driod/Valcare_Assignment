package com.parkinglot.reservation.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequest {
    @NotBlank
    private String slotNumber;

    @NotBlank
    private String vehicleType; // "2W" or "4W"

    @NotNull
    private Long floorId;
}
