/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brenda.libro.gui;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.SwingConstants;


/**
 *
 * @author thefi
 */
public class Pantalla1 extends javax.swing.JPanel {

    private int opcion = 0;
    /**
     * Creates new form Pantalla1
     */
    public Pantalla1() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        list_capitulos = new javax.swing.JList();

        list_capitulos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        list_capitulos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        list_capitulos.setForeground(new java.awt.Color(0, 0, 128));
        list_capitulos.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "II NC Óptico", "III NC Motor Ocular Común", "IV NC Troclear o Patético", "V NC Motor Ocular Externo" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_capitulos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(list_capitulos);
        DefaultListCellRenderer centrar = (DefaultListCellRenderer)list_capitulos.getCellRenderer();
        centrar.setHorizontalAlignment(SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public JList getList_capitulos() {
        return list_capitulos;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JList list_capitulos;
    // End of variables declaration//GEN-END:variables
}
