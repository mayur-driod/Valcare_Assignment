package com.parkinglot.reservation.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleNumber;

    private String vehicleType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double cost;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    @JsonBackReference
    private Slot slot;
}
