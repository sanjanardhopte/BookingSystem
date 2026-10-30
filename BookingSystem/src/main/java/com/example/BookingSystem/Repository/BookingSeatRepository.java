package com.example.BookingSystem.Repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BookingSystem.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {

    @Query("SELECT bs FROM BookingSeat bs, Booking b " +
           "WHERE bs.bookingId = b.bookingId " +
           "AND b.showId = :showId " +
           "AND (b.bookingStatus = 'Confirmed' " +
           "OR (b.bookingStatus = 'Reserved' AND bs.holdExpiresAt > :now))")
    List<BookingSeat> findTakenSeats(@Param("showId") int showId, @Param("now") Timestamp now);
}