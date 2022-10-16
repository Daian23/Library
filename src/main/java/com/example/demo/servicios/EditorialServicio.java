package com.example.demo.servicios;

import com.example.demo.entidades.Editorial;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.repositorios.EditorialRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public Editorial RegistrarEditorial(String nombre) throws ErroresServicios {
        validacion(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(new Date());
        return editorialRepositorio.save(editorial);
    }

    @Transactional
    public Editorial modificarEditorial(String nombre,String id) throws  ErroresServicios{
        
        validacion(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            return editorialRepositorio.save(editorial);
        }else{
             throw new ErroresServicios("No se encontró la editorial solicitada en la bd");
        }
    }

    /*@Transactional
    public void eliminarEditorial(String id) throws ErroresServicios {
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial e = respuesta.get();
            editorialRepositorio.delete(e);
        }else{
             throw new ErroresServicios("No se encontró la editorial solicitada");
        }
      
    }*/
    
    @Transactional
    public void deshabilitarEditorial(String id)throws ErroresServicios{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setBaja(new Date());
            editorialRepositorio.save(editorial);
        }else{
             throw new ErroresServicios("No se encontró la editorial solicitada en la bd");
        }
    }
    
    @Transactional
    public void habilitarEditorial(String id)throws ErroresServicios{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setBaja(null);
            editorialRepositorio.save(editorial);
        }else{
             throw new ErroresServicios("No se encontró la editorial solicitada en la bd");
        }
    }

    @Transactional
    public Editorial buscarPorId(String id) {
        return editorialRepositorio.buscarEditorialPorId(id);
    }

    @Transactional
    public List<Editorial> listarEditorial() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void validacion(String nombre) throws ErroresServicios {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicios("El nombre no puede ser nulo o vacío");
        }
    }

}
