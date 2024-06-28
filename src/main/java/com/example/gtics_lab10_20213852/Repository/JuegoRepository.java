package com.example.gtics_lab10_20213852.Repository;

import com.example.gtics_lab10_20213852.Entity.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {
    @Query(nativeQuery = true,value = "select id_juego from juego order by id_juego desc limit 1")
    Integer obtenerUltimoIdJuego();

    @Query(nativeQuery = true,value = "select id_juego from juego where partida_finalizada=1 order by id_juego desc limit 1")
    Integer obtenerUltimoIdJuegoAcabado();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "insert into juego(id_juego) values (?1)")
    void nuevoJuego(Integer nuevoJuego);
}