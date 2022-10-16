/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.repositorios.AutorRepositorio;
import com.example.demo.repositorios.EditorialRepositorio;
import com.example.demo.servicios.LibroServicio;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Daiana
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    LibroServicio libroServicio;
    
   @Autowired 
    AutorRepositorio autorRepositorio;
   
   @Autowired
   EditorialRepositorio editorialRepositorio;
    
    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores",autores);
        
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales",editoriales);
        
        return "registrarLibro.html"; 
    }
    
    @PostMapping("/registro")
    public String guardarLibro(ModelMap modelo,@RequestParam(required=false) Long isbn,@RequestParam(required=false) String titulo, @RequestParam(required=false) Integer anio, @RequestParam(required=false) Integer ejemplares, @RequestParam(required=false) String idAutor, @RequestParam(required=false) String idEditorial, @RequestParam(required=false) MultipartFile archivo){
        System.out.println("isbn: "+isbn);
            System.out.println("titulo: "+titulo);
            System.out.println("anio: "+anio);
            System.out.println("ejemplares: "+ejemplares);
            System.out.println("IdAutor: "+idAutor);
            System.out.println("IdEditorial: "+idEditorial);
            System.out.println("Archivo: "+archivo); 
           
        try{
            
            libroServicio.registrarLibro(isbn, titulo, anio, ejemplares, idAutor, idEditorial, archivo);
            modelo.put("exito","Registro Exitoso");
            return "registrarLibro";
        }catch(ErroresServicios e){
            
            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores",autores);
        
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("editoriales",editoriales);
             
            modelo.put("error",e.getMessage());
            modelo.put("isbn",isbn);
            modelo.put("titulo",titulo);
            modelo.put("anio",anio);
            modelo.put("ejemplares",ejemplares);
            modelo.put("archivo",archivo);
            modelo.put("idAutor",idAutor);
            modelo.put("idEditorial",idEditorial);
            //Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE,null,e);
            //System.out.println(e.getMessage());
            return "registrarLibro";
        } 
        
    }
    
    @GetMapping("/listaLibro")
    public String listaLibro(ModelMap modelo) {
        
        List<Libro> listaLibros = libroServicio.listarLibros();
        System.out.println("-+-+-+-+-+-+");
        for(Libro str : listaLibros)
        {
         //imprimimos el objeto pivote
           System.out.println(str);
           System.out.println(str.isAlta());
        }
        
        modelo.addAttribute("libros",listaLibros);
  
        return "listaLibro.html";
    }

    @GetMapping("/modificarLibro")
    public String modificarLibro(@RequestParam("id") String id,ModelMap modelo) {
        System.out.println("-----!!");
        System.out.println("-----"+id);
        Libro libro = new Libro();
        
        //if(idLibro==null || idLibro.isEmpty()){
            try{
                libro = libroServicio.buscarPorId(id);
                modelo.addAttribute("libro", libro);
            }catch (Exception e){
                Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE,null,e);
            }
        //}
        
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores",autores);
        
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales",editoriales);
        
        return "modificarLibro.html";
    }
    
    @PostMapping("/actualizarLibro")
    public String modificarLibro(ModelMap modelo,@RequestParam(required=false) String idLibro,@RequestParam(required=false) Long isbn,@RequestParam(required=false) String titulo, @RequestParam(required=false) Integer anio, @RequestParam(required=false) Integer ejemplares, @RequestParam(required=false) String idAutor, @RequestParam(required=false) String idEditorial, @RequestParam(required=false) MultipartFile archivo){
        Libro libro = null;
        
        try{
            libro = libroServicio.buscarPorId(idLibro);
            System.out.println("-->"+libro);
            libroServicio.modificarLibro(archivo, idLibro, isbn, titulo, anio, ejemplares, idAutor, idEditorial);
            return "redirect:/libro/listaLibro";
        }catch (Exception e){
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE,null,e);
            modelo.addAttribute("libro", libro);
            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores",autores);
        
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("editoriales",editoriales);
            return "modificarLibro.html";
        }
        
    }
    
    @GetMapping("/bajaLibro")
    public String bajaLibro(@RequestParam("id") String id,ModelMap modelo) {
        try{
            libroServicio.deshabilitarLibro(id);
            
            return "redirect:/libro/listaLibro";
        }catch(Exception e){
            return "redirect:/libro/listaLibro";
        }
    }
}
