package org.example.tranfercar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City fromCity;

    @ManyToOne
    private City toCity;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private double tripPrice;
    private int availableSeats;

    @ManyToOne
    private DriverAccount driver;

    @ManyToOne
    private Vehicle vehicle;

    private String notes;

    @OneToMany(mappedBy = "trip")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "trip")
    private List<Review> reviews;

}
