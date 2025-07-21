package org.example.tranfercar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;  // one user can have passenger or driver role

    @OneToMany(mappedBy = "driver")
    private List<Trip> tripsAsDriver;

    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "author")
    private List<Review> writtenReviews;

    @OneToMany(mappedBy = "target")
    private List<Review> receivedReviews;

    public enum Role {
        DRIVER,
        PASSENGER
    }

}
