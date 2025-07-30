package org.example.tranfercar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class DriverAccount extends Account {
    @OneToMany(mappedBy = "author")
    private List<Review> writtenReviews;

    @OneToMany(mappedBy = "target")
    private List<Review> driverReviews;
}
