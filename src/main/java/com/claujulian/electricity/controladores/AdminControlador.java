package com.claujulian.electricity.controladores;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.claujulian.electricity.entidades.Articulo;
import com.claujulian.electricity.entidades.Usuario;
import com.claujulian.electricity.excepciones.MiException;
import com.claujulian.electricity.servicios.ArticuloServicio;
import com.claujulian.electricity.servicios.UsuarioServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminControlador {

    private final UsuarioServicio usuarioServicio;
    private final ArticuloServicio articuloServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap model){
        List<Articulo> articulos = articuloServicio.listarArticulos();
        model.addAttribute("articulos", articulos);

        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo){
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_list.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable UUID id){
        usuarioServicio.cambiarRol(id);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuario/{id}")
    public String modificarUsuario(@PathVariable UUID id, ModelMap modelo){ 
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.addAttribute("usuario", usuario);  
        return "usuario_modificar.html";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, 
        @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        
            try{
                usuarioServicio.actualizar(id, nombre, apellido, email, password, password2);
                modelo.put("exito", "El usuario fue actualizado correctamente.");
                return "redirect:/admin/usuarios";
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
                modelo.put("nombre", nombre);
                modelo.put("apellido", apellido);
                modelo.put("email", email);
                return "usuario_modificar.html";
            }
    }

    
}