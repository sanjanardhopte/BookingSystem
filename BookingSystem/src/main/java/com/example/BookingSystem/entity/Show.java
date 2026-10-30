package com.example.BookingSystem.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_id")
    private int showId;

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "screen_id")
    private int screenId;

    @Column(name = "show_date")
    private Date showDate;

    @Column(name = "show_time")
    private Time showTime;

    @Column(name = "recliner_price")
    private float reclinerPrice;

    @Column(name = "prime_plus_price")
    private float primePlusPrice;

    @Column(name = "prime_price")
    private float primePrice;

    public Show() {}

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    public float getReclinerPrice() {
        return reclinerPrice;
    }

    public void setReclinerPrice(float reclinerPrice) {
        this.reclinerPrice = reclinerPrice;
    }

    public float getPrimePlusPrice() {
        return primePlusPrice;
    }

    public void setPrimePlusPrice(float primePlusPrice) {
        this.primePlusPrice = primePlusPrice;
    }

    public float getPrimePrice() {
        return primePrice;
    }

    public void setPrimePrice(float primePrice) {
        this.primePrice = primePrice;
    }
}
