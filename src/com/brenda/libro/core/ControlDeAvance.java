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
    private final String CARPETA  ="doc/docs/";
    private final String PREFIJO ="adv";
    private final String EXT =".dat";

    public ControlDeAvance() {
    }
    
    public synchronized RegistroDeAvance cargarRegistro(String id){
        arcAvance = new File(CARPETA + PREFIJO + id + EXT);
        try{
            ois = new ObjectInputStream(new FileInputStream(arcAvance));
            reg = (RegistroDeAvance) ois.readObject();
            ois.close();
        } 
        catch(IOException | ClassNotFoundException ioe){
            System.out.println("carga: Ocurrio un error\n" + ioe.getLocalizedMessage());
            reg = null;
        }
        return reg;
    }
    
    public synchronized void guardarAvance(RegistroDeAvance reg, String id){
        arcAvance = new File(CARPETA + PREFIJO + id + EXT);
        if (arcAvance.exists() && reg != null) {
            try{
                System.out.println("guardando: " + reg.toString());
                oos = new ObjectOutputStream(new FileOutputStream(arcAvance));
                oos.writeObject(reg);
                oos.close();
            } catch(IOException ioe){
                System.out.println("Guardar: Ocurrio un error\n" + ioe.getLocalizedMessage());
            }
        } else if(!arcAvance.exists()){
            try{
                arcAvance.createNewFile();
                guardarAvance(reg, id);
            } catch(IOException e){
                System.out.println("no se pudo crear el archivo" + arcAvance.getPath());
            }
        } else if (reg == null){
            throw new NullPointerException("El valor del registro en nulo");
        }
    }
    
    public void ResetAvance(String id){
        File f = new File(CARPETA + PREFIJO + id + EXT);
        f.delete();
    }
}
