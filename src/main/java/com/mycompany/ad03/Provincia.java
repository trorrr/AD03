/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ad03;

/**
 *
 * @author trorrr
 */
public class Provincia {
    private int id;
    private String nome;
    
    
    public Provincia(int _id,String _nome){
    this.id = _id;
    this.nome = _nome;
    }
    
    public Provincia() {}
    
    public int getID() {
        return id;
    }
        
    public String getNome() {
        return nome;
    }
}
