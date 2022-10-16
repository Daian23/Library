/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.entidades.Autor;
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
public interface LibroRepositorio extends JpaRepository<Libro,String>{
    
    @Query("SELECT l FROM Libro l ORDER BY l.titulo ASC")
    public  List<Libro> buscarTodosLibros();
    
    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarLibroPorId(@Param("id") String id);
    
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro buscarLibroPorIsbn(@Param("isbn") Long isbn);
    
    @Query("SELECT l FROM Libro l WHERE l.autor= :autor")
    public List<Libro> buscarLibroPorAutor(@Param("autor")Autor autor);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial= :editorial")
    public List<Libro> buscarLibroPorEditorial(@Param("editorial")Editorial editorial);
    
}
