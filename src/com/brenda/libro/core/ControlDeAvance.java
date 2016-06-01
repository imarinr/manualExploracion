package com.brenda.libro.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Ivan
 */
public class ControlDeAvance {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private RegistroDeAvance reg;
    private File arcAvance;

    public ControlDeAvance() {
        arcAvance = new File("doc/docs/adv.dat");
        try{
            if (arcAvance.exists() == false) {
                arcAvance.createNewFile();
                System.out.println("se creo el archivo " + arcAvance.getName());
            }
        } catch(IOException ioe){
            System.out.println("creacion: Ocurrio un error");
        }
    }
    
    public RegistroDeAvance cargarRgistro(){
        try{
            ois = new ObjectInputStream(new FileInputStream(arcAvance));
            reg = (RegistroDeAvance) ois.readObject();
            System.out.println("se leyo correctamente");
            ois.close();
        } 
        catch(IOException | ClassNotFoundException ioe){
            System.out.println("carga: Ocurrio un error\n" + ioe.getLocalizedMessage());
            reg = new RegistroDeAvance(39);
            reg.setCapitulo(1);
            reg.setParte(1);
        }
        return reg;
    }
    
    public void guardarAvance(RegistroDeAvance reg){
        try{
            oos = new ObjectOutputStream(new FileOutputStream(arcAvance));
            System.out.println("escribir objeto\n" + reg.toString());
            oos.writeObject(reg);
            oos.close();
        } catch(IOException ioe){
            System.out.println("Guardar: Ocurrio un error\n" + ioe.getLocalizedMessage());
        }
    }
}
