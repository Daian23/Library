/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Libro;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.servicios.LibroServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping("/libro")
    public ResponseEntity<byte[]> fotoLibro(@RequestParam String id) throws ErroresServicios {
        try{
            Libro libro = libroServicio.buscarPorId(id);
      
            if(libro.getFotoLibro() == null){
                throw new ErroresServicios("El libro no tiene una foto asignada");
            }
            
            byte[] foto = libro.getFotoLibro().getContenido();
            System.out.println(foto);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>(foto,headers, HttpStatus.OK);
        }catch(ErroresServicios e){
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE,null,e);
           
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
