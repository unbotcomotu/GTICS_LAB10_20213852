package com.example.gtics_lab10_20213852.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imagenes")
public class Imagene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen", nullable = false)
    private Integer id;

    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

}