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

    @GetMapping("/registrar")
    public String registrar() {
        return "articulo_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam UUID idFabrica, ModelMap model) {
        try {
            articuloServicio.crearArticulo(nombre, descripcion, idFabrica);
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
        return "articulo_modificar.html";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID idArticulo, String nombre, String descripcion, UUID idFabrica, ModelMap modelo) throws MiException {
        try {
            if (idArticulo == null || idFabrica == null) {
                throw new MiException("Debe seleccionar un artículo y una fábrica válidos.");
            }

            articuloServicio.modificarArticulo(idArticulo, nombre, descripcion, idFabrica);
            return "redirect:../lista";

        } catch(MiException ex) {
            modelo.put("error", ex.getMessage());
            return "articulo_modificar.html";
        }
    }


}
