package com.parkinglot.reservation.repository;

import com.parkinglot.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // reservations for a slot that overlap with [start, end)
    @Query("SELECT r FROM Reservation r WHERE r.slot.id = :slotId AND r.startTime < :end AND r.endTime > :start")
    List<Reservation> findOverlappingReservations(@Param("slotId") Long slotId,
                                                  @Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end);
}
