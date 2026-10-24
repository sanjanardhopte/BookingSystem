Booking System

A backend booking system designed to manage shows, customer bookings, and seat reservations using REST APIs and a relational database.

Project Overview

The project implements the core entities and business logic required for a booking system, with a focus on maintaining seat availability, managing booking statuses, and preventing duplicate seat reservations.

Core Entities
Show: Represents a scheduled movie screening or event, including its date, time, screen, and seat-category pricing.
Booking: Represents a customer's reservation for a particular show, including booking status, booking date, and total amount.
Booking Seat: Represents individual seats associated with a booking, including seat number, category, price, and hold expiration time.
Key Features
CRUD operations for show, booking, and seat reservation entities.
Support for both movie-based and event-based shows.
Validation to ensure each show belongs to exactly one movie or event.
Temporary seat reservations with a 10-minute hold period.
Duplicate seat booking prevention for the same show.
Seat availability management based on booking status and hold expiration.
Booking lifecycle management, including Reserved, Confirmed, and Cancelled statuses.
REST API error handling for invalid requests, missing resources, and booking conflicts.
Technology Stack
Backend: Node.js
Database: PostgreSQL
API Architecture: REST
API Testing: Postman
Core Logic

The system maintains separate but related entities for shows, bookings, and seat reservations to reduce data duplication and preserve data relationships.

A seat is considered unavailable when it belongs to a confirmed booking or an active reservation whose hold has not expired. Cancelled bookings and expired reservation holds release seat availability.

Business validations ensure invalid show configurations are rejected and existing active seat reservations cannot be duplicated through normal sequential booking requests.

Project Objective

To implement the core backend functionality of a booking system with relational data management, reservation lifecycle handling, and essential business-rule validation.
