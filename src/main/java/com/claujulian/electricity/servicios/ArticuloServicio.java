package com.claujulian.electricity.servicios;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claujulian.electricity.entidades.Articulo;
import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.repositorios.ArticuloRepositorio;
import com.claujulian.electricity.repositorios.FabricaRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // -> inyeccion de depencias por constructor (final)
public class ArticuloServicio {

    private final ArticuloRepositorio articuloRepositorio;
    private final FabricaServicio fabricaServicio;

     private static final AtomicInteger atomicInteger = new AtomicInteger(1); // Atributo de clase

// CREATE
@Transactional
public void crearArticulo(String nombre, String descripcion, UUID idFabrica) throws MiException {
    validar(nombre, descripcion, idFabrica);
    Fabrica fabrica = fabricaServicio.buscarPorUUID(idFabrica);
   
    Articulo articulo = new Articulo();

    articulo.setNroArticulo(atomicInteger.incrementAndGet());
    articulo.setNombreArticulo(nombre);;
    articulo.setDescripcionArticulo(descripcion);;
    articulo.setFabrica(fabrica);
   

    articuloRepositorio.save(articulo);
}

// UPDATE
@Transactional
public void modificarArticulo(String nombreArticulo, String descripcionArticulo, UUID idArticulo, UUID idFabrica){
   
   Optional<Articulo> articuloAActualizar = articuloRepositorio.findById(idArticulo);

   if(articuloAActualizar.isPresent()){
    Articulo articulo = new Articulo();
    articulo.setIdArticulo(articuloAActualizar.get().getIdArticulo());
    articulo.setNroArticulo(articuloAActualizar.get().getNroArticulo());
    articulo.setNombreArticulo(nombreArticulo);
    articulo.setDescripcionArticulo(descripcionArticulo);
    articulo.setFabrica(fabricaServicio.buscarPorUUID(idFabrica));
    articuloRepositorio.save(articulo);}
}


    // EXTRAS
    private void validar(String nombre, String descripcion, UUID idFabrica) throws MiException{
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre del artículo no puede estar vacio o ser nulo!");
        }
        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La descripcion del artículo no puede estar vacio o ser nulo!");
        }
        if (idFabrica == null) {
            throw new MiException("El id de la fabrica no puede ser nulo");
        }
    }



    

}
