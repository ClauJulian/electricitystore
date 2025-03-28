package com.claujulian.electricity.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.servicios.FabricaServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/fabrica")
@RequiredArgsConstructor
public class FabricaControlador {

    private final FabricaServicio fabricaServicio;
    private static final Logger LOGGER = Logger.getLogger(FabricaControlador.class.getName());


    @GetMapping("/registrar")
    public String registrar() {
        return "fabrica_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            fabricaServicio.crearFabrica(nombre);
            model.addAttribute("exito", "¡La Fábrica se ha creado con exito!");
            return "fabrica_form.html";
        } catch (MiException me) {
            model.addAttribute("error", "¡Fábrica debe tener un nombre!");
            LOGGER.log(Level.SEVERE, "Fábrica debe tener un nombre.", me);
            return "fabrica_form.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap model) {
        List<Fabrica> fabricas = fabricaServicio.listarFabricas();
        model.addAttribute("fabricas", fabricas);
        return "fabricas_list.html";
    }
 
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap model) {
        model.put("fabrica", fabricaServicio.buscarPorUUID(id));
        return "fabrica_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, String nombre, RedirectAttributes redirect){
        try {
            fabricaServicio.modificarFabrica(nombre, id);
            redirect.addFlashAttribute("exito", "La Fábrica fue modificada exitosamente.");    
            return "redirect:../lista";
            
        }  catch (MiException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:../modificar/{id}";
        } 
    }

}
