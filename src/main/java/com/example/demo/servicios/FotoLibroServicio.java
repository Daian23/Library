package com.example.demo.servicios;

import com.example.demo.entidades.FotoLibro;
import com.example.demo.errores.ErroresServicios;
import com.example.demo.repositorios.FotoLibroRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoLibroServicio {
    
    @Autowired
    private FotoLibroRepositorio fotoLibroRepositorio;
    
    @Transactional
    public FotoLibro guardar(MultipartFile archivo) throws ErroresServicios{
        if(archivo != null){
            try{
                FotoLibro foto = new FotoLibro();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                 
                return fotoLibroRepositorio.save(foto);
                 
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        
        return null;
    }
    
    public FotoLibro actualizar(String idFoto,MultipartFile archivo) throws ErroresServicios{
        if(archivo != null){
            try{
                FotoLibro foto = new FotoLibro();
                
                if(idFoto!=null){
                    Optional<FotoLibro> respuesta = fotoLibroRepositorio.findById(idFoto);
                    if(respuesta.isPresent()){
                        foto = respuesta.get();
                    }
                }
                
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                 
                return fotoLibroRepositorio.save(foto);
                 
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        
        return null;
    }
    
}
