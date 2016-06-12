package com.brenda.libro.gui;

import com.brenda.libro.core.Libro;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.brenda.libro.core.Pregunta;
import com.brenda.libro.core.RegistroDeAvance;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JRadioButton;

/**
 *
 * @author Ivan
 */
public class Cuestionario extends JPanel implements ActionListener{
    public static final int ENCABEZADO_H1 = 10;
    public static final int ENCABEZADO_H2 = 20;
    
    private int conteoPreguntas;
    private boolean aprueba;
    private boolean[] evaluacion, respuestas;
    private String id;
    private JScrollPane scroll_preguntas;
    private JPanel pan_preguntas;
    private JButton btn_continuar, btn_anterior;
    private Font fuente_h1;
    private Font fuente_h2;
    private Font fuente_pregunta;
    private Font fuente_Si_No;
    private Color color_azul;
    private Color color_verde;
    private Color color_blanco;
    private RegistroDeAvance reg;
    private Pregunta pregActual;
    
    public Cuestionario(int numPreguntas, String id){
        this.id = id;
        System.out.println("cuest " + id + " noPregs = " + numPreguntas);
        aprueba = false;
        reg = Libro.controlAdv.cargarRegistro(id);
        if (reg != null) {
            evaluacion = reg.getEvaluadas();
            respuestas = reg.getRespuestas();
            if (evaluacion == null || respuestas == null) {
                System.out.println("no se pudo cargar el registro de preguntas evaluadas");
                evaluacion = new boolean[numPreguntas];
                respuestas = new boolean[numPreguntas];
                for (int i = 0; i < evaluacion.length; i++) {
                    evaluacion[i] = false;
                    respuestas[i] = false;
                }
            }
        } else {
            reg = new RegistroDeAvance(numPreguntas);
            evaluacion = new boolean[numPreguntas];
            respuestas = new boolean[numPreguntas];
                for (int i = 0; i < evaluacion.length; i++) {
                evaluacion[i] = false;
                respuestas[i] = false;
            }
        }
        reg.setNumPreguntas(numPreguntas);
        pan_preguntas = new JPanel();
        pan_preguntas.setLayout(new BoxLayout(pan_preguntas, BoxLayout.Y_AXIS));
        scroll_preguntas = new JScrollPane(pan_preguntas);
        btn_continuar = new JButton("Continuar");
        btn_anterior = new JButton("Anterior");
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
        conteoPreguntas = 0;
        setLayout(new GridLayout());
        add(scroll_preguntas);
        setBackground(Color.white);
        pan_preguntas.setBackground(Color.white);
    }
    
    public void agregarEncabezado(String text, int hNum){
        JPanel pan_header = new JPanel();
        JLabel txt_header = new JLabel(text);
        pan_header.setBackground(color_blanco);
        switch(hNum){
            case ENCABEZADO_H1:
                txt_header.setFont(fuente_h1);
                txt_header.setForeground(color_azul);
                pan_header.add(txt_header);
                break;
            case ENCABEZADO_H2:
                pan_header.setLayout(new BorderLayout());
                JLabel respSi = new JLabel("SI");
                JLabel respNo = new JLabel("NO");
                JPanel pheader = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 20));
                JPanel psiNo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
                txt_header.setFont(fuente_h2);
                txt_header.setForeground(color_verde);
                respSi.setFont(fuente_Si_No);
                respSi.setForeground(color_azul);
                respNo.setFont(fuente_Si_No);
                respNo.setForeground(color_azul);
                pheader.setBackground(color_blanco);
                psiNo.setBackground(color_blanco);
                pheader.add(txt_header);
                psiNo.add(respSi);
                psiNo.add(respNo);
                pan_header.add(pheader, BorderLayout.WEST);
                pan_header.add(psiNo, BorderLayout.EAST);
                break;
                default:
                    break;
        }
        pan_preguntas.add(pan_header);
    }
    
    public void agregarPregunta(String pregunta, int tipo, boolean importa){
        if(conteoPreguntas <= respuestas.length){
            Pregunta pan_pregunta = new Pregunta(conteoPreguntas, pregunta, tipo, respuestas[conteoPreguntas]);
            pregActual = pan_pregunta;
            pregActual.getRespSI().addActionListener(this);
            pan_preguntas.add(pan_pregunta);
            conteoPreguntas++;
            if (!importa) {
                evaluacion[conteoPreguntas-1] = true;
            }
        } else {
            System.out.println("no se pueden agregar mas preguntas");
        }
    }

    public JButton getBtn_continuar() {
        return btn_continuar;
    }

    public JButton getBtn_anterior() {
        return btn_anterior;
    }

    public boolean isAprobado() {
        return aprueba;
    }
    
    /**
     * Finalizar el cuestionario y pone un boton para validar
     */
    public void finalizar(){
        JPanel pan_boton = new JPanel();
        pan_boton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pan_boton.add(btn_anterior);
        pan_boton.add(btn_continuar);
        pan_preguntas.add(pan_boton);
        btn_continuar.addActionListener(this);
    }

    public boolean[] getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(boolean[] evaluacion) {
        this.evaluacion = evaluacion;
        System.out.println("setEvaluacion(" + Arrays.toString(this.evaluacion) + ")");
    }

    public boolean[] getRespuestas() {
        System.out.print("[ ");
            for (int i = 0; i < respuestas.length; i++) {
                System.out.print(" " + respuestas[i]);
            }System.out.println(" ]");
        return respuestas;
    }

    public void setRespuestas(boolean[] respuestas) {
        this.respuestas = respuestas;
    }

    public String getId() {
        return id;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_continuar)) {
            evaluar();
            System.out.println("aprobado: " + isAprobado());
        }
        if (e.getSource() instanceof JRadioButton){
            JRadioButton jr = (JRadioButton) e.getSource();
            Pregunta p = (Pregunta) jr.getParent().getParent();
            Pregunta.setPregunta(p);
            respuestas[Pregunta.getPregunta().getNumeroInterno()] = 
                    evaluacion[Pregunta.getPregunta().getNumeroInterno()] = true;
            System.out.println("res[" + Pregunta.getPregunta().getNumeroInterno() + "] = " + respuestas[Pregunta.getPregunta().getNumeroInterno()]);
        }
    }

    private void evaluar() {
        boolean respuesta;
        for (int i = 0; i < evaluacion.length; i++) {
                respuesta = evaluacion[i];
                System.out.println("r:" + respuesta);
                if (respuesta == true) {
                    aprueba = true;
                } else {
                    aprueba = false;
                    break;
                }
            }
    }

    public RegistroDeAvance getReg() {
        return reg;
    }

    public String getID() {
        return id;
    }
    
}
