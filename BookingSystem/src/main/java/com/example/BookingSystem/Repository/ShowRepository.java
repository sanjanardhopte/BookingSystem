package com.example.BookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BookingSystem.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Integer> {
}
