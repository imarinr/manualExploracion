package com.brenda.libro.test;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Ivan
 */
public class TextAreaTest extends JFrame implements FocusListener{
    String texto;
    JTextArea areaDeTexto;
    
    public TextAreaTest(){
        cargarTexto("try");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout());
        
        areaDeTexto = new JTextArea();
        areaDeTexto.addFocusListener(this);
        
        add(areaDeTexto);
        setVisible(true);
    }

    public void cargarTexto(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File f = new File("registros/" + id + ".txt");
                try{
                    Scanner sc = new Scanner(f);
                    StringBuilder sb = new StringBuilder();
                    
                    while(sc.hasNextLine()){
                        sb.append(sc.nextLine()).append("\n");
                    }
                    texto = sb.toString();
                    System.out.println("texto:" + texto);
                    sc.close();
                } catch(IOException ex){
                    texto ="";
                    System.out.println("ERROR: " + ex.getLocalizedMessage());
                    try{
                        f.createNewFile();
                    } catch (IOException ex2){}
                }
                areaDeTexto.setText(texto);
            }
        }, "leer desde " + id).start();
    }
    
    public void guardar(String text, String id){
        File f = new File("registros/" + id + ".txt");
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(text);
            writer.close();
        } catch(IOException ioe){
            System.out.println(ioe.getLocalizedMessage());
            try{
                f.createNewFile();
                guardar(text, id);
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
        guardar(areaDeTexto.getText(), "try");
    }
    
    public static void main(String[] args) {
        TextAreaTest k = new TextAreaTest();
    }
}
