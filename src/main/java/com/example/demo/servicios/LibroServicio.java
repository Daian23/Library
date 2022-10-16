
package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.FotoLibro;
import com.example.demo.entidades.Libro;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.repositorios.AutorRepositorio;
import com.example.demo.repositorios.EditorialRepositorio;
import com.example.demo.repositorios.LibroRepositorio;
import static java.lang.Double.isNaN;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    EditorialServicio editorialServicio;

    @Autowired
    AutorServicio autorServicio;
    
    @Autowired 
    private FotoLibroServicio fotoLibroServicio;

    @Transactional
    public Libro registrarLibro(Long isbn,String titulo,Integer anio,Integer ejemplares,String idAutor,String idEditorial,MultipartFile archivo) throws ErroresServicios {
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
         validacion(isbn,titulo,anio,ejemplares,autor,editorial);
        
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        FotoLibro foto = fotoLibroServicio.guardar(archivo);
        libro.setFotoLibro(foto);
        return libroRepositorio.save(libro);
    }

    @Transactional
    public Libro modificarLibro(MultipartFile archivo, String idLibro, Long isbn, String titulo, Integer anio, Integer ejemplares,
			String idAutor, String idEditorial) throws ErroresServicios {
        
       
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
         validacion(isbn,titulo,anio,ejemplares,autor, editorial);
        
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            String idFoto = null;
            if(libro.getFotoLibro()!=null){
                idFoto = libro.getFotoLibro().getId();
            }
            
            FotoLibro foto = fotoLibroServicio.actualizar(idFoto, archivo);
            libro.setFotoLibro(foto);

            return libroRepositorio.save(libro);
        }else {
            throw new ErroresServicios("No se encontro el libro solicitado en la bd");
        }
    }

    public void deshabilitarLibro(String idLibro)throws ErroresServicios{
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setBaja(new Date());
            libroRepositorio.save(libro);
        }else{
             throw new ErroresServicios("No se encontro el libro solicitado en la bd");
        }
    }
    
    public void habilitarLibro(String idLibro)throws ErroresServicios{
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setBaja(null);
            libroRepositorio.save(libro);
        }else{
             throw new ErroresServicios("No se encontró el libro solicitado en la bd");
        }
    }
    
    public void modificarLibro(String idLibro, Integer ejemplares)throws ErroresServicios{
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            if(ejemplares!=null){
                libro.setEjemplares(libro.getEjemplares()+ejemplares);
                libroRepositorio.save(libro);
            }
        }
    }

    @Transactional
    public Libro buscarPorId(String idLibro) {
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        System.out.println("respuesta-->"+respuesta);
        Libro libro = respuesta.get();
        System.out.println("Libro-->"+libro);
        System.out.println("---------");
        return libro;
    }

    @Transactional
    public List<Libro> listarLibros() {
        
        List<Libro> listaLibros = libroRepositorio.findAll();
        System.out.println("----------------");
        for(Libro str : listaLibros)
        {
           System.out.println(str);
        }
        System.out.println("----------------");
        return listaLibros;
    }
    
    @Transactional
    public void validacion(Long isbn, String titulo, Integer anio, Integer ejemplares,Autor autor, Editorial editorial) throws ErroresServicios {
        if (isbn == null || isNaN(isbn)== true || isbn < 0) {
            throw new ErroresServicios("El isbn no puede ser nulo o menor a 0");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErroresServicios("El titulo no puede ser nulo o vacío");
        }
        if (anio == null || anio < 0) {
            throw new ErroresServicios("El anio no puede ser nulo o menor a 0");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new ErroresServicios("Los ejemplares no puede ser nulos o menores a 0");
        }
        if(autor == null){
            throw new ErroresServicios("No se encontró el autor solicitado");
        }
        
        if(editorial == null){
            throw new ErroresServicios("No se encontró el editorial solicitado");
        }
    }

}
