package com.brenda.libro.test;

import com.brenda.libro.core.Pregunta;
import com.brenda.libro.gui.Cuestionario;
import javax.swing.JFrame;

/**
 *
 * @author Ivan
 */
public class TestCuestionario extends JFrame{
    public static void main(String[] args) {
        Cuestionario c1 = new Cuestionario(3);
        c1.agregarEncabezado("Encabezado H1: "
                + "este dice el tema que se trata", Cuestionario.ENCABEZADO_H1);
        c1.agregarEncabezado("Encabezado h2: "
                + "este dice algo acerca de la tecnica que se usa", Cuestionario.ENCABEZADO_H2);
        c1.agregarPregunta("pregunta 1", Cuestionario.TIPO_SI_NO, true);
        c1.agregarPregunta("pregunta 2", Cuestionario.TIPO_SI_NO, true);
        c1.agregarPregunta("pregunta 3", Cuestionario.TIPO_SI_NO, true);
        c1.agregarPregunta("pregunta muy pero muy larga de texto extendido y algunos detalles"
                + " extras y cosas por el estilo como detalles de la tecnica o"
                + " del autor", Cuestionario.TIPO_SI_NO, true);
        c1.agregarEncabezado("este es otro encabezado de tipo H1", Cuestionario.ENCABEZADO_H1);
        c1.agregarPregunta("esta es una pregunta de varias lineas para hacer una prueba", Cuestionario.TIPO_SI_NO, true);
        c1.agregarPregunta("esta es otra pregunta de varias lineas para hacer otra prueba", Cuestionario.TIPO_SI_NO, true);
        c1.finalizar();
        TestCuestionario test1 = new TestCuestionario();
        test1.setSize(1280, 720);
        test1.setLocationRelativeTo(null);
        test1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        test1.add(c1);
        test1.setVisible(true);
    }
}
