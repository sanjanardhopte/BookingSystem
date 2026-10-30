package com.example.BookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BookingSystem.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
