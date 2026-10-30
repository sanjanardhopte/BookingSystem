package com.example.BookingSystem.Scheduler;

import java.sql.Timestamp;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.BookingSystem.Repository.BookingRepository;

@Component
public class HoldExpiryJob {

    private final BookingRepository bookingRepository;

    public HoldExpiryJob(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Runs every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void releaseExpiredReservations() {
        int cancelled = bookingRepository.cancelExpiredReservations(
                new Timestamp(System.currentTimeMillis()));
        if (cancelled > 0) {
            System.out.println("Cancelled " + cancelled + " expired reservation(s)");
        }
    }
}