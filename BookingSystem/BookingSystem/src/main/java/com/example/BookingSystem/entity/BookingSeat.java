package com.example.BookingSystem.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "booking_seat")
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_seat_id")
    private int bookingSeatId;

    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "seat_category")
    private String seatCategory;

    @Column(name = "seat_price")
    private float seatPrice;

    @Column(name = "hold_expires_at")
    private Timestamp holdExpiresAt;

    public BookingSeat() {}

    public int getBookingSeatId() {
        return bookingSeatId;
    }

    public void setBookingSeatId(int bookingSeatId) {
        this.bookingSeatId = bookingSeatId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatCategory() {
        return seatCategory;
    }

    public void setSeatCategory(String seatCategory) {
        this.seatCategory = seatCategory;
    }

    public float getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(float seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Timestamp getHoldExpiresAt() {
        return holdExpiresAt;
    }

    public void setHoldExpiresAt(Timestamp holdExpiresAt) {
        this.holdExpiresAt = holdExpiresAt;
    }
}