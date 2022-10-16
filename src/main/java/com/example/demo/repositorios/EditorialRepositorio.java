/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daiana
 */
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,String>{
    
    @Query("SELECT e FROM Editorial e WHERE e.id= :id")
    public Editorial buscarEditorialPorId(@Param("id") String id);
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre ")
    public Editorial buscarEditorialPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT e FROM Editorial e ORDER BY e.nombre ASC")
    public List<Editorial> buscarTodos();
}
