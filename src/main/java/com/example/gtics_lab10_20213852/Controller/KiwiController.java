package com.example.gtics_lab10_20213852.Controller;

import com.example.gtics_lab10_20213852.Entity.ImagenPorJuego;
import com.example.gtics_lab10_20213852.Entity.Imagene;
import com.example.gtics_lab10_20213852.Repository.ImagenPorJuegoRepository;
import com.example.gtics_lab10_20213852.Repository.ImageneRepository;
import com.example.gtics_lab10_20213852.Repository.JuegoRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class KiwiController {

    private final ImageneRepository imageneRepository;
    private final JuegoRepository juegoRepository;
    private final ImagenPorJuegoRepository imagenPorJuegoRepository;

    public KiwiController(ImageneRepository imageneRepository,
                          JuegoRepository juegoRepository,
                          ImagenPorJuegoRepository imagenPorJuegoRepository) {
        this.imageneRepository = imageneRepository;
        this.juegoRepository = juegoRepository;
        this.imagenPorJuegoRepository = imagenPorJuegoRepository;
    }

    @GetMapping("/")
    public String jugar(Model model){
        Integer ultimaPartida=juegoRepository.obtenerUltimoIdJuego();
        Integer ultimaPartidaFinalizada= juegoRepository.obtenerUltimoIdJuegoAcabado();
        if(ultimaPartida==ultimaPartidaFinalizada){
            juegoRepository.nuevoJuego(ultimaPartida+1);
            model.addAttribute("idJuego",ultimaPartida+1);
        }else {
            List<ImagenPorJuego>imagenPorJuego=imagenPorJuegoRepository.listarImagenesPorJuego(ultimaPartida);
            model.addAttribute("listaImagenesPorJuego",imagenPorJuego);
            model.addAttribute("idJuego",ultimaPartida);
        }
        return "lab";
    }

    @PostMapping("/subirFoto")
    public ResponseEntity<HashMap<String, Object>> subirFoto(@RequestParam(value = "imagen",required = false) MultipartFile imagen,
                                                             @RequestParam(value = "idJuego",required = false)Integer idJuego){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        if(imagen==null||imagen.isEmpty()) {
            errors.put("imagen", "Suba una imagen");
            response.put("status", "error");
        }
        String fileName=imagen.getOriginalFilename();
        if (fileName.contains("..")){
            errors.put("imagen","Ataque detectado Uwu");
            response.put("status","error");
        }

        try{
            Imagene imagene=new Imagene();
            imagene.setImagen(imagen.getBytes());
            imageneRepository.save(imagene);
        } catch (IOException e) {
            errors.put("imagen","Error al guardar la imagen");
            response.put("status","error");
        }
        if(response.get("status")==null){
            Integer ultimoIdImagen=imageneRepository.obtenerUltimoIdImagen();
            imagenPorJuegoRepository.agregarImagenPorJuego(ultimoIdImagen,idJuego);
            response.put("status","success");
            response.put("content",ultimoIdImagen);
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/borrarFoto")
    public ResponseEntity<HashMap<String,Object>>borrarFoto(@RequestParam(value = "idImagen",required = false)Integer idImagen){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        if(idImagen==null){
            response.put("status","error");
            errors.put("id","No se ha enviado un id");
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        imageneRepository.borrarImagen(idImagen);
        response.put("status","success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mostrarImagen")
    public ResponseEntity<byte[]>mostrarImagen(Integer idImagen){
        Imagene imagene=imageneRepository.imagenPorId(idImagen);
        if(imagene!=null){
            byte[] imagen=imagene.getImagen();
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.setContentType(MediaType.valueOf("image/png"));
            return new ResponseEntity<>(imagen,httpHeaders,HttpStatus.OK);
        }else {
            return null;
        }
    }
}
