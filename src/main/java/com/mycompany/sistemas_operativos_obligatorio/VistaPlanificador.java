/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemas_operativos_obligatorio;
import Planificador.Planificador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import Procesos.Proceso;
import java.awt.Color;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
/**
 *
 * @author matia
 */
public final class VistaPlanificador extends javax.swing.JFrame {

    private int instanceas = 0;
    
    Planificador instancePlanificador;
    VistaPlanificador ventanaPlani;
    CreadorProcesos ventanaCreadora;
    Timer interrupcion;
    
    float tiempoSegs;
    
    int cantidadCiclos = 0;
    
    DefaultTableModel tablaProcesosSOA;
    DefaultTableModel tablaProcesosSOB;
    DefaultTableModel tablaProcesosNuevosA;
    DefaultTableModel tablaProcesosNuevosB;
    DefaultTableModel tablaProcesosCPUA;
    DefaultTableModel tablaProcesosCPUB;
    DefaultTableModel tablaProcesosESA;
    DefaultTableModel tablaProcesosESB;
    
    DefaultTableModel tablaProcesosEjecucion;
    
    DefaultTableModel tablaProcesosBloqueados;
    
    DefaultTableModel tablaProcesosSuspendidos;
    
    ActionListener ejecutarCiclo = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            instancePlanificador.ejecutarCiclo();
            ventanaPlani.cantidadCiclos++;
            ventanaPlani.actualizarTablaEjecucion();
            ventanaPlani.actualizarVentanasDatos();
            ventanaPlani.actualiazarDatosCriticos();
            System.out.println("POOM");
        }
    };
    
    /**
     * Creates new form VistaPlanificador
     * @param cantCPUs
     * @param tiempoSegs
     * @param cantCiclosEjecucionCPUs
     * @param ventanaCreadora
     */
    public VistaPlanificador(int cantCPUs, float tiempoSegs, int cantCiclosEjecucionCPUs, CreadorProcesos ventanaCreadora) {
        initComponents();
        
        instanceas++;
        
        this.instancePlanificador = Planificador.create(cantCiclosEjecucionCPUs, cantCPUs);
        
        this.ventanaCreadora = ventanaCreadora;
        
        this.ventanaPlani = this;
        
        this.tiempoSegs = tiempoSegs;
        
        this.getContentPane().setBackground(new Color(51,255,255));
        ArrayList<DefaultTableModel> listaTablasModels = new ArrayList<>();
        
        tablaProcesosSOA = new DefaultTableModel();
        tablaProcesosSOB = new DefaultTableModel();
        tablaProcesosNuevosA = new DefaultTableModel();
        tablaProcesosNuevosB = new DefaultTableModel();
        tablaProcesosCPUA = new DefaultTableModel();
        tablaProcesosCPUB = new DefaultTableModel();
        tablaProcesosESA = new DefaultTableModel();
        tablaProcesosESB = new DefaultTableModel();
    
        listaTablasModels.add(tablaProcesosSOA);
        listaTablasModels.add(tablaProcesosSOB);
        listaTablasModels.add(tablaProcesosNuevosA);
        listaTablasModels.add(tablaProcesosNuevosB);
        listaTablasModels.add(tablaProcesosCPUA);
        listaTablasModels.add(tablaProcesosCPUB);
        listaTablasModels.add(tablaProcesosESA);
        listaTablasModels.add(tablaProcesosESB);
        
        for (DefaultTableModel t : listaTablasModels){
            t.addColumn("ID");
            t.addColumn("Nombre");
            t.addColumn("Tipo");
            t.addColumn("Prioridad");
            t.addColumn("% Completado");
            t.addColumn("Ciclos para E/S");
        }
        
        colaSOA.setModel(listaTablasModels.get(0));
        colaSOB.setModel(listaTablasModels.get(1));
        colaNuevosA.setModel(listaTablasModels.get(2));
        colaNuevosB.setModel(listaTablasModels.get(3));
        colaCPUA.setModel(listaTablasModels.get(4));
        colaCPUB.setModel(listaTablasModels.get(5));
        colaESA.setModel(listaTablasModels.get(6));
        colaESB.setModel(listaTablasModels.get(7));
        
        tablaProcesosEjecucion = new DefaultTableModel();
        
        tablaProcesosEjecucion.addColumn("CPU");
        tablaProcesosEjecucion.addColumn("ID");
        tablaProcesosEjecucion.addColumn("Nombre");
        tablaProcesosEjecucion.addColumn("Tipo");
        tablaProcesosEjecucion.addColumn("Prioridad");
        tablaProcesosEjecucion.addColumn("Ciclos para terminar");
        tablaProcesosEjecucion.addColumn("% Completado");
        tablaProcesosEjecucion.addColumn("Ciclos para E/S");
        tablaProcesosEjecucion.addColumn("Ciclos para sacarlo del CPU");

        tablaEjecucion.setModel(tablaProcesosEjecucion);
        
        
        tablaProcesosBloqueados = new DefaultTableModel();
        
        tablaProcesosBloqueados.addColumn("ID");
        tablaProcesosBloqueados.addColumn("Nombre");
        tablaProcesosBloqueados.addColumn("Tipo");
        tablaProcesosBloqueados.addColumn("Prioridad");
        tablaProcesosBloqueados.addColumn("Duracion E/S");
        tablaProcesosBloqueados.addColumn("Tiempor restante de E/S");
        
        tablaProcesosBloqueadosInicial.setModel(tablaProcesosBloqueados);
        
        tablaProcesosSuspendidos = new DefaultTableModel();
        
        tablaProcesosSuspendidos.addColumn("ID");
        tablaProcesosSuspendidos.addColumn("Nombre");
        tablaProcesosSuspendidos.addColumn("Tipo");
        tablaProcesosSuspendidos.addColumn("Prioridad");
        tablaProcesosSuspendidos.addColumn("% completado");
        
        tablaProcesosSuspendidosInicial.setModel(tablaProcesosSuspendidos);
        
        colaCPU1.setText("COLA EXPIRADOS");
        colaCPU1.setBackground(Color.red);
        colaCPU2.setText("COLA DE EJECUCION");
        colaCPU2.setBackground(Color.green);
        
        tablaEjecucion.setEnabled(false);
        
        
        
        desabilitarBotones();
    }
    
    private void actualiazarDatosCriticos(){
        ventanaPlani.actualizarTablasColas();
        ventanaPlani.obtenerProximoProceso();
        ventanaPlani.actualizarTablaBloqueados();
        ventanaPlani.actualizarTablaSuspendidos();
        ventanaPlani.actualizarBarrasDatos();
        ventanaPlani.desabilitarBotones();
    }
    
    public void agregarProcesos(LinkedList<Proceso> listaProcesos){
        this.instancePlanificador.ingresarProcesos(listaProcesos);
        if(!this.isVisible()){
            this.setVisible(true);
            boolean primeraIteracion = false;
            if (interrupcion == null){
                primeraIteracion = true;
                interrupcion = new Timer((int) (tiempoSegs*1000), ejecutarCiclo);
                interrupcion.start();
            }
            if(primeraIteracion){
                instancePlanificador.ejecutarCiclo();
                ventanaPlani.actualizarTablaEjecucion();
                ventanaPlani.actualizarTablasColas();
                inicializarBarrasColas("B");
            } 
        }else{
            reajustarBarrasColas(listaProcesos.size());
            actualizarTablasColas();
            obtenerProximoProceso();
            actualizarVentanasDatos();
            actualizarBarrasDatos();
        }
    }
    
    public void actualizarVentanasDatos(){
        cantidadProcesos.setText(String.valueOf(instancePlanificador.getCantidadProcesos()));
        quantunn.setText(String.valueOf(instancePlanificador.getTiemporEjecucionCPUs()));
        ciclosTranscuridos.setText(String.valueOf(cantidadCiclos));
    }
    
    public void actualizarTablaEjecucion(){
        int filas = tablaProcesosEjecucion.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tablaProcesosEjecucion.removeRow(i);
        }
        Object[] datos = new Object[9];
        ArrayList<Proceso> listaProcesosEje = instancePlanificador.getProcesosEnEjecucion();
        int contador = 1;
        for(Proceso p : listaProcesosEje){
            if(p == null){
                datos[0] = contador;
                datos[1] = "LIBRE";
                datos[2] = "LIBRE";
                datos[3] = "LIBRE";
                datos[4] = "LIBRE";
                datos[5] = "LIBRE";
                datos[6] = "LIBRE";
                datos[7] = "LIBRE";
                datos[8] = "LIBRE";
            }else{
                datos[0] = contador;
                datos[1] = p.getID();
                datos[2] = p.getNombre();
                datos[3] = (p.isOfSO() ? "SO" : "USER");
                datos[4] = p.getPrioridad();
                datos[5] = p.getValoresEjecucionProceso()[0];
                datos[6] = (-(((p.getValoresEjecucionProceso()[0] * 100) / p.getDuracion())-100) + "%");
                datos[7] = (p.getValoresEjecucionProceso()[1] < 0 ? 0 : p.getValoresEjecucionProceso()[1]);
                datos[8] = instancePlanificador.getTiempoEjecucionRestanteProceso(contador - 1);
            }
            tablaProcesosEjecucion.addRow(datos);
            contador++;
        }
    }
    
    public void actualizarTablaBloqueados(){
        int filas = tablaProcesosBloqueados.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tablaProcesosBloqueados.removeRow(i);
        }
        Object[] datos = new Object[6];
        ArrayList<Proceso> listaBloqueados = instancePlanificador.getBloqueadosPorES();
        for(Proceso p : listaBloqueados){
            datos[0] = p.getID();
            datos[1] = p.getNombre();
            datos[2] = (p.isOfSO() ? "SO" : "USER");
            datos[3] = p.getPrioridad();
            datos[4] = p.getDuracionES();
            datos[5] = p.getValoresEjecucionProceso()[2];
            tablaProcesosBloqueados.addRow(datos);
        }
    }
    
    public void actualizarTablaSuspendidos(){
        int filas = tablaProcesosSuspendidos.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tablaProcesosSuspendidos.removeRow(i);
        }
        Object[] datos = new Object[5];
        Set<Proceso> listaSuspendidos = instancePlanificador.getSuspendidos();
        for(Proceso p : listaSuspendidos){
            datos[0] = p.getID();
            datos[1] = p.getNombre();
            datos[2] = (p.isOfSO() ? "SO" : "USER");
            datos[3] = p.getPrioridad();
            datos[4] = (-(((p.getValoresEjecucionProceso()[0] * 100) / p.getDuracion())-100) + "%");
            tablaProcesosSuspendidos.addRow(datos);
        }
    }
    
    public void actualizarTablasColas(){
        if(this.instancePlanificador.getHuvoCambioContextoColas()){
            actualizarTituloColas();
            if(colaCPU1.getText().equals("COLA EXPIRADOS")){
                inicializarBarrasColas("B");
            }else{
                inicializarBarrasColas("A");
            }
        }
        if(colaCPU1.getText().equals("COLA EXPIRADOS")){
            
            ArrayList<Proceso> colaProceso = instancePlanificador.getColaDeExpiradosSO();
            actualizarTablaColaProcesos(tablaProcesosSOA, colaProceso);
            barraProgresoSOA.setMaximum(colaProceso.size());
            barraProgresoSOA.setValue(colaProceso.size());
            
            actualizarTablaColaProcesos(tablaProcesosNuevosA, null);
                    
            colaProceso = instancePlanificador.getColaDeExpiradosLimitadosCPU();
            actualizarTablaColaProcesos(tablaProcesosCPUA, colaProceso);
            barraProgresoCPUA.setMaximum(colaProceso.size());
            barraProgresoCPUA.setValue(colaProceso.size());
                        
            colaProceso = instancePlanificador.getColaDeExpiradosLimitadosES();
            actualizarTablaColaProcesos(tablaProcesosESA, colaProceso);
            barraProgresoESA.setMaximum(colaProceso.size());
            barraProgresoESA.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionSO();
            actualizarTablaColaProcesos(tablaProcesosSOB, colaProceso);
            barraProgresoSOB.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionNuevos();
            actualizarTablaColaProcesos(tablaProcesosNuevosB, colaProceso);
            barraProgresoNuevosB.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionLimitadosCPU();
            actualizarTablaColaProcesos(tablaProcesosCPUB, colaProceso);
            barraProgresoCPUB.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionLimitadosES();
            actualizarTablaColaProcesos(tablaProcesosESB, colaProceso);
            barraProgresoESB.setValue(colaProceso.size());
            
        }else{
            
            ArrayList<Proceso> colaProceso = instancePlanificador.getColaDeExpiradosSO();
            actualizarTablaColaProcesos(tablaProcesosSOB, colaProceso);
            barraProgresoSOB.setMaximum(colaProceso.size());
            barraProgresoSOB.setValue(colaProceso.size());
            
            actualizarTablaColaProcesos(tablaProcesosNuevosB, null);
                    
            colaProceso = instancePlanificador.getColaDeExpiradosLimitadosCPU();
            actualizarTablaColaProcesos(tablaProcesosCPUB, colaProceso);
            barraProgresoCPUB.setMaximum(colaProceso.size());
            barraProgresoCPUB.setValue(colaProceso.size());
                        
            colaProceso = instancePlanificador.getColaDeExpiradosLimitadosES();
            actualizarTablaColaProcesos(tablaProcesosESB, colaProceso);
            barraProgresoESB.setMaximum(colaProceso.size());
            barraProgresoESB.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionSO();
            actualizarTablaColaProcesos(tablaProcesosSOA, colaProceso);
            barraProgresoSOA.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionNuevos();
            actualizarTablaColaProcesos(tablaProcesosNuevosA, colaProceso);
            barraProgresoNuevosA.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionLimitadosCPU();
            actualizarTablaColaProcesos(tablaProcesosCPUA, colaProceso);
            barraProgresoCPUA.setValue(colaProceso.size());
            
            colaProceso = instancePlanificador.getColaDejecucionLimitadosES();
            actualizarTablaColaProcesos(tablaProcesosESA, colaProceso);
            barraProgresoESA.setValue(colaProceso.size());
        }
    }
    
    public void actualizarTablaColaProcesos(DefaultTableModel tabla, ArrayList<Proceso> listaProcesos){
        int filas = tabla.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tabla.removeRow(i);
        }
        if(listaProcesos != null){
            Object[] datos = new Object[6];
            for (int i = listaProcesos.size() - 1; i >= 0; i--){
                Proceso p = listaProcesos.get(i);
                datos[0] = p.getID();
                datos[1] = p.getNombre();
                datos[2] = (p.isOfSO() ? "SO" : "USER");
                datos[3] = p.getPrioridad();
                datos[4] = (-(((p.getValoresEjecucionProceso()[0] * 100) / p.getDuracion())-100) + "%");
                datos[5] = p.getTiemporCorteES();
                tabla.addRow(datos);
            }  
        }
    }
    
    public void inicializarBarrasColas(String tipo){
        
        javax.swing.JProgressBar[] barras = new javax.swing.JProgressBar[8];
        barras[0] = barraProgresoSOA;
        barras[1] = barraProgresoSOB;
        barras[2] = barraProgresoNuevosA;
        barras[3] = barraProgresoNuevosB;
        barras[4] = barraProgresoCPUA;
        barras[5] = barraProgresoCPUB;
        barras[6] = barraProgresoESA;
        barras[7] = barraProgresoESB;
        
        int com;
        if (tipo.equals("B")){
            com = 1;
        }else{
            com = 0;
        }
        int cantidadEnCola = 0;
        boolean primeroATomar = true;
        for (int i = com; i < barras.length; i += 2){
            switch (i){
                case 0 -> cantidadEnCola = instancePlanificador.getColaDejecucionSO().size();
                case 1 -> cantidadEnCola = instancePlanificador.getColaDejecucionSO().size();
                case 2 -> cantidadEnCola = instancePlanificador.getColaDejecucionNuevos().size();
                case 3 -> cantidadEnCola = instancePlanificador.getColaDejecucionNuevos().size();
                case 4 -> cantidadEnCola = instancePlanificador.getColaDejecucionLimitadosCPU().size();
                case 5 -> cantidadEnCola = instancePlanificador.getColaDejecucionLimitadosCPU().size();
                case 6 -> cantidadEnCola = instancePlanificador.getColaDejecucionLimitadosES().size();
                case 7 -> cantidadEnCola = instancePlanificador.getColaDejecucionLimitadosES().size();   
            }
            if((i == 1 | i == 0) & cantidadEnCola != 0){
                primeroATomar = false;
                barras[i].setMaximum(cantidadEnCola + 1);
                barras[i].setValue(barras[i].getMaximum());
            }else if(primeroATomar & cantidadEnCola != 0){
                primeroATomar = false;
                barras[i].setMaximum(cantidadEnCola + 1);
                barras[i].setValue(barras[i].getMaximum());
            }else{
                barras[i].setMaximum(cantidadEnCola);
                barras[i].setValue(barras[i].getMaximum()); 
            }
        }
    }
    
    public void reajustarBarrasColas(int cantidadNuevos){
        int procesosSONuevos;
        if(colaCPU1.getText().equals("COLA EXPIRADOS")){
            procesosSONuevos = instancePlanificador.getCantProcesosSO() - barraProgresoSOB.getMaximum();
            barraProgresoSOB.setMaximum(barraProgresoSOB.getMaximum() + procesosSONuevos);
            barraProgresoSOB.setValue(tablaProcesosSOB.getRowCount() + procesosSONuevos);
            
            procesosSONuevos = cantidadNuevos - procesosSONuevos;
            barraProgresoNuevosB.setMaximum(barraProgresoNuevosB.getMaximum() + procesosSONuevos);
            barraProgresoNuevosB.setValue(tablaProcesosNuevosB.getRowCount() + procesosSONuevos);
        }else{
            procesosSONuevos = instancePlanificador.getCantProcesosSO() - barraProgresoSOA.getMaximum();
            barraProgresoSOA.setMaximum(barraProgresoSOA.getMaximum() + procesosSONuevos);
            barraProgresoSOA.setValue(tablaProcesosSOA.getRowCount() + procesosSONuevos);
            
            procesosSONuevos = cantidadNuevos - procesosSONuevos;
            barraProgresoNuevosA.setMaximum(barraProgresoNuevosA.getMaximum() + procesosSONuevos);
            barraProgresoNuevosA.setValue(tablaProcesosSOB.getRowCount() + procesosSONuevos);
        }
    }
    
    public void actualizarTituloColas(){
        if (colaCPU1.getText().equals("COLA EXPIRADOS")){
            colaCPU2.setText("COLA EXPIRADOS");
            colaCPU2.setBackground(Color.red);
            colaCPU1.setText("COLA DE EJECUCION");
            colaCPU1.setBackground(Color.green);
        }else{
            colaCPU1.setText("COLA EXPIRADOS");
            colaCPU1.setBackground(Color.red);
            colaCPU2.setText("COLA DE EJECUCION");
            colaCPU2.setBackground(Color.green);
        }
    }
    
    public void actualizarBarrasDatos(){
        
        int cantidad1 = instancePlanificador.cantidadCPUs();
        int cantidad2 = instancePlanificador.cantCPUsEnUso();
        barraUsoDeCPU.setMaximum(cantidad1);
        barraUsoDeCPU.setValue(cantidad2);
        etiquetaProcentajeUsoCPUs.setText(String.valueOf(cantidad2 * 100 / cantidad1) + "%");
        
        cantidad1 = instancePlanificador.getCantidadProcesos();
        cantidad2 = instancePlanificador.getProcesosSuspendidos();
        barraSuspendidos.setMaximum(cantidad1);
        barraSuspendidos.setValue(cantidad2);
        cantidad1 =(cantidad1 == 0 ? 1 :cantidad1);
        etiquetaProcentajeProcesosSuspendidos.setText(String.valueOf(cantidad2 * 100 / cantidad1) + "%");
        
        cantidad1 = instancePlanificador.getCantidadProcesos();
        cantidad2 = instancePlanificador.getCantProcesosSO();
        barraSOYUSER.setMaximum(instancePlanificador.getCantidadProcesos());
        barraSOYUSER.setValue(instancePlanificador.getCantProcesosSO());
        cantidad1 =(cantidad1 == 0 ? 1 :cantidad1);
        etiquetaPorcentajeProcesosSO.setText("("+String.valueOf(cantidad2 * 100 / cantidad1) + "%"+")");
        etiquetaPorcentajeProcesosUSER.setText("("+String.valueOf(100 - (cantidad2 * 100 / cantidad1)) + "%"+")");
        
        cantidad1 = instancePlanificador.getCantProceosBloqueados() + instancePlanificador.getProcesosListos();
        cantidad2 = instancePlanificador.getProcesosListos();
        barraBloqueadosYEjecucion.setMaximum(cantidad1);
        barraBloqueadosYEjecucion.setValue(cantidad2);
        cantidad1 =(cantidad1 == 0 ? 1 :cantidad1);
        etiquetaPorcentajeProcesosListos.setText("("+String.valueOf(cantidad2 * 100 / cantidad1) + "%"+")");
        etiquetaPorcentajeProcesosBloqueados.setText("("+String.valueOf(100 - (cantidad2 * 100 / cantidad1)) + "%"+")");
    }
    
    public void obtenerProximoProceso(){
        Proceso proceso = instancePlanificador.seleccionarProximoProcesoAEjecutar();
        if(proceso != null){
            poximoProcesoID.setText(String.valueOf(proceso.getID()));
            poximoProcesoNombre.setText(proceso.getNombre());
            poximoProcesoTipo.setText((proceso.isOfSO() ? "SO" : "USER"));
            poximoProcesoPrioridad.setText(String.valueOf(proceso.getPrioridad()));
            poximoProcesoDuracion.setText(String.valueOf(proceso.getDuracion()));
            poximoProcesoTiempoRestante.setText(String.valueOf(proceso.getValoresEjecucionProceso()[0]));
            poximoProcesoCiclosParaES.setText(String.valueOf((proceso.getTiemporCorteES() == 0 ? "NO TIENE" : proceso.getTiemporCorteES())));
            poximoProcesoParaProximaES.setText(String.valueOf((proceso.getTiemporCorteES() == 0 ? "NO TIENE" : proceso.getValoresEjecucionProceso()[1])));
            poximoProcesoDuracionES.setText(String.valueOf((proceso.getTiemporCorteES() == 0 ? "NO TIENE" : proceso.getDuracionES())));
        }else{
            poximoProcesoID.setText("NO HAY");
            poximoProcesoNombre.setText("NO HAY");
            poximoProcesoTipo.setText("NO HAY");
            poximoProcesoPrioridad.setText("NO HAY");
            poximoProcesoDuracion.setText("NO HAY");
            poximoProcesoTiempoRestante.setText("NO HAY");
            poximoProcesoCiclosParaES.setText("NO HAY");
            poximoProcesoParaProximaES.setText("NO HAY");
            poximoProcesoDuracionES.setText("NO HAY");
        }
    }
    
    public void limpiarSeleccionTablas(javax.swing.JTable tablaHabilitada){
        if(!colaSOA.equals(tablaHabilitada)){
            colaSOA.clearSelection();
        }if(!colaSOB.equals(tablaHabilitada)){
            colaSOB.clearSelection();
        }if(!colaNuevosA.equals(tablaHabilitada)){
            colaNuevosA.clearSelection();
        }if(!colaSOB.equals(tablaHabilitada)){
            colaSOB.clearSelection();
        }if(!colaCPUB.equals(tablaHabilitada)){
            colaCPUB.clearSelection();
        }if(!colaCPUA.equals(tablaHabilitada)){
            colaCPUA.clearSelection();
        }if(!colaESB.equals(tablaHabilitada)){
            colaESB.clearSelection();
        }if(!colaESA.equals(tablaHabilitada)){
            colaESA.clearSelection();
        }if(!tablaProcesosBloqueadosInicial.equals(tablaHabilitada)){
            tablaProcesosBloqueadosInicial.clearSelection();
        }if(!tablaProcesosSuspendidosInicial.equals(tablaHabilitada)){
            tablaProcesosSuspendidosInicial.clearSelection();
        }
    }
    
    public void desabilitarBotones(){
        if(idDelProcesosAInteractuar.getText().equals("")){
            botonSuspenderProceso.setEnabled(false);
            botonDesuspenderProceso.setEnabled(false);
            botonEliminarProceso.setEnabled(false);
            botonCambiarPrioridad.setEnabled(false);
        }
    }
    
    public void habilitarBotones(){
        botonSuspenderProceso.setEnabled(true);
        botonEliminarProceso.setEnabled(true);
        botonCambiarPrioridad.setEnabled(true);
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
        tablaEjecucion = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jLabel1 = new javax.swing.JLabel();
        poximoProcesoNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        poximoProcesoTipo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        poximoProcesoPrioridad = new javax.swing.JTextField();
        poximoProcesoDuracion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        poximoProcesoCiclosParaES = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        poximoProcesoDuracionES = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        poximoProcesoTiempoRestante = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        poximoProcesoID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        poximoProcesoParaProximaES = new javax.swing.JTextField();
        barraProgresoNuevosA = new javax.swing.JProgressBar();
        barraProgresoCPUA = new javax.swing.JProgressBar();
        barraProgresoESA = new javax.swing.JProgressBar();
        barraProgresoSOA = new javax.swing.JProgressBar();
        colaCPU1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        colaESA = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jScrollPane3 = new javax.swing.JScrollPane();
        colaSOA = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jScrollPane4 = new javax.swing.JScrollPane();
        colaNuevosA = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jScrollPane5 = new javax.swing.JScrollPane();
        colaCPUA = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaProcesosBloqueadosInicial = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaProcesosSuspendidosInicial = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        colaCPU2 = new javax.swing.JTextField();
        barraProgresoNuevosB = new javax.swing.JProgressBar();
        barraProgresoCPUB = new javax.swing.JProgressBar();
        barraProgresoESB = new javax.swing.JProgressBar();
        jScrollPane10 = new javax.swing.JScrollPane();
        colaESB = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jScrollPane13 = new javax.swing.JScrollPane();
        colaCPUB = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        jScrollPane14 = new javax.swing.JScrollPane();
        colaSOB = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        botonSuspenderProceso = new javax.swing.JButton();
        botonDesuspenderProceso = new javax.swing.JButton();
        botonCambiarPrioridad = new javax.swing.JButton();
        spinnerPrioridad = new javax.swing.JSpinner();
        jButton4 = new javax.swing.JButton();
        jSpinner2 = new javax.swing.JSpinner();
        botonEliminarProceso = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        botonPausar = new javax.swing.JButton();
        barraUsoDeCPU = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();
        barraBloqueadosYEjecucion = new javax.swing.JProgressBar();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        barraSOYUSER = new javax.swing.JProgressBar();
        jLabel19 = new javax.swing.JLabel();
        barraSuspendidos = new javax.swing.JProgressBar();
        ciclosTranscuridos = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        quantunn = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        cantidadProcesos = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        barraProgresoSOB = new javax.swing.JProgressBar();
        jLabel23 = new javax.swing.JLabel();
        etiquetaProcentajeUsoCPUs = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        etiquetaProcentajeProcesosSuspendidos = new javax.swing.JLabel();
        etiquetaPorcentajeProcesosUSER = new javax.swing.JLabel();
        etiquetaPorcentajeProcesosSO = new javax.swing.JLabel();
        etiquetaPorcentajeProcesosListos = new javax.swing.JLabel();
        etiquetaPorcentajeProcesosBloqueados = new javax.swing.JLabel();
        idDelProcesosAInteractuar = new javax.swing.JTextField();
        jScrollPane16 = new javax.swing.JScrollPane();
        colaNuevosB = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tablaEjecucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CPU", "ID", "Nombre", "Tipo", "Prioridad", "Ciclos para terminar", "% completado", "Ciclos para sacarlo del CPU", "Ciclos para E/S"
            }
        ));
        jScrollPane1.setViewportView(tablaEjecucion);

        jLabel1.setText("Procesos ejecutandose:");

        poximoProcesoNombre.setEditable(false);

        jLabel8.setText("Proximo proceso:");

        jLabel14.setText("Nombre:");

        poximoProcesoTipo.setEditable(false);

        jLabel13.setText("Tipo:");

        jLabel12.setText("Prioridad:");

        poximoProcesoPrioridad.setEditable(false);

        poximoProcesoDuracion.setEditable(false);

        jLabel11.setText("Duracion:");

        jLabel10.setText("Ciclos para E/S:");

        poximoProcesoCiclosParaES.setEditable(false);

        jLabel9.setText("Duracion de E/S");

        poximoProcesoDuracionES.setEditable(false);

        jLabel2.setText("Tiempo restante:");

        poximoProcesoTiempoRestante.setEditable(false);

        jLabel3.setText("ID:");

        poximoProcesoID.setEditable(false);

        jLabel4.setText("Ciclos para proxima E/S");

        poximoProcesoParaProximaES.setEditable(false);
        poximoProcesoParaProximaES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poximoProcesoParaProximaESActionPerformed(evt);
            }
        });

        barraProgresoNuevosA.setBackground(new java.awt.Color(0, 0, 102));
        barraProgresoNuevosA.setString("NUEVOS");
        barraProgresoNuevosA.setStringPainted(true);

        barraProgresoCPUA.setBackground(new java.awt.Color(255, 0, 0));
        barraProgresoCPUA.setString("LIMITADOS POR CPU");
        barraProgresoCPUA.setStringPainted(true);

        barraProgresoESA.setBackground(new java.awt.Color(0, 153, 153));
        barraProgresoESA.setString("LIMITADOS POR E/S");
        barraProgresoESA.setStringPainted(true);

        barraProgresoSOA.setBackground(Color.BLACK);
        barraProgresoSOA.setString("SO");
        barraProgresoSOA.setStringPainted(true);

        colaCPU1.setEditable(false);
        colaCPU1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaCPU1ActionPerformed(evt);
            }
        });

        colaESA.setModel(new javax.swing.table.DefaultTableModel(
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
        colaESA.setFocusable(false);
        colaESA.getTableHeader().setReorderingAllowed(false);
        colaESA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaESAMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(colaESA);

        colaSOA.setModel(new javax.swing.table.DefaultTableModel(
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
        colaSOA.setFocusable(false);
        colaSOA.getTableHeader().setReorderingAllowed(false);
        colaSOA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaSOAMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(colaSOA);

        colaNuevosA.setModel(new javax.swing.table.DefaultTableModel(
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
        colaNuevosA.setFocusable(false);
        colaNuevosA.getTableHeader().setReorderingAllowed(false);
        colaNuevosA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaNuevosAMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(colaNuevosA);

        colaCPUA.setModel(new javax.swing.table.DefaultTableModel(
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
        colaCPUA.setFocusable(false);
        colaCPUA.getTableHeader().setReorderingAllowed(false);
        colaCPUA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaCPUAMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(colaCPUA);

        jLabel5.setText("Procesos bloqueados:");

        jLabel6.setText("Procesos suspendidos");

        tablaProcesosBloqueadosInicial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaProcesosBloqueadosInicial.setFocusable(false);
        tablaProcesosBloqueadosInicial.getTableHeader().setReorderingAllowed(false);
        tablaProcesosBloqueadosInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProcesosBloqueadosInicialMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tablaProcesosBloqueadosInicial);

        tablaProcesosSuspendidosInicial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaProcesosSuspendidosInicial.setFocusable(false);
        tablaProcesosSuspendidosInicial.getTableHeader().setReorderingAllowed(false);
        tablaProcesosSuspendidosInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProcesosSuspendidosInicialMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tablaProcesosSuspendidosInicial);

        colaCPU2.setEditable(false);
        colaCPU2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaCPU2ActionPerformed(evt);
            }
        });

        barraProgresoNuevosB.setBackground(new java.awt.Color(0, 0, 102));
        barraProgresoNuevosB.setString("NUEVOS");
        barraProgresoNuevosB.setStringPainted(true);

        barraProgresoCPUB.setBackground(new java.awt.Color(255, 0, 0));
        barraProgresoCPUB.setString("LIMITADOS POR CPU"); // NOI18N
        barraProgresoCPUB.setStringPainted(true);

        barraProgresoESB.setBackground(new java.awt.Color(0, 153, 153));
        barraProgresoESB.setString("LIMITADOS POR E/S");
        barraProgresoESB.setStringPainted(true);

        colaESB.setModel(new javax.swing.table.DefaultTableModel(
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
        colaESB.setFocusable(false);
        colaESB.getTableHeader().setReorderingAllowed(false);
        colaESB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaESBMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(colaESB);

        colaCPUB.setModel(new javax.swing.table.DefaultTableModel(
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
        colaCPUB.setFocusable(false);
        colaCPUB.getTableHeader().setReorderingAllowed(false);
        colaCPUB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaCPUBMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(colaCPUB);

        colaSOB.setModel(new javax.swing.table.DefaultTableModel(
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
        colaSOB.setFocusable(false);
        colaSOB.getTableHeader().setReorderingAllowed(false);
        colaSOB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaSOBMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(colaSOB);

        botonSuspenderProceso.setText("Suspender");
        botonSuspenderProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSuspenderProcesoActionPerformed(evt);
            }
        });

        botonDesuspenderProceso.setText("Desuspender");
        botonDesuspenderProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDesuspenderProcesoActionPerformed(evt);
            }
        });

        botonCambiarPrioridad.setText("Cambiar Prioridad");
        botonCambiarPrioridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCambiarPrioridadActionPerformed(evt);
            }
        });

        spinnerPrioridad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        jButton4.setText("Cambiar ciclos para corte por CPU");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        botonEliminarProceso.setText("Eliminar");

        jButton6.setText("Insertar");

        botonPausar.setText("PAUSAR");
        botonPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPausarActionPerformed(evt);
            }
        });

        jLabel7.setText("Uso de CPUs");

        jLabel15.setText("Procesos SO");

        jLabel16.setText("Procesos USER");

        jLabel17.setText("Procesos bloqueados");

        jLabel19.setText("Procesos suspendidos");

        ciclosTranscuridos.setEditable(false);
        ciclosTranscuridos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciclosTranscuridosActionPerformed(evt);
            }
        });

        jLabel20.setText("Ciclos transcurridos");

        quantunn.setEditable(false);

        jLabel21.setText("Quantunn");

        cantidadProcesos.setEditable(false);

        jLabel22.setText("Cantidad de procesos");

        barraProgresoSOB.setBackground(new java.awt.Color(0, 0, 102));
        barraProgresoSOB.setString("SO");
        barraProgresoSOB.setStringPainted(true);

        etiquetaProcentajeUsoCPUs.setText("0%");

        jLabel24.setText("Procesoso listos");

        etiquetaProcentajeProcesosSuspendidos.setText("0%");

        etiquetaPorcentajeProcesosUSER.setText("0%");

        etiquetaPorcentajeProcesosSO.setText("0%");

        etiquetaPorcentajeProcesosListos.setText("0%");

        etiquetaPorcentajeProcesosBloqueados.setText("0%");

        idDelProcesosAInteractuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idDelProcesosAInteractuarActionPerformed(evt);
            }
        });
        idDelProcesosAInteractuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idDelProcesosAInteractuarKeyReleased(evt);
            }
        });

        colaNuevosB.setModel(new javax.swing.table.DefaultTableModel(
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
        colaNuevosB.setFocusable(false);
        colaNuevosB.getTableHeader().setReorderingAllowed(false);
        colaNuevosB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colaNuevosBMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(colaNuevosB);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(etiquetaPorcentajeProcesosListos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barraBloqueadosYEjecucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17)
                                .addGap(6, 6, 6)
                                .addComponent(etiquetaPorcentajeProcesosBloqueados))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(barraSuspendidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(etiquetaProcentajeProcesosSuspendidos))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(barraUsoDeCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(etiquetaProcentajeUsoCPUs))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(etiquetaPorcentajeProcesosSO)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(barraSOYUSER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(etiquetaPorcentajeProcesosUSER)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ciclosTranscuridos, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel21)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(quantunn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel22)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cantidadProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(jLabel23))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(botonDesuspenderProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(botonSuspenderProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(botonEliminarProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(botonCambiarPrioridad)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(spinnerPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(idDelProcesosAInteractuar, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                                    .addComponent(botonPausar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(barraProgresoNuevosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(barraProgresoESA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(barraProgresoCPUA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(colaCPU1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(barraProgresoSOA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(barraProgresoNuevosB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(colaCPU2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(barraProgresoSOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(barraProgresoESB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(barraProgresoCPUB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(99, 99, 99)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(poximoProcesoID, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(poximoProcesoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel14))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(poximoProcesoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel13))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(poximoProcesoPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel12))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(poximoProcesoDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(poximoProcesoTiempoRestante, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(18, 18, 18)
                                        .addComponent(poximoProcesoCiclosParaES, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(poximoProcesoParaProximaES))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(poximoProcesoDuracionES, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel23))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(etiquetaProcentajeProcesosSuspendidos)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel7)
                                                            .addComponent(barraUsoDeCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(8, 8, 8))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(etiquetaProcentajeUsoCPUs)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(barraSuspendidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))))
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel15)
                                                .addComponent(etiquetaPorcentajeProcesosSO))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel16)
                                                .addComponent(etiquetaPorcentajeProcesosUSER))
                                            .addComponent(barraSOYUSER, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(ciclosTranscuridos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(quantunn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cantidadProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel24)
                                            .addComponent(etiquetaPorcentajeProcesosListos))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17)
                                            .addComponent(etiquetaPorcentajeProcesosBloqueados)))
                                    .addComponent(barraBloqueadosYEjecucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel2))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(poximoProcesoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoTiempoRestante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoCiclosParaES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoParaProximaES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(poximoProcesoDuracionES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(colaCPU1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(barraProgresoSOA, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barraProgresoNuevosA, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barraProgresoCPUA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barraProgresoESA, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(colaCPU2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barraProgresoSOB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barraProgresoNuevosB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barraProgresoCPUB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barraProgresoESB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(botonPausar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonSuspenderProceso)
                                    .addComponent(botonEliminarProceso)
                                    .addComponent(idDelProcesosAInteractuar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botonDesuspenderProceso)
                                    .addComponent(jButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonCambiarPrioridad)
                                    .addComponent(spinnerPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colaCPU1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaCPU1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaCPU1ActionPerformed

    private void colaCPU2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaCPU2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaCPU2ActionPerformed

    private void botonSuspenderProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSuspenderProcesoActionPerformed
        // TODO add your handling code here:
        boolean seBloqueo = true;
        if(!idDelProcesosAInteractuar.getText().equals("")){
            seBloqueo = instancePlanificador.suspenderProceso(Integer.valueOf(idDelProcesosAInteractuar.getText()));
            if(!seBloqueo){
                JOptionPane.showMessageDialog(null, "La ID ingresada no concuerda con ningun proceso habilitado");
            }
        }else if(colaSOA.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaSOA.getValueAt(colaSOA.getSelectedRow(), 0).toString()));
        }else if(colaSOB.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaSOB.getValueAt(colaSOB.getSelectedRow(), 0).toString()));
        }else if(colaNuevosA.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaNuevosA.getValueAt(colaNuevosA.getSelectedRow(), 0).toString()));
        }else if(colaNuevosB.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaNuevosB.getValueAt(colaNuevosB.getSelectedRow(), 0).toString()));
        }else if(colaCPUA.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaCPUA.getValueAt(colaCPUA.getSelectedRow(), 0).toString()));
        }else if(colaCPUB.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaCPUB.getValueAt(colaCPUB.getSelectedRow(), 0).toString()));
        }else if(colaESB.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaESB.getValueAt(colaESB.getSelectedRow(), 0).toString()));
        }else if(colaESA.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(colaESA.getValueAt(colaESA.getSelectedRow(), 0).toString()));
        }else if(tablaProcesosBloqueadosInicial.getSelectedRow() != -1){
            instancePlanificador.suspenderProceso(Integer.parseInt(tablaProcesosBloqueadosInicial.getValueAt(tablaProcesosBloqueadosInicial.getSelectedRow(), 0).toString()));
        }
        if(seBloqueo){
            actualiazarDatosCriticos();
        }
        idDelProcesosAInteractuar.setText("");
    }//GEN-LAST:event_botonSuspenderProcesoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void botonPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPausarActionPerformed
        // TODO add your handling code here:
        if(interrupcion.isRunning()){
            interrupcion.stop();
        }else{
            interrupcion.start();
        }
    }//GEN-LAST:event_botonPausarActionPerformed

    private void ciclosTranscuridosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ciclosTranscuridosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciclosTranscuridosActionPerformed

    private void poximoProcesoParaProximaESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poximoProcesoParaProximaESActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_poximoProcesoParaProximaESActionPerformed

    private void colaSOAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaSOAMouseClicked

        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaSOA);
    }//GEN-LAST:event_colaSOAMouseClicked

    private void idDelProcesosAInteractuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idDelProcesosAInteractuarActionPerformed

    }//GEN-LAST:event_idDelProcesosAInteractuarActionPerformed

    private void idDelProcesosAInteractuarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idDelProcesosAInteractuarKeyReleased
        // TODO add your handling code here:
        if(idDelProcesosAInteractuar.getText().equals("")){
            desabilitarBotones();
        }else{
            habilitarBotones();
            botonDesuspenderProceso.setEnabled(true);
        }
    }//GEN-LAST:event_idDelProcesosAInteractuarKeyReleased

    private void colaNuevosAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaNuevosAMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaNuevosA);
    }//GEN-LAST:event_colaNuevosAMouseClicked

    private void colaCPUAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaCPUAMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaCPUA);
    }//GEN-LAST:event_colaCPUAMouseClicked

    private void colaESAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaESAMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaESA);
    }//GEN-LAST:event_colaESAMouseClicked

    private void colaSOBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaSOBMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaSOB);
    }//GEN-LAST:event_colaSOBMouseClicked

    private void colaCPUBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaCPUBMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaCPUB);
    }//GEN-LAST:event_colaCPUBMouseClicked

    private void colaESBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaESBMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaESB);
    }//GEN-LAST:event_colaESBMouseClicked

    private void tablaProcesosBloqueadosInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProcesosBloqueadosInicialMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(tablaProcesosBloqueadosInicial);
    }//GEN-LAST:event_tablaProcesosBloqueadosInicialMouseClicked

    private void tablaProcesosSuspendidosInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProcesosSuspendidosInicialMouseClicked
        // TODO add your handling code here:
        botonDesuspenderProceso.setEnabled(true);
        botonEliminarProceso.setEnabled(true);
        botonCambiarPrioridad.setEnabled(true);
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(tablaProcesosSuspendidosInicial);
    }//GEN-LAST:event_tablaProcesosSuspendidosInicialMouseClicked

    private void colaNuevosBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colaNuevosBMouseClicked
        // TODO add your handling code here:
        habilitarBotones();
        idDelProcesosAInteractuar.setText("");
        limpiarSeleccionTablas(colaNuevosB);
    }//GEN-LAST:event_colaNuevosBMouseClicked

    private void botonDesuspenderProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDesuspenderProcesoActionPerformed
        // TODO add your handling code here:
        boolean seDesbloqueo = true;
        if(!idDelProcesosAInteractuar.getText().equals("")){
            seDesbloqueo = instancePlanificador.reanudarProceso(Integer.valueOf(idDelProcesosAInteractuar.getText()));
            if(!seDesbloqueo){
                JOptionPane.showMessageDialog(null, "La ID ingresada no concuerda con ningun proceso habilitado.");
            }
        }else{
            instancePlanificador.reanudarProceso(Integer.parseInt(tablaProcesosSuspendidosInicial.getValueAt(tablaProcesosSuspendidosInicial.getSelectedRow(), 0).toString()));
        }
        if(seDesbloqueo){
            actualiazarDatosCriticos();
        }
        idDelProcesosAInteractuar.setText("");
    }//GEN-LAST:event_botonDesuspenderProcesoActionPerformed

    private void botonCambiarPrioridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCambiarPrioridadActionPerformed
        // TODO add your handling code here:
        boolean seCambio = true;
        int id;
        if(!idDelProcesosAInteractuar.getText().equals("")){
            seCambio = instancePlanificador.modificarPrioridad(Integer.parseInt(idDelProcesosAInteractuar.getText()), Integer.parseInt(spinnerPrioridad.getValue().toString()));
            if(!seCambio){
                JOptionPane.showMessageDialog(null, "La ID ingresada no concuerda con ningun proceso habilitado");
            }
        }else if(colaSOA.getSelectedRow() != -1){
            id = Integer.parseInt(colaSOA.getValueAt(colaSOA.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaSOB.getSelectedRow() != -1){
            id = Integer.parseInt(colaSOB.getValueAt(colaSOB.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaNuevosA.getSelectedRow() != -1){
            id = Integer.parseInt(colaNuevosA.getValueAt(colaNuevosA.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaNuevosB.getSelectedRow() != -1){
            id = Integer.parseInt(colaNuevosB.getValueAt(colaNuevosB.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaCPUA.getSelectedRow() != -1){
            id = Integer.parseInt(colaCPUA.getValueAt(colaCPUB.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaCPUB.getSelectedRow() != -1){
            id = Integer.parseInt(colaCPUB.getValueAt(colaCPUA.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaESB.getSelectedRow() != -1){
            id = Integer.parseInt(colaESB.getValueAt(colaESB.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(colaESA.getSelectedRow() != -1){
            id = Integer.parseInt(colaESA.getValueAt(colaESA.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }else if(tablaProcesosBloqueadosInicial.getSelectedRow() != -1){
            id = Integer.parseInt(tablaProcesosBloqueadosInicial.getValueAt(tablaProcesosBloqueadosInicial.getSelectedRow(), 0).toString());
            instancePlanificador.modificarPrioridad(id, Integer.parseInt(spinnerPrioridad.getValue().toString()));
        }   
        if(seCambio){
            actualiazarDatosCriticos();
        }
        idDelProcesosAInteractuar.setText("");
    }//GEN-LAST:event_botonCambiarPrioridadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Creador_CPUs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Creador_CPUs().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraBloqueadosYEjecucion;
    private javax.swing.JProgressBar barraProgresoCPUA;
    private javax.swing.JProgressBar barraProgresoCPUB;
    private javax.swing.JProgressBar barraProgresoESA;
    private javax.swing.JProgressBar barraProgresoESB;
    private javax.swing.JProgressBar barraProgresoNuevosA;
    private javax.swing.JProgressBar barraProgresoNuevosB;
    private javax.swing.JProgressBar barraProgresoSOA;
    private javax.swing.JProgressBar barraProgresoSOB;
    private javax.swing.JProgressBar barraSOYUSER;
    private javax.swing.JProgressBar barraSuspendidos;
    private javax.swing.JProgressBar barraUsoDeCPU;
    private javax.swing.JButton botonCambiarPrioridad;
    private javax.swing.JButton botonDesuspenderProceso;
    private javax.swing.JButton botonEliminarProceso;
    private javax.swing.JButton botonPausar;
    private javax.swing.JButton botonSuspenderProceso;
    private javax.swing.JTextField cantidadProcesos;
    private javax.swing.JTextField ciclosTranscuridos;
    private javax.swing.JTextField colaCPU1;
    private javax.swing.JTextField colaCPU2;
    private javax.swing.JTable colaCPUA;
    private javax.swing.JTable colaCPUB;
    private javax.swing.JTable colaESA;
    private javax.swing.JTable colaESB;
    private javax.swing.JTable colaNuevosA;
    private javax.swing.JTable colaNuevosB;
    private javax.swing.JTable colaSOA;
    private javax.swing.JTable colaSOB;
    private javax.swing.JLabel etiquetaPorcentajeProcesosBloqueados;
    private javax.swing.JLabel etiquetaPorcentajeProcesosListos;
    private javax.swing.JLabel etiquetaPorcentajeProcesosSO;
    private javax.swing.JLabel etiquetaPorcentajeProcesosUSER;
    private javax.swing.JLabel etiquetaProcentajeProcesosSuspendidos;
    private javax.swing.JLabel etiquetaProcentajeUsoCPUs;
    private javax.swing.JTextField idDelProcesosAInteractuar;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField poximoProcesoCiclosParaES;
    private javax.swing.JTextField poximoProcesoDuracion;
    private javax.swing.JTextField poximoProcesoDuracionES;
    private javax.swing.JTextField poximoProcesoID;
    private javax.swing.JTextField poximoProcesoNombre;
    private javax.swing.JTextField poximoProcesoParaProximaES;
    private javax.swing.JTextField poximoProcesoPrioridad;
    private javax.swing.JTextField poximoProcesoTiempoRestante;
    private javax.swing.JTextField poximoProcesoTipo;
    private javax.swing.JTextField quantunn;
    private javax.swing.JSpinner spinnerPrioridad;
    private javax.swing.JTable tablaEjecucion;
    private javax.swing.JTable tablaProcesosBloqueadosInicial;
    private javax.swing.JTable tablaProcesosSuspendidosInicial;
    // End of variables declaration//GEN-END:variables
}
