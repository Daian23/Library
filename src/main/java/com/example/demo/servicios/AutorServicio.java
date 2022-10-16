package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.repositorios.AutorRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public Autor registrarAutor(String nombre,String apellido) throws ErroresServicios {
        validacion(nombre,apellido);
        
        Autor autor = new Autor();
        autor.setNombre(nombre+" "+apellido);
        autor.setAlta(new Date());
        return autorRepositorio.save(autor);   
    }

    @Transactional
    public Autor modificarAutor(String id,String nombre,String apellido) throws ErroresServicios {
        
        validacion(nombre, apellido);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre+" "+apellido);
            return autorRepositorio.save(autor);
        }else {
            throw new ErroresServicios("No se encontro el autor solicitado en la bd");
        }
    }

    /*@Transactional
    public void eliminarAutor(Autor autor) throws Exception {
        Autor a = autorRepositorio.buscarAutorPorId(autor.getId());
        if (a != null) {
            autorRepositorio.delete(a);
        } else {
            throw new Exception("No se encontro el autor en la bd");
        }
    }*/
    @Transactional
    public void deshabilitarAutor(String id)throws ErroresServicios{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setBaja(new Date());
            autorRepositorio.save(autor);
        }else {
            throw new ErroresServicios("No se encontro el autor solicitado en la bd");
        }
    }
    
    @Transactional
    public void habilitarAutor(String id)throws ErroresServicios{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setBaja(null);
            autorRepositorio.save(autor);
        }else {
            throw new ErroresServicios("No se encontro el autor solicitado en la bd");
        }
    }
    
    @Transactional
    public Autor buscarPorId(String id) {
        return autorRepositorio.buscarAutorPorId(id);
    }

    @Transactional
    public List<Autor> listarLibros() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void validacion(String nombre,String apellido) throws ErroresServicios {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicios("El nombre no puede ser nulo o vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErroresServicios("El apellido no puede ser nulo o vacío");
        }
        
    }

}
