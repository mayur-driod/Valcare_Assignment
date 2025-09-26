package com.parkinglot.reservation.exception;

public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException(String msg) { super(msg); }
}
