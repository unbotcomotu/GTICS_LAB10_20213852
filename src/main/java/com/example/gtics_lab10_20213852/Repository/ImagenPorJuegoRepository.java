package com.example.gtics_lab10_20213852.Repository;

import com.example.gtics_lab10_20213852.Entity.ImagenPorJuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImagenPorJuegoRepository extends JpaRepository<ImagenPorJuego, Integer> {
    @Query(nativeQuery = true,value = "select * from imagen_por_juego where id_juego=?1")
    List<ImagenPorJuego> listarImagenesPorJuego(Integer idJuego);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "insert into imagen_por_juego(id_imagen,id_juego) values(?1,?2)")
    void agregarImagenPorJuego(Integer idImagen,Integer idJuego);
}