package com.brenda.libro.gui;

import com.brenda.libro.core.ControlDeAvance;
import com.brenda.libro.core.RegistroDeAvance;
import com.brenda.libro.core.Pregunta;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * La ventana principal de la aplicacion
 * @author imarinr
 */
public class VentanaPrincipal extends JFrame implements ActionListener, ListSelectionListener, WindowListener{

    private int capituloSeleccionado = 0;
    private int pagActual = 1;
    private int capActual, parteActual;
    private Pantalla1 pan_pan1;
    private PantallaTexto pan_text;
    private Cuestionario pan_cuest1, pan_cuest12, pan_cuest2;
    private LectorPDF lector;
    private JButton btn_anterior, btn_sigPag, btn_cuest;
    private JScrollPane js, scroll;
    JList<Object> list_capitulos;
    RegistroDeAvance reg;
    ControlDeAvance control;

    public VentanaPrincipal(String title) throws HeadlessException {
        super(title);
        control = new ControlDeAvance();
        reg = control.cargarRgistro();
        System.out.println(reg.toString());
        if (reg == null) {
            reg = new RegistroDeAvance(42);
            reg.setCapitulo(1);
            reg.setParte(1);
        }
        this.inicializarComponentes();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int paginas = 0;
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
        if (e.getSource().equals(btn_cuest)) {
            switch(capActual){
                case 1:
                    switch(parteActual){
                        case 1:
                                js.setViewportView(pan_cuest1);
                                pan_cuest1.setVisible(true);
                                repaint();
                                break;
                        case 2:
                                js.setViewportView(pan_cuest12);
                                pan_cuest12.setVisible(true);
                                repaint();
                                break;
                            }
                    break;
                case 2:
                        js.setViewportView(pan_cuest2);
                        pan_cuest2.setVisible(true);
                        repaint();
                    break;
            }
            setActual(lector, js);
            lector = null;
        }
        if (e.getSource().equals(pan_cuest1.getBtn_continuar())) {
            boolean aprueba = pan_cuest1.getEvaluacion();
            if (aprueba) {
                parteActual = 2;
                reg.setParte(2);
                JOptionPane.showMessageDialog(null, "Puedes continuar");
            } else {
                JOptionPane.showMessageDialog(null, "Regresa al primer capitulo", null, JOptionPane.ERROR_MESSAGE);
            }
            setActual(pan_cuest1, list_capitulos);
        }
    }

    /**
     * <p>Inicializa los componentes de la ventana y todas sus propiedades</p>
     */
    private void inicializarComponentes() {
        //declaraciones
        pan_pan1 = new Pantalla1();
        list_capitulos = pan_pan1.getList_capitulos();
        pan_text = new PantallaTexto();
        btn_anterior = pan_text.getBtn_anterior();
        btn_sigPag = pan_text.getBtn_sigPag();
        btn_cuest = pan_text.getBtn_cuestionario();
        js = new JScrollPane();
        pan_cuest1 = new Cuestionario(43);
        pan_cuest12 = new Cuestionario(24);
        pan_cuest2 = new Cuestionario(10);
        
        //agregar preguntas y encabezados a los cuestionarios S:
        //cuestionario 1.1
        new Thread(new Runnable() {
            @Override
            public void run() {
                pan_cuest1.agregarEncabezado("Exploracion: agudeza visual", Cuestionario.ENCABEZADO_H1);
                pan_cuest1.agregarEncabezado("Técnica: visión lejana (seis metros)", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Coloca a tu paciente a 6 metros frente a la carta de Snellen", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Indiquele como debe aplicarse el oclusor, primero en un ojo y posteriormente en el otro", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Examina primero un ojo (preferentemente el derecho) y después el otro", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Indica al paciente que identifique la letra o la figura a la cual se señala", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Debes indicar de arriba hacia abajo, teniendo cuidado de no tapar la letra o figura con el indicador", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Anota la linea que puede leer el paciente y la distania a la que puede leerla", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarEncabezado("Anormalidades de la visión lejana (seis metros)", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Ametropías:", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Miopía", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Astigmatismo", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Hipermetropía", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarEncabezado("Técnica: Visión cercana (35 CMS)", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Con el paciente sentado otórgale una tabla de la Jaeger e índicale que lea el texto impreso", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("La tabla de la Jaeger debes colocarla a una distancia de 35 cm.", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarEncabezado("Anormalidades del examen de la visión cercana a 35 cms", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Presbicia", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarEncabezado("Exploración de campos visuales", Cuestionario.ENCABEZADO_H1);
                pan_cuest1.agregarEncabezado("Técnica: Campimetría por confrontación", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Explorar por separado cada ojo", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Tú y el paciente deben estar sentados frente a frente", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Tu paciente debe cubrir el ojo derecho y ver con su ojo izquierdo hacia tu ojo derecho", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Debes cubrir tu ojo izaquierdo y ver hacia el ojo derecho de tu paciente", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Mueve el objeto de color o luminoso desde la periferia hacia el centro de cada uno de los cuadrantes de la mirada", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("El paciente deberá informarte tan luego vea el objeto", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_cuest1.agregarEncabezado("Anormalidades de la exploración por confrontación", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Estocoma", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Hemianopsia", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Cuadrantopsia", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarEncabezado("Exploración: Campos visuales", Cuestionario.ENCABEZADO_H1);
                pan_cuest1.agregarEncabezado("Técnica: Exploración por perimetría", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Explorar por separado cada ojo", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Tu paciente debe estar frente al pizarrón a una distancia de 10 cm. del mismo", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Para explorar el ojo izquierdo, tu faciente debe cubrir si ojo derecho", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Indícale a tu paciente que vea al punto fijo que le designes a la altura de su mirada en el pizarrón", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Debes ubicarte del mismo lado del ojo a explorar", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Mueve un objeto desde la periferia del pizarrón hacia el centro de cada uno de los cuadrantes de la mirada", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Indica al paciente que te informe tan luego vea el objeto", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("A continuación marca en el pizarrón con una 'X' el sitio indicado por el paciente", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Para explorar el ojo derecho debe cubrir el ojo izquierdo y seguir los pasos anteriores", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_cuest1.agregarEncabezado("Anormalidades de la exploración por perimetría", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Estocoma", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Hemianopsia", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarPregunta("Cuadrantopsia", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.agregarEncabezado("Exploración: visión crómatica", Cuestionario.ENCABEZADO_H1);
                pan_cuest1.agregarEncabezado("Técnica: visión cromática", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Con el paciente sentado, explora ambos ojos a la vez, y presentale la primera lámina de Stilling e Ishihara (la designada para detectar seguera de colores)", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Pídele al paciente que indique lo que observa en la lámina", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_cuest1.agregarPregunta("Luego, presenta al paciente la segunda lámina de Stilling e Ishihara (la designada para detectar la seguera para el rojo y el verde)", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Pídele al paciente que indique lo que observa en la lámina", Pregunta.TIPO_SI_NO, true);
                pan_cuest1.agregarPregunta("Registra lo observado", Pregunta.TIPO_REGISTRAR, false);
                pan_cuest1.agregarEncabezado("Anormalidades de la visión cromática", Cuestionario.ENCABEZADO_H2);
                pan_cuest1.agregarPregunta("Discromatopsia (Daltonismo)", Pregunta.TIPO_SI_NO, false);
                pan_cuest1.finalizar();
            }
        }, "Cuest1").start();
        
        //propiedades de la ventana
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        list_capitulos.addListSelectionListener(this);
        
        //agregar elementos a la ventana
        this.add(pan_pan1);
        this.setVisible(true);
        
        btn_anterior.addActionListener(this);
        btn_sigPag.addActionListener(this);
        btn_cuest.addActionListener(this);
        pan_cuest1.getBtn_continuar().addActionListener(this);
        pan_cuest2.getBtn_continuar().addActionListener(this);
        pan_cuest12.getBtn_continuar().addActionListener(this);
    }

    public void setActual(Container anterior, Container siguiente){
        anterior.setVisible(false);
        siguiente.setVisible(true);
        this.getContentPane().removeAll();
        this.getContentPane().add(siguiente);
        this.getContentPane().paintAll(this.getContentPane().getGraphics());
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            capituloSeleccionado = list_capitulos.getSelectedIndex();
            scroll = new JScrollPane();
            switch(capituloSeleccionado){
                //capitulo 1
                case 0:
                    lector = new LectorPDF("doc/docs/i1 - 9.pdf");
                    capActual = 1;
                    parteActual = 1;//?
                    scroll.setViewportView(lector);
                    pan_text.add(scroll, BorderLayout.CENTER);
                    repaint();
                    repaint();
                    scroll = new JScrollPane(lector);
                    pan_text.add(scroll, BorderLayout.CENTER);
                    setActual(list_capitulos, pan_text);
                    break;
                //capitulo 2
                case 1:
                    if(reg.getCapitulo() == 2){
                        pan_pan1.setVisible(false);
                        lector = new LectorPDF("doc/docs/i21 - 26.pdf");
                        capActual = 2;
                        parteActual = 2;
                        scroll.setViewportView(lector);
                        pan_text.add(scroll, BorderLayout.CENTER);
                        setActual(list_capitulos, pan_text);
                    }
                    repaint();
                    break;
            }pan_pan1.list_capitulos.clearSelection();
            
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        control.guardarAvance(reg);
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        control.guardarAvance(reg);
        
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
}
