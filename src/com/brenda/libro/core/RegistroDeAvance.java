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
    private Boolean[] evaluadas, respuestas;

    public RegistroDeAvance(int numPregs){
        capitulo = 1;
        parte = 1;
        numPreguntas = 1;
        evaluadas = new Boolean[numPregs];
        respuestas = new Boolean[numPregs];
        for (int i = 0; i < evaluadas.length; i++) {
            evaluadas[i] = false;
            respuestas[i] = false;
        }
        System.out.println("nuevo Registro de avance");
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
        return numPreguntas;
    }

    public void setNumPreguntas(int numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public boolean[] getEvaluadas() {
        boolean[] resp = new boolean[evaluadas.length];
        for (int i = 0; i < evaluadas.length; i++) {
            resp[i] = evaluadas[i];
        }
        return resp;
    }

    public void setEvaluadas(boolean[] contestadas) { 
        this.evaluadas = new Boolean[contestadas.length];
        for (int i = 0; i < contestadas.length; i++) {
            this.evaluadas[i] = contestadas[i];
        }
    }

    public boolean[] getRespuestas() {
        boolean[] resp = new boolean[respuestas.length];
        for (int i = 0; i < respuestas.length; i++) {
            resp[i] = respuestas[i];
        }
        return resp;
    }

    public void setRespuestas(boolean[] respuestas) {
        this.respuestas = new Boolean[respuestas.length];
        for (int i = 0; i < respuestas.length; i++) {
            this.respuestas[i] = respuestas[i];
        }
    }

    @Override
    public String toString() {
        return "RegistroDeAvance{" + "capitulo=" + capitulo + ", parte=" + parte 
                + ", numPreguntas=" + numPreguntas + ", evaluadas=" + evaluadas 
                + ", respuestas=" + respuestas + "}";
    }
    
    
    
}
