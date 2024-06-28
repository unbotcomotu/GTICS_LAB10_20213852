package com.example.gtics_lab10_20213852.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "juego")
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juego", nullable = false)
    private Integer id;

    @Column(name = "partida_finalizada", nullable = false)
    private Boolean partidaFinalizada;
}