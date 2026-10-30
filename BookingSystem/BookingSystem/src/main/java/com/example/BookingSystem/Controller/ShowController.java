package com.example.BookingSystem.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BookingSystem.Repository.ShowRepository;
import com.example.BookingSystem.Response.ResponseBean;
import com.example.BookingSystem.entity.Show;

@RestController
public class ShowController {

    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @GetMapping("/show")
    public ResponseEntity<ResponseBean> getShows() {
        System.out.println("GET request received for all shows");

        List<Show> showList = showRepository.findAll();

        ResponseBean response = new ResponseBean();
        response.setData(showList);
        response.setErrorCode("200");
        response.setErrorDesc("Success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<ResponseBean> getShowById(@PathVariable("id") Integer id) {
        ResponseBean response = new ResponseBean();

        Optional<Show> show = showRepository.findById(id);

        if (show.isPresent()) {
            response.setErrorCode("200");
            response.setErrorDesc("Show Found");
            response.setData(show.get());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Show Not Found");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/show")
    public ResponseEntity<ResponseBean> addShow(@RequestBody Show show) {
        ResponseBean response = new ResponseBean();

        if ((show.getMovieId() != null) == (show.getEventId() != null)) {
            response.setErrorCode("400");
            response.setErrorDesc("A show must have exactly one of movieId or eventId");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Show savedShow = showRepository.save(show);

        response.setErrorCode("200");
        response.setErrorDesc("Show Added Successfully");
        response.setData(savedShow);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/show/{id}")
    public ResponseEntity<ResponseBean> updateShow(
            @PathVariable Integer id,
            @RequestBody Show show) {

        ResponseBean response = new ResponseBean();

        if ((show.getMovieId() != null) == (show.getEventId() != null)) {
            response.setErrorCode("400");
            response.setErrorDesc("A show must have exactly one of movieId or eventId");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Show> existingShow = showRepository.findById(id);

        if (existingShow.isPresent()) {
            Show updatedShow = existingShow.get();

            updatedShow.setMovieId(show.getMovieId());
            updatedShow.setEventId(show.getEventId());
            updatedShow.setScreenId(show.getScreenId());
            updatedShow.setShowDate(show.getShowDate());
            updatedShow.setShowTime(show.getShowTime());
            updatedShow.setReclinerPrice(show.getReclinerPrice());
            updatedShow.setPrimePlusPrice(show.getPrimePlusPrice());
            updatedShow.setPrimePrice(show.getPrimePrice());

            showRepository.save(updatedShow);

            response.setErrorCode("200");
            response.setErrorDesc("Show Updated Successfully");
            response.setData(updatedShow);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Show Not Found");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/show/{id}")
    public ResponseEntity<ResponseBean> patchShow(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> fields) {

        ResponseBean response = new ResponseBean();

        Optional<Show> existingShow = showRepository.findById(id);

        if (existingShow.isPresent()) {
            Show show = existingShow.get();

            fields.forEach((key, value) -> {
                switch (key) {
                    case "reclinerPrice" ->
                            show.setReclinerPrice(((Number) value).floatValue());

                    case "primePlusPrice" ->
                            show.setPrimePlusPrice(((Number) value).floatValue());

                    case "primePrice" ->
                            show.setPrimePrice(((Number) value).floatValue());
                }
            });

            showRepository.save(show);

            response.setErrorCode("200");
            response.setErrorDesc("Show Updated Successfully");
            response.setData(show);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Show Not Found");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/show/{id}")
    public ResponseEntity<ResponseBean> deleteShow(@PathVariable Integer id) {
        ResponseBean response = new ResponseBean();

        Optional<Show> existingShow = showRepository.findById(id);

        if (existingShow.isPresent()) {
            showRepository.delete(existingShow.get());

            response.setErrorCode("200");
            response.setErrorDesc("Show Deleted Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("404");
            response.setErrorDesc("Show Not Found");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}