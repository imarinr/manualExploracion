package com.brenda.libro.gui;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author imarinr
 */
public class LectorPDF extends PagePanel{

    private ByteBuffer buffer;
    private PDFFile archivoPDF;
    private int numPags;
    private RandomAccessFile raf;
    private FileChannel chann;
    
    public LectorPDF(String rutaArchivo) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    File f = new File(rutaArchivo);
                    raf = new RandomAccessFile(f, "r");
                    chann = raf.getChannel();
                    buffer = chann.map(FileChannel.MapMode.READ_ONLY, 0, chann.size());
                    archivoPDF = new PDFFile(buffer);
                    numPags = archivoPDF.getNumPages();
                    if (numPags > 0) {
                        verPagina(1);
                    }
                } catch(IOException ioe){
                    System.out.println(ioe.getMessage());
                }
            }
        }, "Carga PDF");
        t.start();        
    }
    
    public void verPagina(int pag){
        PDFPage pagina = archivoPDF.getPage(pag);
        this.useZoomTool(false);
        this.showPage(pagina);
        this.repaint();
    }

    public int getNumPags() {
        return numPags;
    }
    
}
