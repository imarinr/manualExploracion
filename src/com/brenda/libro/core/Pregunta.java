package com.brenda.libro.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * Representa una pregunta que contiene texto y un numero que la identifica de las demas
 * @author ""
 */
public class Pregunta extends JPanel{
    public static final int TIPO_SI_NO = 1;
    public static final int TIPO_REGISTRAR = 2;
    
    private final int TAMAÑO_ORACION = 100;
    private final String INICIO_HMTL = "<html><body><p>";
    private final String FINAL_HMTL = "</p></body></html>";
    private final String SALTO = "<br />";
    
    private JLabel texto;
    private int numeroInterno, tipo;
    private Font fuente_pregunta;
    private Color color_azul;
    private Color color_blanco;

    public Pregunta(int numeroInterno, String texto, int tipo) {
        this.numeroInterno = numeroInterno;
        this.texto = new JLabel(formatearTexto(texto));
        this.tipo = tipo;
        inicializarComponentes();
    }
    
    private void inicializarComponentes(){
        JPanel panPregunta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panRespuesta = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 1));
        JRadioButton preg_SI = new JRadioButton("");
        JRadioButton preg_NO = new JRadioButton("");
        ButtonGroup buttG =  new ButtonGroup();
        JTextArea registro = new JTextArea();
        fuente_pregunta = new Font("Tahoma", Font.PLAIN, 14);
        color_azul = new Color(0, 0, 128);
        color_blanco = new Color(255, 255, 255);
        
        setLayout(new BorderLayout());
        setBackground(color_blanco);
        buttG.add(preg_SI);
        buttG.add(preg_NO);
        preg_NO.setBackground(color_blanco);
        preg_SI.setBackground(color_blanco);
        panPregunta.setBackground(color_blanco);
        panRespuesta.setBackground(color_blanco);
        texto.setBackground(color_blanco);
        texto.setForeground(color_azul);
        texto.setFont(fuente_pregunta);
        
        switch(tipo){
            //depende del tipo el como se acomode la pregunta
            case TIPO_SI_NO:
                panPregunta.add(texto);
                panRespuesta.add(preg_NO);
                panRespuesta.add(preg_SI);
                add(panPregunta, BorderLayout.WEST);
                add(panRespuesta, BorderLayout.EAST);
                break;
            case TIPO_REGISTRAR:
                break;
        }
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
        }
        return cadena.toString();
    }

    @Override
    public String toString() {
        return texto.getText();
    }

    public int getNumeroInterno() {
        return numeroInterno;
    }
    
}
