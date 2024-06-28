package com.example.gtics_lab10_20213852.Repository;

import com.example.gtics_lab10_20213852.Entity.Imagene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageneRepository extends JpaRepository<Imagene, Integer> {
    @Query(nativeQuery = true,value = "select id_imagen from imagenes order by id_imagen desc limit 1")
    Integer obtenerUltimoIdImagen();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from imagenes where id_imagen=?1")
    void borrarImagen(Integer idImagen);

    @Query(nativeQuery = true,value = "select * from imagenes where id_imagen=?1")
    Imagene imagenPorId(Integer idImagen);
}