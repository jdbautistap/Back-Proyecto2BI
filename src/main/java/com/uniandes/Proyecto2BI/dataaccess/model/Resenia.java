package com.uniandes.Proyecto2BI.dataaccess.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resenia")
@Data
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String feeling;

    private String modelFeeling;

    @ManyToOne
    @JoinColumn
    private Usuario usuario;
}
