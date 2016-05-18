/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brenda.libro.core;

import java.io.Serializable;

/**
 *
 * @author Ivan
 */
public class RegistroDeAvance implements Serializable{
    private int capitulo;
    private int parte;
    private int numPreguntas;
    private boolean[] contestadas;

    public RegistroDeAvance(int numPreguntas) {
        contestadas = new boolean[numPreguntas];
    }

    public int getCapitulo() {
        System.out.println("cap: " + capitulo);
        return capitulo;
    }

    public void setCapitulo(int capitulo) {
        this.capitulo = capitulo;
    }

    public int getParte() {
        System.out.println("part: " + parte);
        return parte;
    }

    public void setParte(int parte) {
        this.parte = parte;
    }

    public int getNumPreguntas() {
        System.out.println("numpreg:" + numPreguntas);
        return numPreguntas;
    }

    public void setNumPreguntas(int numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public boolean[] getContestadas() {
        return contestadas;
    }

    public void agregarContestada(int pregunta) {
        if(pregunta < contestadas.length){
            contestadas[pregunta-1] = true;
        }
    }

    @Override
    public String toString() {
        return "RegistroDeAvance{" + "capitulo=" + capitulo + ", parte=" + parte + ", numPreguntas=" + numPreguntas + "}";
    }
    
    
    
}
