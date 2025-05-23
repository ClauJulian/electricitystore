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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registro(MultipartFile archivo , @RequestParam String nombreArticulo, @RequestParam String descripcionArticulo, @RequestParam(required = false) String idFabrica,  ModelMap model, RedirectAttributes redirect) {
        try {
            articuloServicio.crearArticulo(archivo, nombreArticulo, descripcionArticulo, idFabrica);
            model.addAttribute("exito", "¡El artículo se ha creado con exito!");
            return "articulo_form.html";
        } catch (MiException me) {
            redirect.addFlashAttribute("error", me.getMessage());
            Logger.getLogger(ArticuloControlador.class.getName()).log(Level.SEVERE, null, me);
            return "redirect:./registrar";
        }
        
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Articulo> articulos = articuloServicio.listarArticulos();
        modelo.addAttribute("articulos", articulos);
        return "articulos_list.html";
    }

     
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap model) {
        Articulo articulo = articuloServicio.buscarPorUUID(id);
        model.put("articulo", articulo);
        model.addAttribute("fabricas", fabricaServicio.listarFabricas()); 
        model.addAttribute("fabricaSeleccionada", articulo.getFabrica().getIdFabrica());  
        
        return "articulo_modificar.html";
    }


    @PostMapping("/modificar/{idArticulo}")
    public String modificar(MultipartFile archivo, @PathVariable UUID idArticulo, String nombreArticulo, String descripcionArticulo, UUID idFabrica, ModelMap modelo, RedirectAttributes redirect) throws MiException{
        try {
            if (idArticulo == null || idFabrica == null) {
                throw new MiException("Debe seleccionar un artículo y una fábrica válidos.");
            }

            articuloServicio.modificarArticulo(archivo, idArticulo, nombreArticulo, descripcionArticulo, idFabrica);
            redirect.addFlashAttribute("exito", "El artículo se ha modificado exitosamente.");
            return "redirect:../lista";

        } catch(MiException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:../lista";
        }
    }


}
