package com.claujulian.electricity.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.claujulian.electricity.entidades.Articulo;
import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.entidades.Imagen;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.repositorios.ArticuloRepositorio;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // -> inyeccion de depencias por constructor (final)
public class ArticuloServicio {

    private final ArticuloRepositorio articuloRepositorio;
    private final FabricaServicio fabricaServicio;
    private final ImagenServicio imagenServicio;

    private AtomicInteger atomicNroArticulo;



@PostConstruct
    public void init() {
        int maxNroArticulo = articuloRepositorio.findAll()
                .stream()
                .mapToInt(Articulo::getNroArticulo)
                .max()
                .orElse(0);
        atomicNroArticulo = new AtomicInteger(maxNroArticulo);
    }
        
// CREATE
@Transactional
public void crearArticulo(MultipartFile archivo, String nombre, String descripcion, String idFabrica) throws MiException {
    UUID id_fabrica = UUID.fromString(idFabrica);
    validar(nombre, descripcion, id_fabrica);
    Fabrica fabrica = fabricaServicio.buscarPorUUID(id_fabrica);
   
    Articulo articulo = new Articulo();
    int nroArticulo = atomicNroArticulo.incrementAndGet();
    
    articulo.setNroArticulo(nroArticulo);
    articulo.setNombreArticulo(nombre);;
    articulo.setDescripcionArticulo(descripcion);;
    articulo.setFabrica(fabrica);
   
    Imagen imagen = imagenServicio.guardar(archivo);
        articulo.setImagen(imagen);

    articuloRepositorio.save(articulo);
}

// UPDATE
@Transactional
public void modificarArticulo(MultipartFile archivo, UUID idArticulo,String nombreArticulo, String descripcionArticulo,  UUID idFabrica) throws MiException{
   
   Optional<Articulo> articuloAActualizar = articuloRepositorio.findById(idArticulo);

   if(articuloAActualizar.isPresent()){
   
    articuloAActualizar.get().setNombreArticulo(nombreArticulo);
    articuloAActualizar.get().setDescripcionArticulo(descripcionArticulo);
    articuloAActualizar.get().setFabrica(fabricaServicio.buscarPorUUID(idFabrica));
    
   
    UUID idImagen = articuloAActualizar.get().getImagen().getId();;


    if(!archivo.isEmpty()){
        Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
        articuloAActualizar.get().setImagen(imagen);
       
    }       
    articuloRepositorio.save(articuloAActualizar.get());}
}

 // READ
    @Transactional(readOnly = true)
    public List<Articulo> listarArticulos() {
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
