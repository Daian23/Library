/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.entidades.Autor;
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
public interface AutorRepositorio extends JpaRepository<Autor,String>{
    
    @Query("SELECT a FROM Autor a WHERE a.id= :id")
    public Autor buscarAutorPorId(@Param("id") String id);
    
    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre")
    public Autor buscarAutorPorNombre(@Param("nombre") String nombre );
    
    @Query("SELECT a FROM Autor a ORDER BY a.nombre ASC")
    public List<Autor>buscarTodos();
}
