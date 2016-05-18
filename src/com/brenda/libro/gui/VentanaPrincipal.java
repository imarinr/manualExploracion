package com.brenda.libro.gui;

import com.brenda.libro.core.ControlDeAvance;
import com.brenda.libro.core.RegistroDeAvance;
import java.awt.BorderLayout;
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
    private CuestionarioC1 pan_cuest1;
    private CuestionarioC12 pan_cuest12;
    private CuestionarioC2 pan_cuest2;
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
            reg = new RegistroDeAvance(39);
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
                    pan_text.setVisible(false);
                    this.remove(pan_text);
                    repaint();
                    this.add(js);
                    js.setVisible(true);
                    repaint();
                    lector = null;
        }
        if (e.getSource().equals(pan_cuest1.btn_continuar)) {
            System.out.println("continuar1");
            if (pan_cuest1.btn_p1_SI.isSelected()== true && pan_cuest1.btn_p2_SI.isSelected()== true && 
                    pan_cuest1.btn_p3_SI.isSelected() == true && pan_cuest1.btn_p4_SI.isSelected() == true
                    && pan_cuest1.btn_p5_SI.isSelected() == true && pan_cuest1.btn_p6_SI.isSelected() == true
                    && pan_cuest1.btn_p11_SI.isSelected() == true && pan_cuest1.btn_p12_SI.isSelected() == true
                    && pan_cuest1.btn_p14_SI.isSelected() == true
                            && pan_cuest1.btn_p15_SI.isSelected() == true && pan_cuest1.btn_p16_SI.isSelected() == true
                    && pan_cuest1.btn_p17_SI.isSelected() == true && pan_cuest1.btn_p18_SI.isSelected() == true
                    && pan_cuest1.btn_p19_SI.isSelected() == true && pan_cuest1.btn_p23_SI.isSelected() == true
                    && pan_cuest1.btn_p24_SI.isSelected() == true && pan_cuest1.btn_p25_SI.isSelected() == true
                    && pan_cuest1.btn_p26_SI.isSelected() == true && pan_cuest1.btn_p27_SI.isSelected() == true
                    && pan_cuest1.btn_p28_SI.isSelected() == true && pan_cuest1.btn_p29_SI.isSelected() == true
                    && pan_cuest1.btn_p30_SI.isSelected() == true && pan_cuest1.btn_p31_SI.isSelected() == true
                    && pan_cuest1.btn_p39_SI.isSelected() == true) {
                JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
                pan_cuest1.setVisible(false);
                js.setVisible(false);
//                js.removeAll();
                repaint();
//                remove(js);
                parteActual = 2;
                reg.setParte(parteActual);
                control.guardarAvance(reg);
                pan_text.remove(scroll);
                repaint();
                lector = new LectorPDF("doc/docs/i17.pdf");
                scroll = new JScrollPane(lector);
                pan_text.add(scroll);
                add(pan_text);
                pan_text.paintAll(pan_text.getGraphics());
                pan_text.setVisible(true);
                repaint();
                paintAll(getGraphics());
                
            } else {
                JOptionPane.showMessageDialog(null, "Regresa al primer capitulo");
                pan_cuest1.setVisible(false);
                js.setVisible(false);
//                js.removeAll();
                repaint();
                remove(js);
                add(pan_pan1);
                pan_pan1.repaint();
                pan_pan1.setVisible(true);
                repaint();
                paintAll(getGraphics());
            }
        }
        if (e.getSource().equals(pan_cuest12.btn_continuar)) {
            System.out.println("continuar12");
            if(pan_cuest12.btn_p1_SI.isSelected() && pan_cuest12.btn_p2_SI.isSelected()
                        && pan_cuest12.btn_p3_SI.isSelected() && pan_cuest12.btn_p4_SI.isSelected()
                    && pan_cuest12.btn_p5_SI.isSelected() && pan_cuest12.btn_p6_SI.isSelected()
                    && pan_cuest12.btn_p7_SI.isSelected() && pan_cuest12.btn_p8_SI.isSelected()
                    && pan_cuest12.btn_p9_SI.isSelected() && pan_cuest12.btn_p10_SI.isSelected()
                    && pan_cuest12.btn_p11_SI.isSelected() && pan_cuest12.btn_p12_SI.isSelected()
                    && pan_cuest12.btn_p13_SI.isSelected() && pan_cuest12.btn_p14_SI.isSelected()
                    && pan_cuest12.btn_p15_SI.isSelected() && pan_cuest12.btn_p16_SI.isSelected()
                    && pan_cuest12.btn_p17_SI.isSelected() && pan_cuest12.btn_p18_SI.isSelected()
                    && pan_cuest12.btn_p19_SI.isSelected() && pan_cuest12.btn_p20_SI.isSelected()
                    && pan_cuest12.btn_p21_SI.isSelected() && pan_cuest12.btn_p22_SI.isSelected()
                    && pan_cuest12.btn_p23_SI.isSelected() && pan_cuest12.btn_p24_SI.isSelected()
                    ){
                JOptionPane.showMessageDialog(null, "Puedes contnuar alcapitulo 2");
                pan_cuest12.setVisible(false);
                js.setVisible(false);
//                js.removeAll();
                repaint();
//                remove(js);
                add(pan_pan1);
                pan_pan1.repaint();
                pan_pan1.setVisible(true);
                repaint();
                capActual = 2;
                parteActual = 1;
                reg.setCapitulo(capActual);
                reg.setParte(parteActual);
                control.guardarAvance(reg);
                
            } else {
                pan_cuest12.setVisible(false);
                js.setVisible(false);
//                js.removeAll();
                repaint();
//                remove(js);
                JOptionPane.showMessageDialog(null, "Regresa al primer capitulo");
                add(pan_pan1);
                pan_pan1.repaint();
                pan_pan1.setVisible(true);
                repaint();
                paintAll(getGraphics());
                capActual = 1;
                parteActual = 2;
                reg.setCapitulo(capActual);
                reg.setParte(parteActual);
                control.guardarAvance(reg);
                
            }
            
        }
        if (e.getSource().equals(pan_cuest2.btn_continuar2)) {
            System.out.println("continuar2");
            if (pan_cuest2.btn_p1_SI.isSelected() == true && pan_cuest2.btn_p2_SI.isSelected()== true
                    && pan_cuest2.btn_p3_SI.isSelected()== true && pan_cuest2.btn_p4_SI.isSelected()== true
                    && pan_cuest2.btn_p5_SI.isSelected()== true && pan_cuest2.btn_p7_SI.isSelected()== true
                    && pan_cuest2.btn_p8_SI.isSelected()== true && pan_cuest2.btn_p9_SI.isSelected()== true
                    && pan_cuest2.btn_p10_SI.isSelected()== true) {
                JOptionPane.showMessageDialog(null, "Puedes continuar a la siguiente parte");
                pan_cuest2.setVisible(false);
                js.setVisible(false);
//                js.removeAll();
                repaint();
//                remove(js);
                parteActual = 2;
                reg.setParte(parteActual);
                control.guardarAvance(reg);
                pan_text.remove(scroll);
                repaint();
                lector = new LectorPDF("doc/docs/i30 - 34.pdf");
                scroll = new JScrollPane(lector);
                pan_text.add(scroll);
                add(pan_text);
                pan_text.setVisible(true);
                repaint();
                paintAll(getGraphics());
                
            } else {
                pan_cuest12.setVisible(false);
                js.setVisible(false);
//                js.removeAll();
                repaint();
//                remove(js);
                JOptionPane.showMessageDialog(null, "Regresa al capitulo 2");
                add(pan_pan1);
                pan_pan1.repaint();
                pan_pan1.setVisible(true);
                repaint();
                paintAll(getGraphics());
                capActual = 2;
                parteActual = 1;
                reg.setCapitulo(capActual);
                reg.setParte(parteActual);
                control.guardarAvance(reg);
            }
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
        pan_cuest1 = new CuestionarioC1();
        pan_cuest12 = new CuestionarioC12();
        pan_cuest2 = new CuestionarioC2();
        
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
        pan_cuest1.btn_continuar.addActionListener(this);
        pan_cuest2.btn_continuar2.addActionListener(this);
        pan_cuest12.btn_continuar.addActionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            capituloSeleccionado = list_capitulos.getSelectedIndex();
            switch(capituloSeleccionado){
                //capitulo 1
                case 0:
                    lector = new LectorPDF("doc/docs/i1 - 9.pdf");
                    
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            pan_pan1.setVisible(false);
                            repaint();                    
                            capActual = 1;
                            parteActual = 1;
                            scroll = new JScrollPane(lector);
                            pan_text.add(scroll, BorderLayout.CENTER);
                            remove(pan_pan1);
                            add(pan_text);
                            pan_text.repaint();
                            pan_text.setVisible(true);
                            repaint();
                            pan_pan1.list_capitulos.clearSelection();
                            repaint();
                            scroll = new JScrollPane(lector);
                            pan_text.add(scroll, BorderLayout.CENTER);
                            remove(pan_pan1);
                            add(pan_text);
                            pan_text.repaint();
                            pan_text.setVisible(true);
                            repaint();
                            pan_pan1.list_capitulos.clearSelection();
                            repaint();
                            paintAll(getGraphics());
                        }
                    }, "PDF GUI");
                    t.start();
                    try{
                        t.join();
                    } catch(InterruptedException ine){}
                    break;
                //capitulo 2
                case 1:
                    if(reg.getCapitulo() == 2){
                        pan_pan1.setVisible(false);
                        lector = new LectorPDF("doc/docs/i21 - 26.pdf");
                        capActual = 2;
                        parteActual = 2;
                        
                        scroll = new JScrollPane(lector);
                        pan_text.add(scroll, BorderLayout.CENTER);
                        remove(pan_pan1);
                        add(pan_text);
                        pan_text.repaint();
                        pan_text.setVisible(true);
                        repaint();
                    }
                    pan_pan1.list_capitulos.clearSelection();
                    repaint();
                    break;
            }
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
    
    public void pasarGarbageCollector(){
        Runtime garbage = Runtime.getRuntime();
        garbage.gc();
 
    }
}
