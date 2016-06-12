/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brenda.libro.core;

import com.brenda.libro.gui.VentanaPrincipal;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author imarinr
 */
public class Libro {
    public static ControlDeAvance controlAdv = new ControlDeAvance();
    public static void main(String[] args) {
        JFrame splash = new JFrame();
        splash.setUndecorated(true);
        JLabel imagen = new JLabel(new ImageIcon("res/portada"));
        splash.setSize(1080, 720);
        splash.add(imagen);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        try{
            Thread.sleep(2000);
        } catch(InterruptedException e){}
        splash.setVisible(false);
        splash = null;
        VentanaPrincipal ven = new VentanaPrincipal("Manual de exploración clínica de los nervios craneales II, III, IV, V, VI ");
    }
}
