package com.example.BookingSystem.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BookingSystem.Repository.BookingRepository;
import com.example.BookingSystem.Response.ResponseBean;
import com.example.BookingSystem.entity.Booking;

@RestController
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/booking")
    public ResponseEntity<ResponseBean> getBookings() {
        System.out.println("GET request received for all bookings");
        List<Booking> bookingList = bookingRepository.findAll();
        ResponseBean response = new ResponseBean();
        response.setData(bookingList);
        response.setErrorCode("200");
        response.setErrorDesc("Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<ResponseBean> getBookingById(@PathVariable("id") Integer id) {
        ResponseBean response = new ResponseBean();
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            response.setErrorCode("200");
            response.setErrorDesc("Booking Found");
            response.setData(booking.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/booking")
    public ResponseEntity<ResponseBean> addBooking(@RequestBody Booking booking) {
        ResponseBean response = new ResponseBean();
        if (booking.getBookingStatus() == null) {
            booking.setBookingStatus("Reserved");
        }
        if (booking.getBookingDateTime() == null) {
            booking.setBookingDateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        }
        Booking savedBooking = bookingRepository.save(booking);
        response.setErrorCode("200");
        response.setErrorDesc("Booking Added Successfully");
        response.setData(savedBooking);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<ResponseBean> updateBooking(@PathVariable Integer id, @RequestBody Booking booking) {
        ResponseBean response = new ResponseBean();
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking updatedBooking = existingBooking.get();
            updatedBooking.setUserId(booking.getUserId());
            updatedBooking.setShowId(booking.getShowId());
            updatedBooking.setBookingDateTime(booking.getBookingDateTime());
            updatedBooking.setBookingStatus(booking.getBookingStatus());
            updatedBooking.setTotalAmount(booking.getTotalAmount());
            bookingRepository.save(updatedBooking);
            response.setErrorCode("200");
            response.setErrorDesc("Booking Updated Successfully");
            response.setData(updatedBooking);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/booking/{id}")
    public ResponseEntity<ResponseBean> patchBooking(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        ResponseBean response = new ResponseBean();
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            fields.forEach((key, value) -> {
                switch (key) {
                    case "bookingStatus" -> booking.setBookingStatus((String) value);
                    case "totalAmount" -> booking.setTotalAmount(((Number) value).floatValue());
                }
            });
            bookingRepository.save(booking);
            response.setErrorCode("200");
            response.setErrorDesc("Booking Updated Successfully");
            response.setData(booking);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<ResponseBean> deleteBooking(@PathVariable Integer id) {
        ResponseBean response = new ResponseBean();
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            bookingRepository.delete(existingBooking.get());
            response.setErrorCode("200");
            response.setErrorDesc("Booking Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}