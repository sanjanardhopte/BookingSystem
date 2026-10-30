package com.example.BookingSystem.Repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.BookingSystem.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.bookingStatus = 'Cancelled' " +
           "WHERE b.bookingStatus = 'Reserved' " +
           "AND EXISTS (SELECT s.bookingSeatId FROM BookingSeat s WHERE s.bookingId = b.bookingId) " +
           "AND NOT EXISTS (SELECT s2.bookingSeatId FROM BookingSeat s2 WHERE s2.bookingId = b.bookingId AND s2.holdExpiresAt > :now)")
    int cancelExpiredReservations(@Param("now") Timestamp now);
}