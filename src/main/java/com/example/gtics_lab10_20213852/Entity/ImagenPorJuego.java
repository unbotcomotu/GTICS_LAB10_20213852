package com.example.gtics_lab10_20213852.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imagen_por_juego")
public class ImagenPorJuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen_por_juego", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_imagen")
    private Imagene imagene;

    @ManyToOne
    @JoinColumn(name = "id_juego")
    private Juego juego;

    @Column(name = "posicion1_imagen", nullable = false, length = 45)
    private String posicion1Imagen;

    @Column(name = "posicion2_imagen", nullable = false, length = 45)
    private String posicion2Imagen;

}