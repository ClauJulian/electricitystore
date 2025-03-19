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

import com.claujulian.electricity.entidades.Fabrica;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.servicios.FabricaServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/fabrica")
@RequiredArgsConstructor
public class FabricaControlador {

    private final FabricaServicio fabricaServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "fabrica_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            fabricaServicio.crearFabrica(nombre);
            model.addAttribute("exito", "¡La Fabrica se ha creado con exito!");
        } catch (MiException me) {
            model.addAttribute("error", "¡La Fabrica debe tener un nombre!");
            Logger.getLogger(FabricaControlador.class.getName()).log(Level.SEVERE, null, me);
            return "fabrica_form.html";
        }
        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Fabrica> fabricas = fabricaServicio.listarFabricas();
        modelo.addAttribute("fabricas", fabricas);
        return "fabricas_list.html";
    }

     
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap modelo) {
        modelo.put("fabrica", fabricaServicio.buscarPorUUID(id));
        return "fabrica_modificar.html";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, String nombre, ModelMap modelo) {
        try {
            fabricaServicio.modificarFabrica(nombre, id);

            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "fabrica_modificar.html";
        }
    }

}
