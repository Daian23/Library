/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.errores.ErroresServicios;
import com.example.demo.servicios.EditorialServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    EditorialServicio editorialServicio;
    
    @GetMapping("/registro")
    public String formulario(){
      return "registrarEditorial.html";
    }
    
    @PostMapping("/registro")
    public String guardarEditorial(ModelMap modelo, @RequestParam(required=false) String nombre){
        System.out.println("Nombre: "+nombre);
        try{
            
           editorialServicio.RegistrarEditorial(nombre);
           modelo.put("exito","Registro Exitoso");
           
           return "registrarEditorial";
           
        }catch(ErroresServicios e){
            System.out.println("Hola");
            modelo.put("error",e.getMessage());
            modelo.put("nombre",nombre);
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE,null,e);
            System.out.println(e.getMessage());
            return "registrarEditorial";
        }
    }
}
