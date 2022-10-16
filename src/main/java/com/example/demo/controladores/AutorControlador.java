/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Daiana
 */
@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    AutorServicio autorServicio;
    
    @GetMapping("/registro")
    public String formulario(){
      return "registrarAutor.html";
    }
    
    @PostMapping("/registro")
    public String guardarAutor(ModelMap modelo, @RequestParam(required=false) String nombre, @RequestParam(required=false) String apellido){
        
        try{
           autorServicio.registrarAutor(nombre,apellido);
           modelo.put("exito","Registro Exitoso");
           return "registrarAutor";
           
        }catch(ErroresServicios e){
            modelo.put("error",e.getMessage());
            modelo.put("nombre",nombre);
            modelo.put("apellido",apellido);
            return "registrarAutor";
        }
    }
    
    @PostMapping("/editar")
    public String guardarAutor(ModelMap modelo, @RequestParam(required=false) String nombre, @RequestParam(required=false) String apellido,@RequestParam(required=false) String idAutor){
        System.out.println("-->"+idAutor);
        System.out.println("-->"+nombre);
        System.out.println("-->"+idAutor);
        try{
           autorServicio.modificarAutor(idAutor, nombre, apellido);
           modelo.put("exito","Actualización exitosa");
           return "redirect:/libro/listaAutor";
           
        }catch(ErroresServicios e){
            modelo.put("error",e.getMessage());
            modelo.put("nombre",nombre);
            modelo.put("apellido",apellido);
            return "listaAutor";
        }
    }
    
    @GetMapping("/modificarAutor")
    public String modificarAutor(@RequestParam("id") String id,ModelMap modelo){
        Autor autor = new Autor();
        try{
            autor = autorServicio.buscarPorId(id);
            modelo.addAttribute("autor", autor);
        }catch(Exception e){
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE,null,e);
        }
        
        return "modificarAutor.html";
    }
    
    @GetMapping("/listaAutor")
    public String listaAutor(ModelMap modelo){
      List <Autor> autores = autorServicio.listarLibros();
      modelo.addAttribute("autores",autores);
      return "listaAutor.html";
    }
    
    
}
