package org.example.tranfercar.domain;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private User author;  // driver or passanger

    @ManyToOne
    private User target; // driver or passanger

    private String comment;
    private int rating;
}
