package com.claujulian.electricity.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.claujulian.electricity.entidades.Articulo;
import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.servicios.ArticuloServicio;
import com.claujulian.electricity.servicios.FabricaServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/articulo")
@RequiredArgsConstructor
public class ArticuloControlador {

    private final ArticuloServicio articuloServicio;

    private final FabricaServicio fabricaServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        List<Fabrica> fabricas = fabricaServicio.listarFabricas();
        model.addAttribute("fabricas", fabricas);
        return "articulo_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreArticulo, @RequestParam String descripcionArticulo, @RequestParam String idFabrica, ModelMap model) {
        try {
            articuloServicio.crearArticulo(nombreArticulo, descripcionArticulo, idFabrica);
            model.addAttribute("exito", "¡La Fabrica se ha creado con exito!");
        } catch (MiException me) {
            model.addAttribute("error", "¡La Fabrica debe tener un nombre!");
            Logger.getLogger(ArticuloControlador.class.getName()).log(Level.SEVERE, null, me);
            return "articulo_form.html";
        }
        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Articulo> articulos = articuloServicio.listarArticulos();
        modelo.addAttribute("articulos", articulos);
        return "articulos_list.html";
    }

     
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap modelo) {
        modelo.put("articulo", articuloServicio.buscarPorUUID(id));
        modelo.addAttribute("fabricas", fabricaServicio.listarFabricas()); 
        return "articulo_modificar.html";
    }


    @PostMapping("/modificar/{idArticulo}")
    public String modificar(@PathVariable UUID idArticulo, String nombreArticulo, String descripcionArticulo, UUID idFabrica, ModelMap modelo) throws MiException {
        try {
            if (idArticulo == null || idFabrica == null) {
                throw new MiException("Debe seleccionar un artículo y una fábrica válidos.");
            }

            articuloServicio.modificarArticulo(idArticulo, nombreArticulo, descripcionArticulo, idFabrica);
            return "redirect:../lista";

        } catch(MiException ex) {
            modelo.put("error", ex.getMessage());
            return "articulo_modificar.html";
        }
    }


}
