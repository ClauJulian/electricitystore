package com.claujulian.electricity.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claujulian.electricity.entidades.Articulo;
import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.repositorios.ArticuloRepositorio;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // -> inyeccion de depencias por constructor (final)
public class ArticuloServicio {

    private final ArticuloRepositorio articuloRepositorio;
    private final FabricaServicio fabricaServicio;


// CREATE
@Transactional
public void crearArticulo(String nombre, String descripcion, UUID idFabrica) throws MiException {
    validar(nombre, descripcion, idFabrica);
    Fabrica fabrica = fabricaServicio.buscarPorUUID(idFabrica);
   
    Articulo articulo = new Articulo();

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

 // READ
    @Transactional(readOnly = true)
    public List<Articulo> listarLibros() {
        List<Articulo> articulos = new ArrayList<Articulo>();
        
        articulos = articuloRepositorio.findAll();

        return articulos;
    }

    // READ
    @Transactional(readOnly = true)
    public Articulo buscarPorUUID(UUID idArticulo){
        return articuloRepositorio.getReferenceById(idArticulo);
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
