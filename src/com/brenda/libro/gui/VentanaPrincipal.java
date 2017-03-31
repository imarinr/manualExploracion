package com.brenda.libro.gui;

import com.brenda.libro.core.Libro;
import com.brenda.libro.core.RegistroDeAvance;
import com.brenda.libro.core.Pregunta;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * La ventana principal de la aplicacion
 * @author imarinr
 */
public class VentanaPrincipal extends JFrame implements ActionListener,
        ListSelectionListener, WindowListener, WindowStateListener{

    private final String ID ="c0";
    private int capituloSeleccionado = 0;
    private int pagActual = 1;
    private int capActual, parteActual;
    private Pantalla1 pan_pan1;
    private PantallaTexto pan_text;
    private Cuestionario pan_Cuest11, pan_Cuest12, pan_Cuest21, pan_Cuest22, pan_Cuest31, pan_Cuest41, pan_Cuest42, pan_Cuest43;
    private LectorPDF lector;
    private JButton btn_anterior, btn_sigPag, btn_cont;
    private JScrollPane js, scroll;
    private JDialog pantallaCreditos;
    JList<Object> list_capitulos;
    RegistroDeAvance regGen;
    Stack<Container> stackPantallas;

    public VentanaPrincipal(String title) throws HeadlessException {
        super(title);
        stackPantallas = new Stack<>();
        this.inicializarComponentes();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int paginas = 0;
        JButton jb = (JButton)e.getSource();
        if(lector != null){
            paginas = lector.getNumPags();
        }
        if (e.getSource().equals(btn_anterior)) {
            pagActual -= 1;
            if (pagActual > paginas || pagActual < 1) {
                pagActual = 1;
            }
            lector.verPagina(pagActual);
        }
        if (e.getSource().equals(btn_sigPag)) {
            pagActual += 1;
            if (pagActual > paginas || pagActual < 1) {
                pagActual = 1;
            }
            lector.verPagina(pagActual);
        }
        if (e.getSource().equals(btn_cont)) {
            switch(capActual){
                case 1:
                    switch(parteActual){
                        case 1:
                                js.setViewportView(pan_Cuest11);
                                pan_Cuest11.setVisible(true);
                                repaint();
                                break;
                        case 2:
                                js.setViewportView(pan_Cuest12);
                                pan_Cuest12.setVisible(true);
                                repaint();
                                break;
                            }
                    setActual(js);
                    if (parteActual == 3) {
                        if(pan_Cuest12.isAprobado()){
                            JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
                            setActual(pan_Cuest21);
                            capActual = 2;
                            parteActual = 1;
                            regGen.setParte(parteActual);
                            regGen.setCapitulo(capActual);
                            pan_Cuest12.getReg().setEvaluadas(pan_Cuest12.getEvaluacion());
                            pan_Cuest12.getReg().setRespuestas(pan_Cuest12.getRespuestas());
                            Libro.controlAdv.guardarAvance(regGen, ID);
                            Libro.controlAdv.guardarAvance(pan_Cuest12.getReg(), pan_Cuest12.getID());
                        }else{
                            capActual = 1;
                            parteActual = 3;
                            JOptionPane.showMessageDialog(null, "Vuelve al capitulo 1", null, JOptionPane.ERROR_MESSAGE);
                            setActual(list_capitulos);
                            regGen.setParte(parteActual);
                            pan_Cuest12.getReg().setEvaluadas(pan_Cuest12.getEvaluacion());
                            pan_Cuest12.getReg().setRespuestas(pan_Cuest12.getRespuestas());
                            Libro.controlAdv.guardarAvance(regGen, ID);
                            Libro.controlAdv.guardarAvance(pan_Cuest12.getReg(), pan_Cuest12.getID());
                        }
                    }
                    break;
                case 2:
                    switch(parteActual){
                        case 1:
                            //inicia con cuestionario
                            if(pan_Cuest21.isAprobado()){
                                String[] opciones = {"Continuar", "Inicio"};
                                switch(JOptionPane.showOptionDialog(null, "Puedes continuar a la siguiente parte", "Mensaje", JOptionPane.YES_NO_OPTION, 
                                        JOptionPane.INFORMATION_MESSAGE, null, opciones, null)){
                                    case JOptionPane.YES_OPTION:
                                        setActual(pan_Cuest22);
                                        capActual = 2;
                                        parteActual = 2;//?
                                        break;
                                    case JOptionPane.NO_OPTION:
                                        setActual(list_capitulos);
                                        capActual = 2;
                                        parteActual = 2;//?
                                        break;
                                }
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest21.getReg().setEvaluadas(pan_Cuest21.getEvaluacion());
                                pan_Cuest21.getReg().setRespuestas(pan_Cuest21.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest21.getReg(), pan_Cuest21.getID());
                                
                            } else {
                                parteActual = 1;
                                capActual = 2;
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest21.getReg().setEvaluadas(pan_Cuest21.getEvaluacion());
                                pan_Cuest21.getReg().setRespuestas(pan_Cuest21.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest21.getReg(), pan_Cuest21.getID());
                                JOptionPane.showMessageDialog(null, "Regresa al capitulo 2");
                                setActual(list_capitulos);
                            }
                            break;
//                        case 2:
//                            if(pan_Cuest22.isAprobado()){
//                                capActual = 3;
//                                parteActual = 1;
//                                JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
//                                regGen.setParte(parteActual);
//                                regGen.setCapitulo(capActual);
//                                Libro.controlAdv.guardarAvance(regGen, ID);
//                                Libro.controlAdv.guardarAvance(pan_Cuest22.getReg(), pan_Cuest22.getID());
//                                setActual(list_capitulos);
//                            }else{
//                                capActual = 2;
//                                parteActual = 2;
//                                JOptionPane.showMessageDialog(null, "Vuelve al capitulo 2", null, JOptionPane.ERROR_MESSAGE);
//                                setActual(list_capitulos);
//                                regGen.setParte(parteActual);
//                                regGen.setCapitulo(capActual);
//                                Libro.controlAdv.guardarAvance(regGen, ID);
//                                Libro.controlAdv.guardarAvance(pan_Cuest22.getReg(), pan_Cuest22.getID());
//                            }
                            
                    }
                    break;
                case 3:
                    boolean aprueba = pan_Cuest31.isAprobado();
                    if (aprueba) {
                        parteActual = 1;
                        capActual = 4;
                        JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
                        regGen.setParte(parteActual);
                        regGen.setCapitulo(capActual);
                        pan_Cuest31.getReg().setEvaluadas(pan_Cuest31.getEvaluacion());
                        pan_Cuest31.getReg().setRespuestas(pan_Cuest31.getRespuestas());
                        Libro.controlAdv.guardarAvance(regGen, ID);
                        Libro.controlAdv.guardarAvance(pan_Cuest31.getReg(), pan_Cuest31.getID());
                        setActual(pan_Cuest41);
                    }else{
                        JOptionPane.showMessageDialog(null, "Vuelve al capitulo 3", null, JOptionPane.ERROR_MESSAGE);
                        setActual(list_capitulos);
                        regGen.setParte(parteActual);
                        regGen.setCapitulo(capActual);
                        pan_Cuest31.getReg().setEvaluadas(pan_Cuest31.getEvaluacion());
                        pan_Cuest31.getReg().setRespuestas(pan_Cuest31.getRespuestas());
                        Libro.controlAdv.guardarAvance(regGen, ID);
                        Libro.controlAdv.guardarAvance(pan_Cuest31.getReg(), pan_Cuest31.getID());
                    }
                    break;
                case 4:
                    
                    switch(parteActual){
                        case 1:
                            aprueba = pan_Cuest41.isAprobado();
                            if (aprueba) {
                                parteActual = 2;
                                capActual = 4;
                                JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest41.getReg().setEvaluadas(pan_Cuest41.getEvaluacion());
                                pan_Cuest41.getReg().setRespuestas(pan_Cuest41.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest41.getReg(), pan_Cuest41.getID());
                                setActual(pan_Cuest42);
                            }else{
                                JOptionPane.showMessageDialog(null, "Vuelve al capitulo 4", null, JOptionPane.ERROR_MESSAGE);
                                setActual(list_capitulos);
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest41.getReg().setEvaluadas(pan_Cuest41.getEvaluacion());
                                pan_Cuest41.getReg().setRespuestas(pan_Cuest41.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest41.getReg(), pan_Cuest41.getID());
                            }
                            break;
                        case 2:
                            aprueba = pan_Cuest42.isAprobado();
                            if (aprueba) {
                                parteActual = 3;
                                capActual = 4;
                                JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest42.getReg().setEvaluadas(pan_Cuest42.getEvaluacion());
                                pan_Cuest42.getReg().setRespuestas(pan_Cuest42.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest42.getReg(), pan_Cuest42.getID());
                                setActual(pan_Cuest43);
                            }else{
                                JOptionPane.showMessageDialog(null, "Vuelve al capitulo 4", null, JOptionPane.ERROR_MESSAGE);
                                setActual(list_capitulos);
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest42.getReg().setEvaluadas(pan_Cuest42.getEvaluacion());
                                pan_Cuest42.getReg().setRespuestas(pan_Cuest42.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest42.getReg(), pan_Cuest42.getID());
                            }
                            break;
                        case 3:
                            aprueba = pan_Cuest43.isAprobado();
                            if (aprueba) {
                                parteActual = 4;
                                capActual = 4;
                                JOptionPane.showMessageDialog(null, "Has completado el manual");
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest43.getReg().setEvaluadas(pan_Cuest43.getEvaluacion());
                                pan_Cuest43.getReg().setRespuestas(pan_Cuest43.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest43.getReg(), pan_Cuest43.getID());
                                setActual(list_capitulos);
                            }else{
                                JOptionPane.showMessageDialog(null, "Vuelve al capitulo 4", null, JOptionPane.ERROR_MESSAGE);
                                setActual(list_capitulos);
                                regGen.setParte(parteActual);
                                regGen.setCapitulo(capActual);
                                pan_Cuest43.getReg().setEvaluadas(pan_Cuest43.getEvaluacion());
                                pan_Cuest43.getReg().setRespuestas(pan_Cuest43.getRespuestas());
                                Libro.controlAdv.guardarAvance(regGen, ID);
                                Libro.controlAdv.guardarAvance(pan_Cuest43.getReg(), pan_Cuest43.getID());
                            }
                            break;
                        case 4:
                            setActual(list_capitulos);
                    }
                    break;
                    
            }
        }
        
        if (e.getSource().equals(pan_Cuest11.getBtn_continuar())) {
            boolean aprueba = pan_Cuest11.isAprobado();
            String[] opciones = {"Continuar", "Inicio"};
            pan_Cuest11.getReg().setEvaluadas(pan_Cuest11.getEvaluacion());
            pan_Cuest11.getReg().setRespuestas(pan_Cuest11.getRespuestas());
            if (aprueba) {
                capActual = 1;
                parteActual = 2;
                switch(JOptionPane.showOptionDialog(null, "Puedes continuar a la segunda parte", "Mensaje", JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, null, opciones, null)){
                    case JOptionPane.YES_OPTION:
                        lector = new LectorPDF("doc/docs/i17.pdf");
                        scroll.setViewportView(lector);
                        repaint();
                        repaint();
                        scroll.setViewportView(lector);
                        pan_text.add(scroll, BorderLayout.CENTER);
                        setActual(pan_text);
                        break;
                    case JOptionPane.NO_OPTION:
                        setActual(list_capitulos);
                        break;
                }
                regGen.setParte(parteActual);
                regGen.setCapitulo(capActual);
                pan_Cuest11.getReg().setEvaluadas(pan_Cuest11.getEvaluacion());
                pan_Cuest11.getReg().setRespuestas(pan_Cuest11.getRespuestas());
                Libro.controlAdv.guardarAvance(regGen, ID);
                Libro.controlAdv.guardarAvance(pan_Cuest11.getReg(), pan_Cuest11.getID());
            } else {
                pan_Cuest11.getReg().setEvaluadas(pan_Cuest11.getEvaluacion());
                pan_Cuest11.getReg().setRespuestas(pan_Cuest11.getRespuestas());
                Libro.controlAdv.guardarAvance(pan_Cuest11.getReg(), pan_Cuest11.getID());
                JOptionPane.showMessageDialog(null, "Regresa al primer capitulo", null, JOptionPane.ERROR_MESSAGE);
                setActual(list_capitulos);
            }
        }
        if (e.getSource().equals(pan_Cuest12.getBtn_continuar())) {
            lector = new LectorPDF("doc/docs/i21 - 26.pdf");
            capActual = 1;
            parteActual = 3;//?
            scroll.setViewportView(lector);
            repaint();
            repaint();
            scroll = new JScrollPane(lector);
            pan_text.add(scroll, BorderLayout.CENTER);
            setActual(pan_text);
        }
        if (e.getSource().equals(pan_Cuest21.getBtn_continuar())) {
            lector = new LectorPDF("doc/docs/i30 - 34.pdf");
            scroll.setViewportView(lector);
            repaint();
            repaint();
            scroll = new JScrollPane(lector);
            pan_text.add(scroll, BorderLayout.CENTER);
            setActual(pan_text);
        }
        if (e.getSource().equals(pan_Cuest22.getBtn_continuar())) {
            boolean aprueba = pan_Cuest22.isAprobado();
            System.out.println("22:" + aprueba);
            String[] opciones = {"Continuar", "Inicio"};
            pan_Cuest22.getReg().setEvaluadas(pan_Cuest22.getEvaluacion());
            pan_Cuest22.getReg().setRespuestas(pan_Cuest22.getRespuestas());
            if (aprueba) {
                capActual = 3;
                parteActual = 1;
                switch(JOptionPane.showOptionDialog(null, "Puedes continuar a la siguiente parte", "Mensaje", JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, null, opciones, null)){
                    case JOptionPane.YES_OPTION:
                        setActual(pan_Cuest31);
                        break;
                    case JOptionPane.NO_OPTION:
                        setActual(list_capitulos);
                        break;
                }
                regGen.setParte(parteActual);
                regGen.setCapitulo(capActual);
                Libro.controlAdv.guardarAvance(regGen, ID);
                Libro.controlAdv.guardarAvance(pan_Cuest22.getReg(), pan_Cuest22.getID());
            } else {
                pan_Cuest22.getReg().setEvaluadas(pan_Cuest22.getEvaluacion());
                pan_Cuest22.getReg().setRespuestas(pan_Cuest22.getRespuestas());
                Libro.controlAdv.guardarAvance(pan_Cuest22.getReg(), pan_Cuest22.getID());
                Libro.controlAdv.guardarAvance(regGen, ID);
                JOptionPane.showMessageDialog(null, "Regresa al capitulo 2", null, JOptionPane.ERROR_MESSAGE);
                setActual(list_capitulos);
            }
        }
        if (e.getSource().equals(pan_Cuest31.getBtn_continuar())) {
            lector = new LectorPDF("doc/docs/i44.pdf");
            scroll.setViewportView(lector);
            repaint();
            repaint();
            scroll = new JScrollPane(lector);
            pan_text.add(scroll, BorderLayout.CENTER);
            setActual(pan_text);
        }
        if (e.getSource().equals(pan_Cuest41.getBtn_continuar())) {
            lector = null;
            lector = new LectorPDF("doc/docs/i46.pdf");
            scroll.setViewportView(lector);
            repaint();
            repaint();
            scroll = new JScrollPane(lector);
            pan_text.add(scroll, BorderLayout.CENTER);
            setActual(pan_text);
        }
        if (e.getSource().equals(pan_Cuest42.getBtn_continuar())) {
            lector = new LectorPDF("doc/docs/i48 - 52.pdf");
            scroll.setViewportView(lector);
            repaint();
            repaint();
            scroll = new JScrollPane(lector);
            pan_text.add(scroll, BorderLayout.CENTER);
            setActual(pan_text);
        }
        if (e.getSource().equals(pan_Cuest43.getBtn_continuar())) {
            lector = new LectorPDF("doc/docs/i54-76.pdf");
            scroll.setViewportView(lector);
            repaint();
            repaint();
            scroll = new JScrollPane(lector);
            pan_text.add(scroll, BorderLayout.CENTER);
            setActual(pan_text);
        }
        if (jb.getName() != null) {
            if (jb.getName().equals("anterior")) {
            stackPantallas.pop();
            setActual(stackPantallas.pop());
        }
            if(jb.getName().equals("inicio")){
                stackPantallas.removeAllElements();
                stackPantallas.add(list_capitulos);
                setActual(list_capitulos);
            }
        }
    }

    /**
     * <p>Inicializa los componentes de la ventana y todas sus propiedades</p>
     */
    private void inicializarComponentes() {
        //declaraciones
        regGen = Libro.controlAdv.cargarRegistro(ID);
        if (regGen == null) {
            regGen = new RegistroDeAvance(1);
            regGen.setParte(1);
            regGen.setCapitulo(1);
        }
        pan_pan1 = new Pantalla1();
        list_capitulos = pan_pan1.getList_capitulos();
        pan_text = new PantallaTexto();
        scroll = new JScrollPane();
        btn_anterior = pan_text.getBtn_anterior();
        btn_sigPag = pan_text.getBtn_sigPag();
        btn_cont = pan_text.getBtn_cuestionario();
        js = new JScrollPane();
        pan_Cuest11 = new Cuestionario(43, "c11");
        pan_Cuest12 = new Cuestionario(25, "c12");
        pan_Cuest21 = new Cuestionario(7, "c21");
        pan_Cuest22 = new Cuestionario(62, "c22");
        pan_Cuest31 = new Cuestionario(5, "c31");
        pan_Cuest41 = new Cuestionario(5, "c41");
        pan_Cuest42 = new Cuestionario(6, "c42");
        pan_Cuest43 = new Cuestionario(14, "c43");
        //agregar preguntas y encabezados a los cuestionarios S:
        //cuestionario 1.1
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                pan_Cuest11.agregarEncabezado("Exploracion: agudeza visual", Cuestionario.ENCABEZADO_H1);
                pan_Cuest11.agregarEncabezado("Técnica: visión lejana (seis metros)", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Coloca a tu paciente a 6 metros frente a la carta de Snellen", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Indiquele como debe aplicarse el oclusor, primero en un ojo y posteriormente en el otro", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Examina primero un ojo (preferentemente el derecho) y después el otro", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Indica al paciente que identifique la letra o la figura a la cual se señala", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Debes indicar de arriba hacia abajo, teniendo cuidado de no tapar la letra o figura con el indicador", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Anota la linea que puede leer el paciente y la distania a la que puede leerla", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarEncabezado("Anormalidades de la visión lejana (seis metros)", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Ametropías:", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Miopía", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Astigmatismo", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Hipermetropía", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarEncabezado("Técnica: Visión cercana (35 CMS)", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Con el paciente sentado otórgale una tabla de la Jaeger e índicale que lea el texto impreso", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("La tabla de la Jaeger debes colocarla a una distancia de 35 cm.", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarEncabezado("Anormalidades del examen de la visión cercana a 35 cms", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Presbicia", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarEncabezado("Exploración de campos visuales", Cuestionario.ENCABEZADO_H1);
                pan_Cuest11.agregarEncabezado("Técnica: Campimetría por confrontación", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Explorar por separado cada ojo", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Tú y el paciente deben estar sentados frente a frente", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Tu paciente debe cubrir el ojo derecho y ver con su ojo izquierdo hacia tu ojo derecho", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Debes cubrir tu ojo izaquierdo y ver hacia el ojo derecho de tu paciente", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Mueve el objeto de color o luminoso desde la periferia hacia el centro de cada uno de los cuadrantes de la mirada", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("El paciente deberá informarte tan luego vea el objeto", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_Cuest11.agregarEncabezado("Anormalidades de la exploración por confrontación", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Estocoma", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Hemianopsia", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Cuadrantopsia", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarEncabezado("Exploración: Campos visuales", Cuestionario.ENCABEZADO_H1);
                pan_Cuest11.agregarEncabezado("Técnica: Exploración por perimetría", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Explorar por separado cada ojo", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Tu paciente debe estar frente al pizarrón a una distancia de 10 cm. del mismo", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Para explorar el ojo izquierdo, tu faciente debe cubrir si ojo derecho", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Indícale a tu paciente que vea al punto fijo que le designes a la altura de su mirada en el pizarrón", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Debes ubicarte del mismo lado del ojo a explorar", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Mueve un objeto desde la periferia del pizarrón hacia el centro de cada uno de los cuadrantes de la mirada", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Indica al paciente que te informe tan luego vea el objeto", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("A continuación marca en el pizarrón con una 'X' el sitio indicado por el paciente", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Para explorar el ojo derecho debe cubrir el ojo izquierdo y seguir los pasos anteriores", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_Cuest11.agregarEncabezado("Anormalidades de la exploración por perimetría", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Estocoma", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Hemianopsia", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarPregunta("Cuadrantopsia", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.agregarEncabezado("Exploración: visión crómatica", Cuestionario.ENCABEZADO_H1);
                pan_Cuest11.agregarEncabezado("Técnica: visión cromática", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Con el paciente sentado, explora ambos ojos a la vez, y presentale la primera lámina de Stilling e Ishihara (la designada para detectar seguera de colores)", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Pídele al paciente que indique lo que observa en la lámina", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_Cuest11.agregarPregunta("Luego, presenta al paciente la segunda lámina de Stilling e Ishihara (la designada para detectar la seguera para el rojo y el verde)", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Pídele al paciente que indique lo que observa en la lámina", Pregunta.TIPO_SI_NO, true);
                pan_Cuest11.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_Cuest11.agregarEncabezado("Anormalidades de la visión cromática", Cuestionario.ENCABEZADO_H2);
                pan_Cuest11.agregarPregunta("Discromatopsia (Daltonismo)", Pregunta.TIPO_SI_NO, false);
                pan_Cuest11.finalizar();
            }
        }, "cuest 11").start();
        //cuestionario 1.2
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest12.agregarEncabezado("Exploración: fondo de ojo",Cuestionario.ENCABEZADO_H1);
                pan_Cuest12.agregarEncabezado("Técnica: oftalmoscopía",Cuestionario.ENCABEZADO_H2);
																	
                pan_Cuest12.agregarPregunta("Examina el ojo derecho del paciente con tu ojo derecho; y su ojo izquierdo  con tu ojo izquierdo.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("Coloca el disco de lentes del oftalmoscopio en cero.",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest12.agregarPregunta("De primera instancia utiliza la luz blanca y circular.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest12.agregarPregunta("Toma el oftalmoscopio con la mano derecha en posición vertical frente a tu ojo derecho y con el rayo luminoso dirigido al paciente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest12.agregarPregunta("Coloca el dedo índice en el disco de selección de lentes, para cambiar lentes en caso necesario.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest12.agregarPregunta("Indica a tu paciente que vea directamente hacia adelante hacia un punto fijo designado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest12.agregarPregunta("Frente al paciente, acércate a 15 cm. aproximadamente y a 25º a la derecha de él. Es importante cuidar que no obstruyas el campo visual del paciente para evitar movimientos oculares que dificulten la exploración.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("Dirige el haz luminoso a la pupila y observa un “reflejo rojo” al ver a través de la abertura del oftalmoscopio.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("En el caso que no puedas observar a causa del reflejo de la luz, utiliza el haz luminoso más pequeño del oftalmoscopio o bien dirige la fuente luminosa por la orilla de la pupila disminuyendo el ángulo de entrada.",Pregunta.TIPO_SI_NO,true);		

                pan_Cuest12.agregarEncabezado("Técnica: Oftalmoscopía",Cuestionario.ENCABEZADO_H2); 														
                pan_Cuest12.agregarPregunta("Manteniendo la vista en el “reflejo rojo” acércate a una distancia de 3 a5 cm. del ojo derecho de tu paciente.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("Si no observas con claridad el fondo de ojo, rota el disco de lentes hasta enfocarlo. Localiza los vasos sanguíneos e identifícalos como arteria" +
                        "o vena y estudia las características de cada uno de ellos siguiendo su trayecto:\n" +
                        "Recuerda que, la arteria es rectilínea, con coloración rojo brillante  y con mayor luminosidad en su  trayecto y que las venas"+
                        " son más gruesas, con coloración rojo oscuro, con trayecto sinuoso y son menos luminosas."+
                        "Relación arteria/ vena de 3:5 o de 1:3",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("Siguiendo un vaso dirígete en sentido contrario a las ramificaciones o hacia donde se engruesa; esto te llevará la mirada hacia la papila óptica o disco óptico, que se encuentra hacia el borde nasal",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("Para el examen de la macula dirige la luz y la mirada hacia el lado temporal:",Pregunta.TIPO_SI_NO,true); 			
                pan_Cuest12.agregarPregunta("Para el examen de la retina paracentral, debes dirigir el haz luminoso del oftalmoscopio hacia: ",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("arriba para la retina superior",Pregunta.TIPO_SI_NO,true);				
                pan_Cuest12.agregarPregunta("abajo para la retina inferior",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest12.agregarPregunta("al lado temporal para la retina temporal",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest12.agregarPregunta("al lado nasal para la retina nasal",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest12.agregarPregunta("Para examinar el ojo izquierdo el oftalmoscopio se mantiene con la mano izquierda ante el ojo izquierdo del explorador y a la izquierda del paciente",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest12.agregarPregunta("Repite todos los pasos anteriores",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest12.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest12.agregarEncabezado("Anormalidades: fondo de ojo",Cuestionario.ENCABEZADO_H2);																		
                pan_Cuest12.agregarPregunta("Papila: bordes no definidos; alteración en su coloración y excavación.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest12.agregarPregunta("Alteraciones de la relación arteria/vena",Pregunta.TIPO_SI_NO,false); 		
                pan_Cuest12.agregarPregunta("Hemorragias",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest12.agregarPregunta("Exudados",Pregunta.TIPO_SI_NO,false);
                pan_Cuest12.finalizar();

            }
        }, "cuest 12").start();
        //cuestionario 2.1
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest21.agregarEncabezado("Exploración de la rama superior",Cuestionario.ENCABEZADO_H1);
                pan_Cuest21.agregarEncabezado("Músculos: recto superior y elevador del  párpado superior.",Cuestionario.ENCABEZADO_H1);
                pan_Cuest21.agregarEncabezado("Técnica: exploración del elevador del párpado superior",Cuestionario.ENCABEZADO_H2);														
                pan_Cuest21.agregarPregunta("El explorado mantiene su mirada hacia delante",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest21.agregarPregunta("Tú observa la simetría de las aberturas palpebrales",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest21.agregarPregunta("Observa la distancia entre los bordes libres de los parpados.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest21.agregarPregunta("Observa la relación de los parpados con el iris (la capa que lo cubre se denomina cornea) y la pupila, recuerda que el borde del parpado superior cubre el polo superior del iris. No se debe exponer ninguna porción de la esclerótica entre los bordes de los parpados y los polos superior e inferior del iris.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest21.agregarPregunta("Solicita al explorado que cierre los ojos, enseguida le pides que los abra y tú  intentas mantener cerrados ambos parpados lo cual permite valorar la función y fuerza del músculo elevador del párpado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest21.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest21.agregarEncabezado("Anormalidades: rama superior",Cuestionario.ENCABEZADO_H2);																		
                pan_Cuest21.agregarPregunta("Ptosis palpebral.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest21.finalizar();
            }
        }, "cuest 21").start();
        //cuestionario 2.2
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest22.agregarEncabezado("Exploración de la rama inferiror",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarPregunta("El paciente y tú se mantienen frente a frente a la misma altura de las pupilas.",Pregunta.TIPO_SI_NO,true); 		
                pan_Cuest22.agregarPregunta("Explora cada ojo independientemente, solicita que se ocluya un de sus ojos, para explorar al otro.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Indica al paciente que, sin mover la cabeza, siga con su ojo  el objeto que le presentas.",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest22.agregarPregunta("Moviliza el objeto hacia dentro, arriba y abajo; hacia fuera y arriba y fuera y abajo",Pregunta.TIPO_SI_NO,true); 		
                pan_Cuest22.agregarPregunta("Registra lo observado.", Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest22.agregarEncabezado("Anormalidades:  rama inferior",Cuestionario.ENCABEZADO_H2);																		
                pan_Cuest22.agregarPregunta("Desviación del globo ocular hacia abajo y afuera.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarEncabezado("Exploración de los reflejos exógenos:  músculos, constrictor de la pupila y ciliar",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica: exploración del reflejo fotomotor",Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Explora cada ojo por separado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("El paciente y tú se mantienen frente a frente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Observa el tamaño de la pupila.",Pregunta.TIPO_SI_NO,true); 		
                pan_Cuest22.agregarPregunta("Indica al paciente que fije su mirada al frente en un punto designado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Evita colocar el haz luminoso de frente al paciente en un primer momento; aproxima el haz luminoso de afuera hacia la línea media del ojo a explorar.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("La aplicación del estímulo provoca contracción pupilar: “miosis”",Pregunta.TIPO_SI_NO,true);
                pan_Cuest22.agregarPregunta("Procede a explorar el otro ojo de la misma manera.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest22.agregarEncabezado("Anormalidades: exploración del reflejos fotomotor",Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Parálisis pupilar.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarPregunta("Anisocoria.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarEncabezado("Exploración de reflejos exógenos",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Músculos, constrictor de la pupila y ciliar.",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica: exploaración de los reflejos de acomodación",Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Explora cada ojo por separado.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest22.agregarPregunta("Observa el tamaño de la pupila.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Solicita a tu paciente que fije la mirada al frente en un punto designado, a 10 cm de la pupila a explorar.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Le indicas que en esa misma línea de mirada, vea un punto lejano designado a 3 metros de distancia de la pupila a explorar.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Solicita a tu paciente que repita los dos pasos previos, con lo que observas la contracción pupilar con la visión cercana y la dilatación pupilar con la visión lejana.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Enseguida procede a explorar el otro ojo de la misma manera.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest22.agregarEncabezado("Anormalidades: exploarción del reflejo de acomodación", Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Parálisis.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarPregunta("Anisocoria.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarEncabezado("Exploración de reflejos exógenos",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Músculo constrictor de la pupila y ciliar.",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica:exploración del refejo consensusal",Cuestionario.ENCABEZADO_H2);																
                pan_Cuest22.agregarPregunta("Explora cada ojo por separado",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Observa el tamaño de la pupila del ojo derecho",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Indica al paciente que lleve su mirada al frente en un punto fijo designado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Coloca el canto de tu mano libre sobre el puente de la nariz de tu paciente, para obstruir el paso del estímulo luminoso al lado contrario.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Recuerda que debes evitar, de primera instancia, colocar el haz luminoso de frente al paciente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Aproxima el haz luminoso de afuera hacia la línea media del ojo que estas  explorando.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("El estímulo provoca contracción pupilar en el ojo derecho y también del ojo contralateral.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Procede a explorar del mismo modo el ojo izquierdo.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest22.agregarEncabezado("Anormaliadades: exploración del reflejo consensual",Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Parálisis.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarPregunta("Anisocoria.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest22.agregarEncabezado("Exploración de reflejos endógenos",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica: exploración del reflejo álgico",Cuestionario.ENCABEZADO_H2);																
                pan_Cuest22.agregarPregunta("Se exploran ambos ojos simultáneamente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Observa el tamaño de cada pupila.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Le pides al paciente que lleve su mirada al frente a un punto fijo designado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Provocas dolor con un pellizco en el cuello o en el músculo trapecio.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Procede a observar ambos ojos en búsqueda de la contracción pupilar.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);		
                pan_Cuest22.agregarEncabezado("Anormalidades: exploración del reflejo álgido",Cuestionario.ENCABEZADO_H2);																
                pan_Cuest22.agregarPregunta("Arreflexia",Pregunta.TIPO_SI_NO,false);				
                pan_Cuest22.agregarEncabezado("Exploración de reflejos endógenos",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica:exploración del reflejo psíquico",Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Se exploran ambos ojos simultáneamente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Observa el tamaño de cada pupila.",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest22.agregarPregunta("Le pides al paciente que lleve su mirada al frente a un punto fijo designado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Solicita a tu paciente que se imagine una fuente luminosa extrema que se intensifica frente a él.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Procede a observar ambos ojos en búsqueda de la contracción pupilar.",Pregunta.TIPO_SI_NO,true); 		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);	
                pan_Cuest22.agregarEncabezado("Anormalidades: exploración del reflejo psíquico",Cuestionario.ENCABEZADO_H2);														
                pan_Cuest22.agregarPregunta("Arreflexia",Pregunta.TIPO_SI_NO,false);				
                pan_Cuest22.agregarEncabezado("Exploración de reflejos con movimientos asociados",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica: exploración del fenómeno del orbicular o Piltz-Westphal",Cuestionario.ENCABEZADO_H2);												
                pan_Cuest22.agregarPregunta("Explora cada ojo por separado.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest22.agregarPregunta("Solicita al paciente que cierre el ojo a explorar y enseguida intenta separar los parpados con dos de tus dedos.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Lo que se busca es una contracción pupilar.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Procede a explorar del mismo modo el otro ojo.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);		
                pan_Cuest22.agregarEncabezado("Anormaliades: exploración del fenómeno del orbicular o de Piltz-Westpahl",Cuestionario.ENCABEZADO_H2);
                pan_Cuest22.agregarPregunta("Arreflexia",Pregunta.TIPO_SI_NO, false);		
                pan_Cuest22.agregarEncabezado("Exploración de rflejos con mi¿vimetos asociados",Cuestionario.ENCABEZADO_H1);
                pan_Cuest22.agregarEncabezado("Técnica: exploración del reflejos de Tournay",Cuestionario.ENCABEZADO_H2);															
                pan_Cuest22.agregarPregunta("Explora cada ojo por separado.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Indica al paciente que lleve su mirada hacia fuera (en abducción).",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Esto provoca midriasis en el ojo en abducción.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Procede a explorar el otro ojo de la misma manera.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest22.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);		
                pan_Cuest22.agregarEncabezado("Anormalidades: exploarción del reflejo de Tournay",Cuestionario.ENCABEZADO_H2);												
                pan_Cuest22.agregarPregunta("Arreflexia",Pregunta.TIPO_SI_NO,false);
                pan_Cuest22.finalizar();
            }
        }, "cuest 22").start();
        //cuestionario 3.1        //cuestionario 4.3

        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest31.agregarEncabezado("Exploración del IV nervio Craneal",Cuestionario.ENCABEZADO_H1);
                pan_Cuest31.agregarEncabezado("Técnica: exploración del músculo oblicuo mayor",Cuestionario.ENCABEZADO_H2);	
                pan_Cuest31.agregarPregunta("Pide al paciente que siga el objeto que le presentas a nivel del ojo que se explora.",Pregunta.TIPO_SI_NO,true);			
                pan_Cuest31.agregarPregunta("Moviliza el objeto de color o luminoso hacia abajo y adentro.",Pregunta.TIPO_SI_NO,true);	
                pan_Cuest31.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);		
                pan_Cuest31.agregarEncabezado("Anormalidades: exploarción del músculo oblicuo mayor",Cuestionario.ENCABEZADO_H2);	
                pan_Cuest31.agregarPregunta("Globo ocular ligeramente elevado.",Pregunta.TIPO_SI_NO,false); 		
                pan_Cuest31.agregarPregunta("En aducción manifiesta limitación para llevarlo hacia abajo.",Pregunta.TIPO_SI_NO,false);
                pan_Cuest31.finalizar();
            }
        }, "cuest 31").start();
        //cuestionario 4.1
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest41.agregarEncabezado("Exploración del VI nervio Craneal",Cuestionario.ENCABEZADO_H1);
                pan_Cuest41.agregarEncabezado("Técnica: exploarción del músculo recto externo",Cuestionario.ENCABEZADO_H2);
                pan_Cuest41.agregarPregunta("Le pides al paciente que siga el objeto que le presentas a nivel del ojo que se explora.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest41.agregarPregunta("Moviliza el objeto de color o luminoso hacia afuera (abducción).",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest41.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);		
                pan_Cuest41.agregarEncabezado("Anormalidades: exploración del músculo recto externo",Cuestionario.ENCABEZADO_H2);
                pan_Cuest41.agregarPregunta("El ojo afectado se encuentra en aducción.",Pregunta.TIPO_SI_NO,false);		
                pan_Cuest41.agregarPregunta("Si los dos ojos están afectados, se aprecian con sus ejes cruzados",Pregunta.TIPO_SI_NO,false);
                pan_Cuest41.finalizar();
            }
        }, "cuest 41").start();
        //cuestionario 4.2
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest42.agregarEncabezado("Exploración binocular:",Cuestionario.ENCABEZADO_H1);
                pan_Cuest42.agregarEncabezado("Técnica: movimientos oculares en cojunto",Cuestionario.ENCABEZADO_H2);	
                pan_Cuest42.agregarPregunta("El paciente se coloca frente a ti con el campo visual de preferencia al mismo nivel.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest42.agregarPregunta("Luego, indica al paciente que siga con la mirada (sin mover la cabeza) el objeto de color o luminoso que le presentas frente a él.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest42.agregarPregunta("Moviliza el objeto. hacia adentro (aducción) y afuera (abducción).",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest42.agregarPregunta("Con la mirada hacia dentro el objeto se moviliza hacia arriba y abajo.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest42.agregarPregunta("Con la mirada hacia fuera se moviliza el objeto hacia arriba y abajo",Pregunta.TIPO_SI_NO,true); 		
                pan_Cuest42.agregarPregunta("Registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest42.finalizar();
            }
        }, "cuest 42").start();
        //cuestionario 4.3
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_Cuest43.agregarEncabezado("Exploración de nistagmo optocinético",Cuestionario.ENCABEZADO_H1);
                pan_Cuest43.agregarEncabezado("Técnica: exploración de nistagmo optocinético",Cuestionario.ENCABEZADO_H2);
                pan_Cuest43.agregarPregunta("Se observa la función de los ojos.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se le informa al paciente que se hará girar la silla sobre la cual se encuentra sentado y que se detendrá repentinamente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se le indica al paciente que mantenga los ojos abiertos.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se hace girar la silla.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se detiene repentinamente la silla.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("El explorador observa los ojos del paciente en busca de nistagmo horizontal.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se registra lo observado.",Pregunta.TIPO_REGISTRAR,false);		
                pan_Cuest43.agregarEncabezado("Técnica: exploraciíon de nistagmo optocinético",Cuestionario.ENCABEZADO_H2);
                pan_Cuest43.agregarPregunta("Se observa la función de los ojos.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se le informa al paciente que se hará girar el tambor y que se detendrá repentinamente.",Pregunta.TIPO_SI_NO,true); 		
                pan_Cuest43.agregarPregunta("Se le indica al paciente que mantenga los ojos abiertos y mirando las líneas del tambor",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se hace girar el tambor",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se detiene repentinamente.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("El explorador observa los ojos del paciente en busca de nistagmo horizontal.",Pregunta.TIPO_SI_NO,true);		
                pan_Cuest43.agregarPregunta("Se registra lo observado.",Pregunta.TIPO_REGISTRAR,false);
                pan_Cuest43.finalizar();
            }
        }, "cuest 43").start();
        //propiedades de la ventana
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        
        list_capitulos.addListSelectionListener(this);
        
        //agregar elementos a la ventana
        this.add(pan_pan1);
        stackPantallas.push(pan_pan1);
        
        btn_anterior.addActionListener(this);
        btn_sigPag.addActionListener(this);
        btn_cont.addActionListener(this);
        pan_Cuest11.getBtn_continuar().addActionListener(this);
        pan_Cuest11.getBtn_anterior().addActionListener(this);
        pan_Cuest11.getBtn_inicio().addActionListener(this);
        pan_Cuest21.getBtn_continuar().addActionListener(this);
        pan_Cuest21.getBtn_anterior().addActionListener(this);
        pan_Cuest21.getBtn_inicio().addActionListener(this);
        pan_Cuest12.getBtn_continuar().addActionListener(this);
        pan_Cuest12.getBtn_anterior().addActionListener(this);
        pan_Cuest12.getBtn_inicio().addActionListener(this);
        pan_Cuest22.getBtn_continuar().addActionListener(this);
        pan_Cuest22.getBtn_anterior().addActionListener(this);
        pan_Cuest22.getBtn_inicio().addActionListener(this);
        pan_Cuest31.getBtn_continuar().addActionListener(this);
        pan_Cuest31.getBtn_anterior().addActionListener(this);
        pan_Cuest31.getBtn_inicio().addActionListener(this);
        pan_Cuest41.getBtn_continuar().addActionListener(this);
        pan_Cuest41.getBtn_anterior().addActionListener(this);
        pan_Cuest41.getBtn_inicio().addActionListener(this);
        pan_Cuest42.getBtn_continuar().addActionListener(this);
        pan_Cuest42.getBtn_anterior().addActionListener(this);
        pan_Cuest42.getBtn_inicio().addActionListener(this);
        pan_Cuest43.getBtn_continuar().addActionListener(this);
        pan_Cuest43.getBtn_anterior().addActionListener(this);
        pan_Cuest43.getBtn_inicio().addActionListener(this);
        
        //iniciar desde el avance
        capActual = regGen.getCapitulo();
        parteActual = regGen.getParte();
        if (regGen.getCapitulo()!= 1 || regGen.getParte() != 1) {
            String[] opciones = {"Sí, continuar", "No, reiniciar", "Ir a inicio"};
            switch(JOptionPane.showOptionDialog(null, "¿Continuar desde donde lo dejaste?\nCapítulo: " + regGen.getCapitulo() + "\nParte: " +
                    regGen.getParte(), "Mensaje", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null)){
                case JOptionPane.YES_OPTION:
                    switch(regGen.getCapitulo()){
                        case 1:
                            switch(regGen.getParte()){
                                case 1:
                                    break;
                                case 2:
                                    lector = new LectorPDF("doc/docs/i17.pdf");
                                    capActual = 1;
                                    parteActual = 2;//?
                                    scroll.setViewportView(lector);
                                    repaint();
                                    repaint();
                                    scroll = new JScrollPane(lector);
                                    pan_text.add(scroll, BorderLayout.CENTER);
                                    setActual(pan_text);
                                    break;
                            }
                            break;
                        case 2:
                            switch(regGen.getParte()){
                                case 1:
                                    lector = new LectorPDF("doc/docs/i30 - 34.pdf");
                                    capActual = 2;
                                    parteActual = 1;//?
                                    scroll.setViewportView(lector);
                                    repaint();
                                    repaint();
                                    scroll = new JScrollPane(lector);
                                    pan_text.add(scroll, BorderLayout.CENTER);
                                    setActual(pan_text);
                                    break;
                                case 2:
                                    capActual = 2;
                                    parteActual = 2;//?
                                    setActual(pan_Cuest22);
                                    break;
                            }
                            break;
                        case 3:
                            //este solo tiene una parte
                            capActual = 3;
                            parteActual = 1;
                            setActual(pan_Cuest31);
                            break;
                        case 4:
                            switch(regGen.getParte()){
                                case 1:
                                    capActual = 4;
                                    parteActual = 1;
                                    setActual(pan_Cuest41);
                                    break;
                                case 2:
                                    capActual = 4;
                                    parteActual = 2;
                                    setActual(pan_Cuest42);
                                    break;
                                case 3:
                                    capActual = 4;
                                    parteActual = 3;
                                    setActual(pan_Cuest43);
                                    break;
                            }
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    resetALL();
                    break;
                case JOptionPane.CANCEL_OPTION:
            }
        }
    }

    public void setActual(Container siguiente){
        siguiente.setVisible(true);
        this.getContentPane().removeAll();
        this.getContentPane().add(siguiente);
        this.getContentPane().paintAll(this.getContentPane().getGraphics());
        stackPantallas.push(siguiente);
    }
    
    private void resetALL(){
        switch(JOptionPane.showConfirmDialog(null, "Se reiniciará su avance en el manual", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)){
            case JOptionPane.OK_OPTION:
                //toar cada registro de avance, reestablecerlo y guardarlo
                parteActual = 1;
                capActual = 1;
                regGen.reset();
                Libro.controlAdv.guardarAvance(regGen, ID);
                pan_Cuest11.reset();
                pan_Cuest12.reset();
                pan_Cuest21.reset();
                pan_Cuest22.reset();
                pan_Cuest31.reset();
                pan_Cuest41.reset();
                pan_Cuest42.reset();
                pan_Cuest43.reset();
                File carpeta = new File("registros");
                File[] archivos = carpeta.listFiles();
                for (File archivo : archivos) {
                    archivo.deleteOnExit();
                }
                JOptionPane.showMessageDialog(null, "Se ha reestablecido el avance\nPor favor reinicia la aplicación");
                System.exit(0);
                break;
            case JOptionPane.CANCEL_OPTION:
                setActual(list_capitulos);
                break;
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            capituloSeleccionado = list_capitulos.getSelectedIndex();
            
            switch(capituloSeleccionado){
                //capitulo 1
                case 0:
                    lector = new LectorPDF("doc/docs/i1 - 9.pdf");
                    capActual = 1;
                    parteActual = 1;//?
                    scroll.setViewportView(lector);
                    repaint();
                    repaint();
                    scroll = new JScrollPane(lector);
                    pan_text.add(scroll, BorderLayout.CENTER);
                    setActual(pan_text);
                    break;
                //capitulo 2
                case 1:
                    if(regGen.getCapitulo() >= 2){
                        setActual(pan_Cuest21);
                    }
                    repaint();
                    break;
                case 2:
                    if (regGen.getCapitulo() >= 3) {
                        setActual(pan_Cuest31);
                    }
                    break;
                case 3:
                    if (regGen.getCapitulo() == 4) {
                        setActual(pan_Cuest41);
                        parteActual = 1;
                    }
                    break;
                case 4:
                    //Aqui va la pantalla de creditos
                    AcercaDe();
                    break;
            }
            pan_pan1.list_capitulos.clearSelection();
            
        }
    }

    public void AcercaDe(){
        pantallaCreditos = new JDialog(this);
        JButton aceptar = new JButton("Aceptar");
        JLabel imagenFondo = new JLabel(new ImageIcon("res/creditos"));//inicializar
        JPanel boton = new JPanel(new FlowLayout());
        
        boton.add(aceptar);
        pantallaCreditos.setUndecorated(true);
        pantallaCreditos.setSize(this.getWidth(), this.getHeight());
        pantallaCreditos.setLocationRelativeTo(this);
        pantallaCreditos.add(imagenFondo, BorderLayout.CENTER);
        pantallaCreditos.add(boton, BorderLayout.SOUTH);
        pantallaCreditos.setVisible(true);
        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallaCreditos.setVisible(false);
                pantallaCreditos = null;
            }
        });
    }
    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //guardar avance de la parte actual
        try{
            pan_Cuest11.getReg().setEvaluadas(pan_Cuest11.getEvaluacion());
            pan_Cuest11.getReg().setRespuestas(pan_Cuest11.getRespuestas());
            pan_Cuest12.getReg().setRespuestas(pan_Cuest12.getRespuestas());
            pan_Cuest12.getReg().setEvaluadas(pan_Cuest12.getEvaluacion());
            pan_Cuest21.getReg().setRespuestas(pan_Cuest21.getRespuestas());
            pan_Cuest21.getReg().setEvaluadas(pan_Cuest21.getEvaluacion());
            pan_Cuest22.getReg().setRespuestas(pan_Cuest22.getRespuestas());
            pan_Cuest22.getReg().setEvaluadas(pan_Cuest22.getEvaluacion());
            pan_Cuest31.getReg().setRespuestas(pan_Cuest31.getRespuestas());
            pan_Cuest31.getReg().setEvaluadas(pan_Cuest31.getEvaluacion());
            pan_Cuest41.getReg().setRespuestas(pan_Cuest41.getRespuestas());
            pan_Cuest41.getReg().setEvaluadas(pan_Cuest41.getEvaluacion());
            pan_Cuest42.getReg().setEvaluadas(pan_Cuest42.getEvaluacion());
            pan_Cuest42.getReg().setRespuestas(pan_Cuest42.getRespuestas());
            pan_Cuest43.getReg().setEvaluadas(pan_Cuest43.getEvaluacion());
            pan_Cuest43.getReg().setRespuestas(pan_Cuest43.getRespuestas());
            Libro.controlAdv.guardarAvance(regGen, ID);
            Libro.controlAdv.guardarAvance(pan_Cuest11.getReg(), pan_Cuest11.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest12.getReg(), pan_Cuest12.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest21.getReg(), pan_Cuest21.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest22.getReg(), pan_Cuest22.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest31.getReg(), pan_Cuest31.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest41.getReg(), pan_Cuest41.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest42.getReg(), pan_Cuest42.getID());
            Libro.controlAdv.guardarAvance(pan_Cuest43.getReg(), pan_Cuest43.getID());
        } catch(NullPointerException ne){}
        finally{
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        this.paintAll(this.getGraphics());
    }
}
