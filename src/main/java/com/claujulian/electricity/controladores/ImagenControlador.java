package com.claujulian.electricity.controladores;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.claujulian.electricity.entidades.Articulo;
import com.claujulian.electricity.servicios.ArticuloServicio;
import com.claujulian.electricity.servicios.ImagenServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/imagen")
@RequiredArgsConstructor
public class ImagenControlador {

    private final ArticuloServicio articuloServicio;
    
    private final ImagenServicio imagenServicio;

    // Obtener imagen de articulo

    @GetMapping("/articulo/{id}")
    public ResponseEntity<byte[]> imagenArticulo(@PathVariable UUID id) {
        Articulo articulo = articuloServicio.buscarPorUUID(id);
        byte[] imagen = articulo.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    // Subir imagen al articulo

    @PostMapping("/articulo/{id}")
    public ResponseEntity<String> actualizarImagenArticulo(@PathVariable UUID id,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
// Llamada al servicio para guardar la imagen
            imagenServicio.actualizar(archivo, id);
            return new ResponseEntity<>("Imagen actualizada exitosamente",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la imagen",
                    HttpStatus.BAD_REQUEST);
        }
    } 
}
