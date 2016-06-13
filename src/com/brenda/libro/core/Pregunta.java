package com.brenda.libro.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
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
public class Pregunta extends JPanel implements ActionListener, FocusListener{
    public static final int TIPO_SI_NO = 1;
    public static final int TIPO_REGISTRAR = 2;
    public static int inicial = 1;
    
    private final int TAMAÑO_ORACION = 100;
    private final String INICIO_HMTL = "<html><body><p>";
    private final String FINAL_HMTL = "</p></body></html>";
    private final String SALTO = "<br />";
    private final String CARPETA = "registros/";
    private final String EXT = ".txt";
    
    private static Pregunta p = new Pregunta();
    
    private int numeroInterno, tipo;
    private boolean respondida;
    private String id;
    private JLabel texto;
    private JRadioButton preg_SI, preg_NO;
    private Font fuente_pregunta;
    private Color color_azul;
    private Color color_blanco;

    public Pregunta(int numeroInterno, String texto, int tipo, boolean respondida) {
        this.numeroInterno = numeroInterno;
        this.respondida = respondida;
        this.texto = new JLabel(formatearTexto(texto));
        this.tipo = tipo;
        preg_SI = new JRadioButton("");
        setPregunta(p);
        inicializarComponentes();
    }
    
    public Pregunta (){
        this(0, "", Pregunta.TIPO_SI_NO, false);
    }
    
    public static Pregunta getPregunta(){
        return p;
    }
    
    public static void setPregunta(Pregunta preg){
        p = preg;
    }
    
    private void inicializarComponentes(){
        id = "Registro " + inicial + numeroInterno;
        JPanel panPregunta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panRespuesta = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 1));
        
        preg_NO = new JRadioButton("");
        ButtonGroup buttG =  new ButtonGroup();
        JTextArea registro = new JTextArea(5, 30);
        fuente_pregunta = new Font("Tahoma", Font.PLAIN, 14);
        color_azul = new Color(0, 0, 128);
        color_blanco = new Color(255, 255, 255);
        
        if (respondida) {
            preg_SI.setSelected(true);
        } else {
            preg_NO.setSelected(true);
        }
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
                preg_NO.addActionListener(this);
                break;
            case TIPO_REGISTRAR:
                registro.setText(cargarTexto(id));
                panRespuesta.add(registro);
                registro.addFocusListener(this);
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
        if (e.getSource().equals(preg_NO) && preg_NO.isSelected()) {
            respondida = false;
        }
    }
    
    private String cargarTexto(String id){
        File f = new File("registros/" + id + ".txt");
        StringBuilder sb = new StringBuilder();
        try{
            Scanner sc = new Scanner(f);

            while(sc.hasNextLine()){
                sb.append(sc.nextLine()).append("\n");
            }
            sc.close();
        } catch(IOException ex){
            System.out.println("ERROR: " + ex.getLocalizedMessage());
            try{
                f.createNewFile();
            } catch (IOException ex2){}
        }
        return sb.toString();
    }
    
    private void guardarRegistro(String text, String id){
        File f = new File("registros/" + id + ".txt");
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(text);
            writer.close();
        } catch(IOException ioe){
            System.out.println(ioe.getLocalizedMessage());
            try{
                f.createNewFile();
                guardarRegistro(text, id);
            } catch(IOException e){
                System.out.println("ERROR " + e.getLocalizedMessage());
            }
        }
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextArea jt = (JTextArea)e.getComponent();
        guardarRegistro(jt.getText(), id);
    }
}
