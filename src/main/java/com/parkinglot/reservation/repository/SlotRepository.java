package com.parkinglot.reservation.repository;

import com.parkinglot.reservation.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    // find slots that DO NOT have a reservation overlapping [start, end)
    @Query("SELECT s FROM Slot s WHERE s.id NOT IN (SELECT r.slot.id FROM Reservation r WHERE r.startTime < :end AND r.endTime > :start)")
    List<Slot> findAvailableSlots(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
