package com.brenda.libro.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.brenda.libro.core.Pregunta;

/**
 *
 * @author Ivan
 */
public class Cuestionario extends JPanel{
    public static final int TIPO_SI_NO = 1;
    public static final int TIPO_REGISTRAR = 2;
    public static final int ENCABEZADO_H1 = 10;
    public static final int ENCABEZADO_H2 = 20;
    
    private int conteoPreguntas;
    private boolean[] respuestas;
    private JScrollPane scroll_preguntas;
    private JPanel pan_preguntas;
    private JButton btn_continuar;
    private Font fuente_h1;
    private Font fuente_h2;
    private Font fuente_pregunta;
    private Font fuente_Si_No;
    private Color color_azul;
    private Color color_verde;
    private Color color_blanco;
    
    public Cuestionario(int numPreguntas){
        respuestas = new boolean[numPreguntas];
        pan_preguntas = new JPanel();
        pan_preguntas.setLayout(new BoxLayout(pan_preguntas, BoxLayout.Y_AXIS));
        scroll_preguntas = new JScrollPane(pan_preguntas);
        btn_continuar = new JButton("Continuar");
        fuente_h1 = new Font("Tahoma", Font.BOLD, 24);
        fuente_h2 = new Font("Tahoma", 1, 18);
        fuente_pregunta = new Font("Tahoma", Font.PLAIN, 14);
        fuente_Si_No = new Font("Tahoma", Font.BOLD, 14);
        color_azul = new Color(0, 0, 128);
        color_verde = new Color(0, 188, 133);
        color_blanco = new Color(255, 255, 255);
        inicializar();
    }

    private void inicializar() {
        setLayout(new GridLayout());
        add(scroll_preguntas);
        setBackground(Color.white);
        pan_preguntas.setBackground(Color.white);
        conteoPreguntas = 0;
        for (int i = 0; i < respuestas.length; i++) {
            respuestas[i] = true;
        }
    }
    
    public void agregarEncabezado(String text, int hNumber){
        JPanel pan_header = new JPanel();
        JLabel txt_header = new JLabel(text);
        pan_header.setBackground(color_blanco);
        switch(hNumber){
            case ENCABEZADO_H1:
                txt_header.setFont(fuente_h1);
                txt_header.setForeground(color_azul);
                pan_header.add(txt_header);
                break;
            case ENCABEZADO_H2:
                JLabel respSi = new JLabel("SI");
                JLabel respNo = new JLabel("NO");
                pan_header.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                txt_header.setFont(fuente_h2);
                txt_header.setForeground(color_verde);
                respSi.setFont(fuente_Si_No);
                respSi.setForeground(color_azul);
                respNo.setFont(fuente_Si_No);
                respNo.setForeground(color_azul);
                //poner a la vista
                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 2;
                c.gridheight = 1;
                c.weightx = 1.0;
                c.anchor = GridBagConstraints.WEST;
                pan_header.add(txt_header, c);
                c.gridx = 2;
                c.gridy = 0;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.weightx = 1.0;
                c.anchor = GridBagConstraints.CENTER;
                pan_header.add(respSi);
                c.gridx = 3;
                c.gridy = 0;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.weightx = 1.0;
//                c.anchor = GridBagConstraints.WEST;
                pan_header.add(respNo, c);
                break;
                default:
                    break;
        }
        pan_preguntas.add(pan_header);
    }
    
    public void agregarPregunta(String pregunta, int tipo, boolean importa){
        JPanel pan_pregunta = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Pregunta txt_pregunta = new Pregunta(conteoPreguntas, pregunta);
        JRadioButton preg_SI = new JRadioButton("");
        JRadioButton preg_NO = new JRadioButton("");
        ButtonGroup buttG =  new ButtonGroup();
        JTextArea registro = new JTextArea();//va aqui?
        buttG.add(preg_SI);
        buttG.add(preg_NO);
        preg_NO.setBackground(color_blanco);
        preg_SI.setBackground(color_blanco);
        pan_pregunta.setBackground(color_blanco);
        txt_pregunta.setForeground(color_azul);
        txt_pregunta.setFont(fuente_pregunta);
        
        switch(tipo){
            //depende del tipo el como se acomode la pregunta
            case TIPO_SI_NO:
                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 2;
                c.gridheight = 1;
                c.weightx = 1.0;
                c.anchor = GridBagConstraints.WEST;
                pan_pregunta.add(txt_pregunta, c);
                c.gridx = 2;
                c.gridy = 0;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.weightx = 1.0;
                c.anchor = GridBagConstraints.CENTER;
//                teamo
                pan_pregunta.add(preg_SI, c);
                c.gridx = 3;
                c.gridy = 0;
                c.gridwidth = 2;
                c.gridheight = 1;
                c.weightx = 1.0;
                c.anchor = GridBagConstraints.CENTER;
                pan_pregunta.add(preg_NO, c);
                break;
            case TIPO_REGISTRAR:
                break;
        }
        pan_preguntas.add(pan_pregunta);
        conteoPreguntas++;
    }
    
    /**
     * Finalizar el cuestionario y pone un boton para validar
     */
    public void finalizar(){
        JPanel pan_boton = new JPanel();
        pan_boton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pan_boton.add(btn_continuar);
        pan_preguntas.add(pan_boton);
    }
}
