package com.brenda.libro.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * Representa una pregunta que contiene texto y un numero que la identifica de las demas
 * @author ""
 */
public class Pregunta extends JPanel implements ActionListener{
    public static final int TIPO_SI_NO = 1;
    public static final int TIPO_REGISTRAR = 2;
    
    private final int TAMAÑO_ORACION = 100;
    private final String INICIO_HMTL = "<html><body><p>";
    private final String FINAL_HMTL = "</p></body></html>";
    private final String SALTO = "<br />";
    
    private static Pregunta p = new Pregunta();
    private int numeroInterno, tipo;
    private JLabel texto;
    private JRadioButton preg_SI, preg_NO;
    private Font fuente_pregunta;
    private Color color_azul;
    private Color color_blanco;

    public Pregunta(int numeroInterno, String texto, int tipo) {
        this.numeroInterno = numeroInterno;
        this.texto = new JLabel(formatearTexto(texto));
        this.tipo = tipo;
        inicializarComponentes();
    }
    
    public Pregunta (){
        this(0, "", Pregunta.TIPO_SI_NO);
    }
    
    public static Pregunta getPregunta(){
        return p;
    }
    
    private void setPregunta(Pregunta preg){
        p = null;
        p = preg;
    }
    
    private void inicializarComponentes(){
        JPanel panPregunta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panRespuesta = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 1));
        preg_SI = new JRadioButton("");
        preg_NO = new JRadioButton("");
        ButtonGroup buttG =  new ButtonGroup();
        JTextArea registro = new JTextArea(5, 30);
        fuente_pregunta = new Font("Tahoma", Font.PLAIN, 14);
        color_azul = new Color(0, 0, 128);
        color_blanco = new Color(255, 255, 255);
        
        registro.setBorder(new LineBorder(color_azul));
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
        panPregunta.add(texto);
        add(panPregunta, BorderLayout.WEST);
        
        switch(tipo){
            //depende del tipo el como se acomode la pregunta
            case TIPO_SI_NO:
                panRespuesta.add(preg_SI);
                panRespuesta.add(preg_NO);
                preg_SI.addActionListener(this);
                preg_NO.addActionListener(this);
                break;
            case TIPO_REGISTRAR:
                panRespuesta.add(registro);
                break;
        }
        add(panRespuesta, BorderLayout.EAST);
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
    
    public JRadioButton getRespSI(){
        return preg_SI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            setPregunta(this);
    }
    
}
