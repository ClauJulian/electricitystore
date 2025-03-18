package com.claujulian.electricity.servicios;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.repositorios.FabricaRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // -> inyeccion de depencias por constructor (final)
public class FabricaServicio {

    private final FabricaRepositorio fabricaRepositorio;

 // CREATE
 @Transactional
 public void crearFabrica(String nombreFabrica) throws MiException{
     validar(nombreFabrica);
     Fabrica fabrica = new Fabrica();
     fabrica.setNombreFabrica(nombreFabrica);
     fabricaRepositorio.save(fabrica);
 }

  // UPDATE
    @Transactional
    public void modificarFabrica(String nombreFabrica, UUID idFabrica) throws MiException{
        validar(nombreFabrica);
        Optional<Fabrica> respuesta = fabricaRepositorio.findById(idFabrica);

        if (respuesta.isPresent()) {
            Fabrica fabrica = respuesta.get();
            fabrica.setNombreFabrica(nombreFabrica);
            fabricaRepositorio.save(fabrica);
        }
    }

    // READ
    @Transactional(readOnly = true)
    public Fabrica buscarPorUUID(UUID id){
        return fabricaRepositorio.getReferenceById(id);
    }

    // READ
    @Transactional(readOnly = true)
    public List<Fabrica> listarFabricas() {
        List<Fabrica> fabricas = new ArrayList<Fabrica>();

        fabricas = fabricaRepositorio.findAll();

        return fabricas;
    }

     // EXTRAS
    private void validar(String nombreFabrica) throws MiException{
        if (nombreFabrica.isEmpty() || nombreFabrica == null) {
            throw new MiException("El nombre de la fabrica no puede estar vacio o ser nulo!");
        }
       
    }
}
