package com.brenda.libro.core;

import javax.swing.JLabel;

/**
 * Representa una pregunta que contiene texto y un numero que la identifica de las demas
 * @author ""
 */
public class Pregunta extends JLabel{
    private final int TAMAÑO_ORACION = 100;
    private final String INICIO_HMTL = "<html><body><p>";
    private final String FINAL_HMTL = "</p></body></html>";
    private final String SALTO = "<br />";
    
    private int numeroInterno;

    public Pregunta(int numeroInterno, String texto) {
        this.numeroInterno = numeroInterno;
        setText(formatearTexto(texto));
    }
    
    private String formatearTexto(String texto){
        StringBuilder cadena = new StringBuilder();
        char[] c = texto.toCharArray();
        cadena.append(INICIO_HMTL);
        if(c.length > TAMAÑO_ORACION){
            int j = 0;
            for (int i = 0; i < c.length; i++) {
                cadena.append(c[i]);
                j++;
                if (c[i] == ' ' && (TAMAÑO_ORACION - j) < 5) {
                    cadena.append(SALTO);
                    j = 0;
                } else if (j == TAMAÑO_ORACION) {
                    cadena.append(SALTO);
                    j = 0;
                }
            }
            cadena.append(FINAL_HMTL);
        } else{
            cadena.append(texto);
            cadena.append(FINAL_HMTL);
            cadena.setLength(TAMAÑO_ORACION);
        }
        return cadena.toString();
    }

    @Override
    public String toString() {
        return getText();
    }

    public int getNumeroInterno() {
        return numeroInterno;
    }
    
}
