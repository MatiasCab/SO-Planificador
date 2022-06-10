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
/**
 *
 * @author matia
 */
public class VistaPlanificador extends javax.swing.JFrame {

    private int instanceas = 0;
    
    Planificador instancePlanificador;
    VistaPlanificador ventanaPlani;
    CreadorProcesos ventanaCreadora;
    Timer interrupcion;
    
    int tiempoSegs;
    
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
            ventanaPlani.actualizarTablasColas();
            ventanaPlani.obtenerProximoProceso();
            ventanaPlani.actualizarTablaBloqueados();
            ventanaPlani.actualizarTablaSuspendidos();
            ventanaPlani.actualizarVentanasDatos();
            ventanaPlani.actualizarBarrasDatos();
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
    public VistaPlanificador(int cantCPUs, int tiempoSegs, int cantCiclosEjecucionCPUs, CreadorProcesos ventanaCreadora) {
        initComponents();
        
        instanceas++;
        
        this.instancePlanificador = Planificador.create(cantCiclosEjecucionCPUs, cantCPUs);
        
        this.ventanaCreadora = ventanaCreadora;
        
        this.ventanaPlani = this;
        
        this.tiempoSegs = tiempoSegs;
        
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
            t.addColumn("Nombre");
            t.addColumn("Tipo");
            t.addColumn("Prioridad");
            t.addColumn("Duracion");
            t.addColumn("Ciclos para E/S");
            t.addColumn("Duracion E/S");
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
    }
    
    public void agregarProcesos(LinkedList<Proceso> listaProcesos){
        this.instancePlanificador.ingresarProcesos(listaProcesos);
        
        if(!this.isVisible()){
            this.setVisible(true);
            boolean primeraIteracion = false;
            if (interrupcion == null){
                primeraIteracion = true;
                interrupcion = new Timer(tiempoSegs*1000, ejecutarCiclo);
            }
            if(!interrupcion.isRunning()){
                interrupcion.start();
            }
            if(primeraIteracion){
                instancePlanificador.ejecutarCiclo();
                ventanaPlani.actualizarTablaEjecucion();
                ventanaPlani.actualizarTablasColas();
                inicializarBarrasColas("B");
            } 
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
        Object[] datos = new Object[5];
        ArrayList<Proceso> listaBloqueados = instancePlanificador.getBloqueadosPorES();
        for(Proceso p : listaBloqueados){
            datos[0] = p.getNombre();
            datos[1] = (p.isOfSO() ? "SO" : "USER");
            datos[2] = p.getPrioridad();
            datos[3] = p.getDuracionES();
            datos[4] = p.getValoresEjecucionProceso()[2];
            tablaProcesosBloqueados.addRow(datos);
        }
    }
    
    public void actualizarTablaSuspendidos(){
        int filas = tablaProcesosSuspendidos.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tablaProcesosSuspendidos.removeRow(i);
        }
        Object[] datos = new Object[5];
        ArrayList<Proceso> listaSuspendidos = instancePlanificador.getSuspendidos();
        for(int i = listaSuspendidos.size() - 1; i >= 0; i--){
            Proceso p = listaSuspendidos.get(i);
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
            barras[i].setMaximum((i == 0 | i == 1 ? cantidadEnCola + 1 : cantidadEnCola));
            barras[i].setValue((i == 0 | i == 1 ? cantidadEnCola + 1 : cantidadEnCola));
        }
    }
    
    public void actualizarTablaColaProcesos(DefaultTableModel tabla, ArrayList<Proceso> listaProcesos){
        int filas = tabla.getRowCount();
        for(int i = filas - 1; i >= 0; i--){
            tabla.removeRow(i);
        }
        Object[] datos = new Object[6];
        for (int i = listaProcesos.size() - 1; i >= 0; i--){
                Proceso p = listaProcesos.get(i);
                datos[0] = p.getNombre();
                datos[1] = (p.isOfSO() ? "SO" : "USER");
                datos[2] = p.getPrioridad();
                datos[3] = p.getDuracion();
                datos[4] = p.getTiemporCorteES();
                datos[5] = p.getDuracionES();
                tabla.addRow(datos);
        }
    }
    
    public void actualizarTituloColas(){
        if (colaCPU1.getText().equals("COLA EXPIRADOS")){
            colaCPU2.setText("COLA EXPIRADOS");
            colaCPU2.setBackground(Color.red);
            colaCPU1.setText("COLA DE EJECUCION");
            colaCPU1.setBackground(Color.green);
            colaNuevosB.setEnabled(false);
            colaNuevosA.setEnabled(true);
        }else{
            colaCPU1.setText("COLA EXPIRADOS");
            colaCPU1.setBackground(Color.red);
            colaCPU2.setText("COLA DE EJECUCION");
            colaCPU2.setBackground(Color.green);
            colaNuevosA.setEnabled(false);
            colaNuevosB.setEnabled(true);
        }
    }
    
    public void actualizarBarrasDatos(){
        barraUsoDeCPU.setMaximum(instancePlanificador.cantidadCPUs());
        barraUsoDeCPU.setValue(instancePlanificador.cantCPUsEnUso());
        
        barraSuspendidos.setMaximum(instancePlanificador.getCantidadProcesos());
        barraSuspendidos.setValue(instancePlanificador.getProcesosSuspendidos());
        
        barraSOYUSER.setMaximum(instancePlanificador.getCantidadProcesos());
        barraSOYUSER.setValue(instancePlanificador.getProcesosSO());
        
        barraBloqueadosYEjecucion.setMaximum(instancePlanificador.getCantProceosBloqueados() + instancePlanificador.getProcesosListos());
        barraBloqueadosYEjecucion.setValue(instancePlanificador.getCantProceosBloqueados());
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
            poximoProcesoID.setText("");
            poximoProcesoNombre.setText("");
            poximoProcesoTipo.setText("");
            poximoProcesoPrioridad.setText("");
            poximoProcesoDuracion.setText("");
            poximoProcesoTiempoRestante.setText("");
            poximoProcesoCiclosParaES.setText("");
            poximoProcesoParaProximaES.setText("");
            poximoProcesoDuracionES.setText("");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEjecucion = new javax.swing.JTable();
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
        colaESA = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        colaSOA = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        colaNuevosA = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        colaCPUA = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaProcesosBloqueadosInicial = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaProcesosSuspendidosInicial = new javax.swing.JTable();
        colaCPU2 = new javax.swing.JTextField();
        barraProgresoNuevosB = new javax.swing.JProgressBar();
        barraProgresoCPUB = new javax.swing.JProgressBar();
        barraProgresoESB = new javax.swing.JProgressBar();
        jScrollPane10 = new javax.swing.JScrollPane();
        colaESB = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        colaCPUB = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        colaNuevosB = new javax.swing.JTable();
        jScrollPane15 = new javax.swing.JScrollPane();
        colaSOB = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jButton4 = new javax.swing.JButton();
        jSpinner2 = new javax.swing.JSpinner();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        botonPausar = new javax.swing.JButton();
        barraUsoDeCPU = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();
        barraBloqueadosYEjecucion = new javax.swing.JProgressBar();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        barraSOYUSER = new javax.swing.JProgressBar();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        barraSuspendidos = new javax.swing.JProgressBar();
        ciclosTranscuridos = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        quantunn = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        cantidadProcesos = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        barraProgresoSOB = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
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

        barraProgresoCPUA.setBackground(new java.awt.Color(255, 0, 0));

        barraProgresoESA.setBackground(new java.awt.Color(0, 153, 153));

        barraProgresoSOA.setBackground(Color.BLACK);

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
        ));
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
        ));
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
        ));
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
        ));
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
        jScrollPane12.setViewportView(tablaProcesosSuspendidosInicial);

        colaCPU2.setEditable(false);
        colaCPU2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaCPU2ActionPerformed(evt);
            }
        });

        barraProgresoNuevosB.setBackground(new java.awt.Color(0, 0, 102));

        barraProgresoCPUB.setBackground(new java.awt.Color(255, 0, 0));

        barraProgresoESB.setBackground(new java.awt.Color(0, 153, 153));

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
        ));
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
        ));
        jScrollPane13.setViewportView(colaCPUB);

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
        ));
        jScrollPane14.setViewportView(colaNuevosB);

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
        ));
        colaSOB.setEnabled(false);
        jScrollPane15.setViewportView(colaSOB);

        jButton1.setText("Bloquear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Desbloquear");

        jButton3.setText("Cambiar Prioridad");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        jButton4.setText("Cambiar ciclos para corte por CPU");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jButton5.setText("Eliminar");

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

        jLabel18.setText("Procesos en ejecucion");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(12, 12, 12)
                                        .addComponent(botonPausar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(poximoProcesoID, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(poximoProcesoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(poximoProcesoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(poximoProcesoPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(poximoProcesoDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(poximoProcesoTiempoRestante))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(poximoProcesoCiclosParaES)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(poximoProcesoParaProximaES))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(poximoProcesoDuracionES, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barraBloqueadosYEjecucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(barraUsoDeCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(barraSuspendidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(67, 67, 67)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel20)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(jLabel21))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(barraSOYUSER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel22)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(quantunn)
                                    .addComponent(ciclosTranscuridos, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(cantidadProcesos)))))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(barraProgresoNuevosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barraProgresoESA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barraProgresoCPUA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(colaCPU1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(barraProgresoSOA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(barraProgresoESB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(barraProgresoCPUB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(barraProgresoNuevosB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(colaCPU2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(barraProgresoSOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(565, 565, 565))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)
                                    .addComponent(barraSOYUSER, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(ciclosTranscuridos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(quantunn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cantidadProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22))
                                .addGap(11, 11, 11)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(barraBloqueadosYEjecucion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(barraUsoDeCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(barraSuspendidos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel12))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel13))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel10))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                    .addComponent(jLabel8))))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barraProgresoNuevosB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barraProgresoSOB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barraProgresoCPUB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barraProgresoESB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton3)
                                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(botonPausar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(448, 448, 448))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colaCPU1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaCPU1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaCPU1ActionPerformed

    private void colaCPU2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaCPU2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaCPU2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info);
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Creador_CPUs().setVisible(true);
            }
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
    private javax.swing.JButton botonPausar;
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
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
    private javax.swing.JTable tablaEjecucion;
    private javax.swing.JTable tablaProcesosBloqueadosInicial;
    private javax.swing.JTable tablaProcesosSuspendidosInicial;
    // End of variables declaration//GEN-END:variables
}
