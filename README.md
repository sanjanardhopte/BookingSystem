# Booking System

A RESTful backend application for managing shows, bookings, and seat reservations, with a focus on data integrity, seat availability, and booking lifecycle management.

## Overview

The Booking System manages movie and event shows, customer bookings, and individual seat reservations. It uses a relational database to maintain relationships between shows, bookings, and seats while enforcing essential booking rules.

## Core Entities

### 1. Show
Stores information about scheduled movie screenings and events.
- Show date, time, and screen details
- Movie or event association
- Seat category pricing
- Validation to ensure exactly one of `movieId` or `eventId` is provided

### 2. Booking
Represents a customer's reservation for a particular show.
- Customer and show references
- Booking date and time
- Booking status: `Reserved`, `Confirmed`, or `Cancelled`
- Total booking amount

### 3. Booking Seat
Represents individual seats associated with a booking.
- Seat number and category
- Individual seat price
- Associated booking reference
- Temporary seat hold expiration

## Key Features

- REST API operations for managing shows, bookings, and seat reservations
- Support for movie-based and event-based shows
- Validation of show configurations
- Temporary seat holds with a 10-minute expiration period
- Prevention of duplicate active seat reservations
- Seat availability tracking based on booking status and hold expiration
- Booking lifecycle management
- HTTP error handling for invalid requests, missing resources, and booking conflicts

## Business Logic

- A show must be associated with exactly one movie or event.
- A seat cannot be reserved twice for the same show while an existing reservation is active.
- Reserved seats remain unavailable while their hold is active.
- Confirmed bookings continue to occupy their seats.
- Cancelled bookings and expired reservation holds release seat availability.

## Database Design

The system uses three related PostgreSQL tables:

| Table | Purpose |
|---|---|
| `show` | Stores show scheduling and pricing information |
| `booking` | Stores customer booking details and status |
| `booking_seat` | Stores individual seat reservation details |

Foreign-key relationships connect bookings to shows and seat reservations to bookings, reducing data duplication and maintaining referential integrity.

## Technology Stack

- **Backend:** Node.js
- **Database:** PostgreSQL
- **Architecture:** RESTful APIs
- **API Testing:** Postman
- **Version Control:** Git

## Project Objective

To develop a structured booking backend that manages show schedules, customer reservations, and seat availability while enforcing business rules and maintaining consistent relationships between database entities.
