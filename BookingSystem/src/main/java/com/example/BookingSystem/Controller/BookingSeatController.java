package com.example.BookingSystem.Controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BookingSystem.Repository.BookingRepository;
import com.example.BookingSystem.Repository.BookingSeatRepository;
import com.example.BookingSystem.Response.ResponseBean;
import com.example.BookingSystem.entity.Booking;
import com.example.BookingSystem.entity.BookingSeat;

@RestController
public class BookingSeatController {

    private final BookingSeatRepository bookingSeatRepository;
    private final BookingRepository bookingRepository;

    public BookingSeatController(BookingSeatRepository bookingSeatRepository,
                                 BookingRepository bookingRepository) {
        this.bookingSeatRepository = bookingSeatRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/booking_seat")
    public ResponseEntity<ResponseBean> getBookingSeats() {
        System.out.println("GET request received for all booking seats");
        List<BookingSeat> seatList = bookingSeatRepository.findAll();
        ResponseBean response = new ResponseBean();
        response.setData(seatList);
        response.setErrorCode("200");
        response.setErrorDesc("Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/booking_seat/taken/{showId}")
    public ResponseEntity<ResponseBean> getTakenSeats(@PathVariable Integer showId) {
        ResponseBean response = new ResponseBean();
        List<BookingSeat> takenSeats = bookingSeatRepository.findTakenSeats(
                showId, new Timestamp(System.currentTimeMillis()));
        response.setData(takenSeats);
        response.setErrorCode("200");
        response.setErrorDesc("Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/booking_seat/{id}")
    public ResponseEntity<ResponseBean> getBookingSeatById(@PathVariable("id") Integer id) {
        ResponseBean response = new ResponseBean();
        Optional<BookingSeat> seat = bookingSeatRepository.findById(id);
        if (seat.isPresent()) {
            response.setErrorCode("200");
            response.setErrorDesc("Booking Seat Found");
            response.setData(seat.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Seat Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/booking_seat")
    public ResponseEntity<ResponseBean> addBookingSeat(@RequestBody BookingSeat bookingSeat) {
        ResponseBean response = new ResponseBean();

        Optional<Booking> booking = bookingRepository.findById(bookingSeat.getBookingId());
        if (booking.isEmpty()) {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<BookingSeat> takenSeats = bookingSeatRepository.findTakenSeats(
                booking.get().getShowId(), new Timestamp(System.currentTimeMillis()));
        for (BookingSeat taken : takenSeats) {
            if (bookingSeat.getSeatNumber() != null
                    && bookingSeat.getSeatNumber().equals(taken.getSeatNumber())) {
                response.setErrorCode("409");
                response.setErrorDesc("Seat " + bookingSeat.getSeatNumber()
                        + " is already taken for this show");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        if (bookingSeat.getHoldExpiresAt() == null) {
            bookingSeat.setHoldExpiresAt(new Timestamp(System.currentTimeMillis() + 2 * 60 * 1000));
        }
        BookingSeat savedSeat = bookingSeatRepository.save(bookingSeat);
        response.setErrorCode("200");
        response.setErrorDesc("Booking Seat Added Successfully");
        response.setData(savedSeat);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/booking_seat/{id}")
    public ResponseEntity<ResponseBean> updateBookingSeat(@PathVariable Integer id, @RequestBody BookingSeat bookingSeat) {
        ResponseBean response = new ResponseBean();
        Optional<BookingSeat> existingSeat = bookingSeatRepository.findById(id);
        if (existingSeat.isPresent()) {
            BookingSeat updatedSeat = existingSeat.get();
            updatedSeat.setBookingId(bookingSeat.getBookingId());
            updatedSeat.setSeatNumber(bookingSeat.getSeatNumber());
            updatedSeat.setSeatCategory(bookingSeat.getSeatCategory());
            updatedSeat.setSeatPrice(bookingSeat.getSeatPrice());
            if (bookingSeat.getHoldExpiresAt() != null) {
                updatedSeat.setHoldExpiresAt(bookingSeat.getHoldExpiresAt());
            }
            bookingSeatRepository.save(updatedSeat);
            response.setErrorCode("200");
            response.setErrorDesc("Booking Seat Updated Successfully");
            response.setData(updatedSeat);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Seat Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/booking_seat/{id}")
    public ResponseEntity<ResponseBean> patchBookingSeat(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        ResponseBean response = new ResponseBean();
        Optional<BookingSeat> existingSeat = bookingSeatRepository.findById(id);
        if (existingSeat.isPresent()) {
            BookingSeat seat = existingSeat.get();
            fields.forEach((key, value) -> {
                switch (key) {
                    case "seatCategory" -> seat.setSeatCategory((String) value);
                    case "seatPrice" -> seat.setSeatPrice(((Number) value).floatValue());
                }
            });
            bookingSeatRepository.save(seat);
            response.setErrorCode("200");
            response.setErrorDesc("Booking Seat Updated Successfully");
            response.setData(seat);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Seat Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/booking_seat/{id}")
    public ResponseEntity<ResponseBean> deleteBookingSeat(@PathVariable Integer id) {
        ResponseBean response = new ResponseBean();
        Optional<BookingSeat> existingSeat = bookingSeatRepository.findById(id);
        if (existingSeat.isPresent()) {
            bookingSeatRepository.delete(existingSeat.get());
            response.setErrorCode("200");
            response.setErrorDesc("Booking Seat Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Booking Seat Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}