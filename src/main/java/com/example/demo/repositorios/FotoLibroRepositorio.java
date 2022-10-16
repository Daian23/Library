/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.entidades.FotoLibro;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daiana
 */
public interface  FotoLibroRepositorio  extends JpaRepository<FotoLibro, String>{
    
}
