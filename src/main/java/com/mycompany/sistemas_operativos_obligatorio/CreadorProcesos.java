/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemas_operativos_obligatorio;

import javax.swing.table.DefaultTableModel;
import Procesos.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matia
 */
public class CreadorProcesos extends javax.swing.JFrame {
    
    DefaultTableModel tablaProcesos;
    VistaPlanificador ventanaPrincipal;
    int cantProcesosAuto = 0;

    /**
     * Creates new form CreadorProcesos
     * @param cantCPUs
     * @param tiempoSegs
     * @param cantCiclosEjecucionCPUs
     */
    public CreadorProcesos(int cantCPUs, float tiempoSegs, int cantCiclosEjecucionCPUs) {
        
        initComponents();
        
        setLocationRelativeTo(null);
        this.
        
        duracionESProceso.setEnabled(false);
        
        botonGenerarProcesos.setEnabled(false);
        botonCargarProceso.setEnabled(false);
        botonInsertarProceso.setEnabled(false);
        botonEliminarProceso.setEnabled(false);
        botonActualizarProceso.setEnabled(false);
        
        this.ventanaPrincipal = new VistaPlanificador(cantCPUs, tiempoSegs, cantCiclosEjecucionCPUs, this);
        
        this.ventanaPrincipal.setVisible(false);
        
        tablaProcesos = new DefaultTableModel();
        
        tablaProcesos.addColumn("Nombre");
        tablaProcesos.addColumn("Tipo");
        tablaProcesos.addColumn("Prioridad");
        tablaProcesos.addColumn("Duracion");
        tablaProcesos.addColumn("Ciclos para E/S");
        tablaProcesos.addColumn("Duracion E/S");
        
        tablaProcesosIncial.setModel(tablaProcesos);
    }              
    
    private void limpiarTabla(){
        int filas = tablaProcesosIncial.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tablaProcesos.removeRow(i);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        duracionProceso = new javax.swing.JSpinner();
        tiempoCorteESProceso = new javax.swing.JSpinner();
        duracionESProceso = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        botonInsertarProceso = new javax.swing.JButton();
        botonGenerarProcesos = new javax.swing.JButton();
        botonEliminarProceso = new javax.swing.JButton();
        botonLimpiarTablaProceso = new javax.swing.JButton();
        botonActualizarProceso = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonCargarProceso = new javax.swing.JButton();
        nombreProceso = new javax.swing.JTextField();
        tipoProceso = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        prioridadProceso = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        spinnerCargarProcesos = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProcesosIncial = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        duracionProceso.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        tiempoCorteESProceso.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        tiempoCorteESProceso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tiempoCorteESProcesoStateChanged(evt);
            }
        });

        duracionESProceso.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel4.setText("Duracion:");

        jLabel5.setText("Tiempo de bloqueo por E/S");

        jLabel6.setText("Duracion de E/S");

        botonInsertarProceso.setText("Insertar");
        botonInsertarProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInsertarProcesoActionPerformed(evt);
            }
        });

        botonGenerarProcesos.setText("Generar procesos");
        botonGenerarProcesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarProcesosActionPerformed(evt);
            }
        });

        botonEliminarProceso.setText("Eliminar");
        botonEliminarProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarProcesoActionPerformed(evt);
            }
        });

        botonLimpiarTablaProceso.setText("Limpiar Tabla");
        botonLimpiarTablaProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarTablaProcesoActionPerformed(evt);
            }
        });

        botonActualizarProceso.setText("Actualizar");
        botonActualizarProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarProcesoActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre:");

        botonCargarProceso.setText("Cargar Proceso(s)");
        botonCargarProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCargarProcesoActionPerformed(evt);
            }
        });

        nombreProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreProcesoActionPerformed(evt);
            }
        });
        nombreProceso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombreProcesoKeyReleased(evt);
            }
        });

        tipoProceso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USER", "SO-A.", "SO-N.A." }));
        tipoProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoProcesoActionPerformed(evt);
            }
        });

        jLabel2.setText("Tipo:");

        prioridadProceso.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        jLabel3.setText("Prioridad:");

        spinnerCargarProcesos.setModel(new javax.swing.SpinnerNumberModel());
        spinnerCargarProcesos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCargarProcesosStateChanged(evt);
            }
        });

        tablaProcesosIncial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProcesosIncial.getTableHeader().setReorderingAllowed(false);
        tablaProcesosIncial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProcesosIncialMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProcesosIncial);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(spinnerCargarProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonInsertarProceso)
                        .addGap(18, 18, 18)
                        .addComponent(botonEliminarProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonActualizarProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonLimpiarTablaProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonCargarProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(nombreProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tipoProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(prioridadProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(duracionProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tiempoCorteESProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(duracionESProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(botonGenerarProcesos)
                    .addContainerGap(501, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spinnerCargarProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(duracionProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(duracionESProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiempoCorteESProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prioridadProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonInsertarProceso)
                    .addComponent(botonLimpiarTablaProceso)
                    .addComponent(botonActualizarProceso)
                    .addComponent(botonEliminarProceso)
                    .addComponent(botonCargarProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(botonGenerarProcesos)
                    .addContainerGap(322, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonInsertarProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInsertarProcesoActionPerformed
        // TODO add your handling code here:
        Object[] datos = new Object[6];
        datos[0] = nombreProceso.getText();
        datos[1] = tipoProceso.getSelectedItem();
        datos[2] = prioridadProceso.getValue();
        datos[3] = duracionProceso.getValue();
        datos[4] = tiempoCorteESProceso.getValue();
        datos[5] = ((int) tiempoCorteESProceso.getValue() == 0 ? 0 : duracionESProceso.getValue());
        
        tablaProcesos.addRow(datos);
        
        nombreProceso.setText("");
        botonInsertarProceso.setEnabled(false);
        botonActualizarProceso.setEnabled(false);
        botonCargarProceso.setEnabled(true);
    }//GEN-LAST:event_botonInsertarProcesoActionPerformed

    private void botonEliminarProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarProcesoActionPerformed
        // TODO add your handling code here:
        int fila = tablaProcesosIncial.getSelectedRow();
        if(fila >= 0){
            tablaProcesos.removeRow(fila);
            botonEliminarProceso.setEnabled(false);
            botonActualizarProceso.setEnabled(false);
            if(tablaProcesosIncial.getRowCount() == 0){
                botonCargarProceso.setEnabled(false);
            }
        }
    }//GEN-LAST:event_botonEliminarProcesoActionPerformed

    private void botonLimpiarTablaProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarTablaProcesoActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
        botonEliminarProceso.setEnabled(false);
        botonActualizarProceso.setEnabled(false);
        botonCargarProceso.setEnabled(false);
    }//GEN-LAST:event_botonLimpiarTablaProcesoActionPerformed

    private void nombreProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreProcesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreProcesoActionPerformed

    private void tipoProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoProcesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoProcesoActionPerformed

    private void tiempoCorteESProcesoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tiempoCorteESProcesoStateChanged
        // TODO add your handling code here:
        int valorTiemporCorte = (int) tiempoCorteESProceso.getValue();
        if(valorTiemporCorte == 0){
            duracionESProceso.setEnabled(false);
        }else{
            duracionESProceso.setEnabled(true);
        }
    }//GEN-LAST:event_tiempoCorteESProcesoStateChanged

    private void spinnerCargarProcesosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCargarProcesosStateChanged
        // TODO add your handling code here:
        int cantProcesoGenerar = (int) spinnerCargarProcesos.getValue();
        if(cantProcesoGenerar == 0){
            botonGenerarProcesos.setEnabled(false);
        }else{
            botonGenerarProcesos.setEnabled(true);
        }
    }//GEN-LAST:event_spinnerCargarProcesosStateChanged

    private void nombreProcesoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreProcesoKeyReleased
        // TODO add your handling code here
        if (!nombreProceso.getText().equals("")){
            botonInsertarProceso.setEnabled(true);
        }else{
            botonInsertarProceso.setEnabled(false);
        }
        
    }//GEN-LAST:event_nombreProcesoKeyReleased

    private void tablaProcesosIncialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProcesosIncialMouseClicked
        // TODO add your handling code here:
        botonEliminarProceso.setEnabled(true);
        botonActualizarProceso.setEnabled(true);
    }//GEN-LAST:event_tablaProcesosIncialMouseClicked

    private void botonActualizarProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarProcesoActionPerformed
        // TODO add your handling code here:
        int fila = tablaProcesosIncial.getSelectedRow();
        if(fila >= 0){
            if (!nombreProceso.getText().toString().equals("")){
                tablaProcesos.setValueAt(nombreProceso.getText() , fila, 0);
            }
            tablaProcesos.setValueAt(tipoProceso.getSelectedItem(), fila, 1);
            tablaProcesos.setValueAt(prioridadProceso.getValue(), fila, 2);
            tablaProcesos.setValueAt(duracionProceso.getValue(), fila, 3);
            tablaProcesos.setValueAt(tiempoCorteESProceso.getValue(), fila, 4);
            tablaProcesos.setValueAt(((int) tiempoCorteESProceso.getValue() == 0 ? 0 : duracionESProceso.getValue()), fila, 5);
        }
    }//GEN-LAST:event_botonActualizarProcesoActionPerformed

    private void botonCargarProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCargarProcesoActionPerformed
        // TODO add your handling code here:
        LinkedList<Proceso> listaProcesos = new LinkedList<>();
        
        int filas = tablaProcesosIncial.getRowCount();
        for (int i = 0; i < filas; i++){
            String nombre = tablaProcesos.getValueAt(i, 0).toString();
            boolean tipo = (tablaProcesos.getValueAt(i, 1).toString()).contains("SO");
            boolean apropiativo = ((tablaProcesos.getValueAt(i, 1).toString()).equals("SO-A.") | !tipo);
            int prioridad = (int) tablaProcesos.getValueAt(i, 2);
            int duracion = (int) tablaProcesos.getValueAt(i, 3);
            int tempCorteES = (int) tablaProcesos.getValueAt(i, 4);
            int duracionES = (int) tablaProcesos.getValueAt(i, 5);
            
            listaProcesos.add(new Proceso(nombre, prioridad, tipo, apropiativo, duracion, tempCorteES, duracionES));
        }
        
        limpiarTabla();
        
        botonInsertarProceso.setEnabled(false);
        botonActualizarProceso.setEnabled(false);
        botonEliminarProceso.setEnabled(false);
        botonCargarProceso.setEnabled(false);
        
        try {
            ventanaPrincipal.agregarProcesos(listaProcesos);
        } catch (InterruptedException ex) {
            Logger.getLogger(CreadorProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dispose();
    }//GEN-LAST:event_botonCargarProcesoActionPerformed

    private void botonGenerarProcesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarProcesosActionPerformed
        // TODO add your handling code here:
        
        int cantidadProcesos = (int) spinnerCargarProcesos.getValue();
        for (int i = 0; i < cantidadProcesos; i++){
            Object[] datos = new Object[6];
            datos[0] = "p-A-"+cantProcesosAuto;
            if(cantProcesosAuto% 10 == 0){
                if(cantProcesosAuto % 40 == 0){
                    datos[1] = "SO-N.A.";
                }else{
                    datos[1] = "SO-A.";
                }
            }else{
                datos[1] = "USER";
            }
            datos[2] = (int)(Math.random() * ((99 - 1) + 1)) + 1;
            datos[3] = (int)(Math.random() * ((200 - 1) + 1)) + 1;
            datos[4] = (int)(Math.random() * ((50 - 0) + 1)) + 0;
            datos[5] = ( ((int) datos[4]) == 0 ? 0 : (int)(Math.random() * ((200 - 1) + 1)) + 1);
        
            tablaProcesos.addRow(datos);
            cantProcesosAuto++;
        }
        botonCargarProceso.setEnabled(true);
    }//GEN-LAST:event_botonGenerarProcesosActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    //break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Creador_CPUs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Creador_CPUs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Creador_CPUs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Creador_CPUs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Creador_CPUs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizarProceso;
    private javax.swing.JButton botonCargarProceso;
    private javax.swing.JButton botonEliminarProceso;
    private javax.swing.JButton botonGenerarProcesos;
    private javax.swing.JButton botonInsertarProceso;
    private javax.swing.JButton botonLimpiarTablaProceso;
    private javax.swing.JSpinner duracionESProceso;
    private javax.swing.JSpinner duracionProceso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nombreProceso;
    private javax.swing.JSpinner prioridadProceso;
    private javax.swing.JSpinner spinnerCargarProcesos;
    private javax.swing.JTable tablaProcesosIncial;
    private javax.swing.JSpinner tiempoCorteESProceso;
    private javax.swing.JComboBox<String> tipoProceso;
    // End of variables declaration//GEN-END:variables
}
