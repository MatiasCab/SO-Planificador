/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Planificador;
import Procesos.*;
import java.util.*;
import ListaProcesos.*;

/**
 *
 * @author matia
 */
public class Planificador { //Esta clase tiene que ser singleton
    
    
    private static final Planificador instance = null;
    
    private ArrayList<CPU> CPUs;
    private final ListaProcesos[] colaProcesos1 = new ListaProcesos[4];
    private final ListaProcesos[] colaProcesos2 = new ListaProcesos[4];
    private final HashMap<Proceso, String> suspendidos = new HashMap<>();
    private ListaProcesos bloqueadosPorES = new ListaProcesos();
    
    public boolean cambioContextoColas = false;
    
    public int cantidadProcesos = 0;
    
    public int tiempoDeEjecucion = 0;
   
    private ListaProcesos[] colaDeEjecucion;
    
    private ListaProcesos[] colaDeExpirados;
    
    public static Planificador create(int tiempoDeEjecucion, int cantCPUs){
        if(instance == null){
            return new Planificador(tiempoDeEjecucion, cantCPUs);
        }
        return instance;
    }

    private Planificador(int tiempoDeEjecucion, int cantCPUs){
        
        colaProcesos1[0] = new ListaProcesos();
        colaProcesos1[1] = new ListaProcesos();
        colaProcesos1[2] = new ListaProcesos();
        colaProcesos1[3] = new ListaProcesos();
        
        colaDeEjecucion = colaProcesos1;
                
        colaProcesos2[0] = new ListaProcesos();
        colaProcesos2[2] = new ListaProcesos();
        colaProcesos2[3] = new ListaProcesos();
        
        colaDeExpirados = colaProcesos2;
        
        this.tiempoDeEjecucion = tiempoDeEjecucion;
        
        crearCPUs(cantCPUs);
    }
    
    public boolean modificarPrioridad(int idProceso, int prioridad){
        Proceso proceso = this.colaDeEjecucion[0].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeEjecucion[0].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.colaDeEjecucion[1].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeEjecucion[1].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.colaDeEjecucion[2].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeEjecucion[2].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.colaDeEjecucion[3].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeEjecucion[3].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.colaDeExpirados[0].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeExpirados[0].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.colaDeExpirados[2].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeExpirados[2].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.colaDeExpirados[3].eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.colaDeExpirados[3].insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        proceso = this.bloqueadosPorES.eliminar(idProceso);
        if(proceso != null){
            proceso.cambiarPrioridad(prioridad);
            this.bloqueadosPorES.insertar(proceso.getPrioridad(), proceso);
            return true;
        }
        return false;
        
    }
    
    public void modificarTiempoCPU(int nuevoTiempo){
        this.tiempoDeEjecucion = nuevoTiempo;
    }
    
    public boolean suspenderProceso(int idProceso){
        Proceso proceso = this.colaDeEjecucion[0].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EJE0");
            return true;
        }
        proceso = this.colaDeEjecucion[1].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EJE1");
            return true;
        }
        proceso = this.colaDeEjecucion[2].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EJE2");
            return true;
        }
        proceso = this.colaDeEjecucion[3].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EJE3");
            return true;
        }
        proceso = this.colaDeExpirados[0].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EXP0");
            return true;
        }
        proceso = this.colaDeExpirados[2].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EXP2");
            return true;
        }
        proceso = this.colaDeExpirados[3].eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "EXP3");
            return true;
        }
        proceso = this.bloqueadosPorES.eliminar(idProceso);
        if(proceso != null){
            this.suspendidos.put(proceso, "BLO");
            return true;
        }
        return false;
    }
    
    public boolean reanudarProceso(int idProceso){
        for(Proceso p : this.suspendidos.keySet()){
            if (p.getID().equals(idProceso)){
                String cola = this.suspendidos.get(p);
                if(cola.contains("EJE")){
                    switch (cola.charAt(3)) {
                        case '0' -> this.colaDeEjecucion[0].insertar(p.getPrioridad(), p);
                        case '1' -> this.colaDeEjecucion[1].insertar(p.getPrioridad(), p);
                        case '2' -> this.colaDeEjecucion[2].insertar(p.getPrioridad(), p);
                        default -> this.colaDeEjecucion[3].insertar(p.getPrioridad(), p);
                    }
                }else if(cola.contains("EXP")){
                    switch (cola.charAt(3)) {
                        case '0' -> this.colaDeExpirados[0].insertar(p.getPrioridad(), p);
                        case '2' -> this.colaDeExpirados[2].insertar(p.getPrioridad(), p);
                        default -> this.colaDeEjecucion[3].insertar(p.getPrioridad(), p);
                    }
                }else{
                    this.bloqueadosPorES.insertar(p.getValoresEjecucionProceso()[2], p);
                }
                this.suspendidos.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public void ingresarProcesos(LinkedList<Proceso> procesosNuevos){
        for (Proceso p : procesosNuevos){
            if(p.isOfSO()){
                colaDeEjecucion[0].insertar(p.getPrioridad(), p);
            }else{
                colaDeEjecucion[1].insertar(p.getPrioridad(), p);
            }
        }
    }
    
    private void crearCPUs(int cantidad){
        this.CPUs = new ArrayList(cantidad);
        for (int i = 0; i < cantidad; i++){
            this.CPUs.add(new CPU(this.tiempoDeEjecucion));
        }
    }
    
    public int cantidadCPUs(){
        return this.CPUs.size();
    }
    
    public int cantCPUsEnUso(){
        int contador = 0;
        for (CPU c : this.CPUs){
            if(c.getProcesoEnejecucion() != null){
                contador++;
            }
        }
        return contador;
    }
    
    public void ejecutarCiclo(){
        this.cambioContextoColas = false;
        for (CPU c : this.CPUs){
            if(!c.ejecutarCiclo(this.tiempoDeEjecucion, this.colaDeExpirados, this.bloqueadosPorES)){
                c.asignarProceso(seleccionarProceso());
            }    
        }
        actualizarBloqueados();
        this.cantidadProcesos = obtenerCantidadProcesos();
    }
    
    private int obtenerCantidadProcesos(){
        int cantidadProcesosTotales = 0;
        
        cantidadProcesosTotales += getProcesosListos();       
        cantidadProcesosTotales += getCantProceosBloqueados();
        cantidadProcesosTotales += getProcesosSuspendidos();
        
        for (CPU c : this.CPUs){
            if(c.getProcesoEnejecucion() != null){
                cantidadProcesosTotales++;
            }
        }
        return cantidadProcesosTotales;
    }
    
    public Proceso seleccionarProceso(){
        for (int i = 0; i < 2; i++){
            if (!this.colaDeEjecucion[0].esVacia()){
                return this.colaDeEjecucion[0].eliminarUltimo();
            }else if(!this.colaDeEjecucion[1].esVacia()){
                return this.colaDeEjecucion[1].eliminarUltimo();
            }else if(!this.colaDeEjecucion[2].esVacia()){
                return this.colaDeEjecucion[2].eliminarUltimo();
            }else if(!this.colaDeEjecucion[3].esVacia()){
                return this.colaDeEjecucion[3].eliminarUltimo();
            }else{
                this.cambioContextoColas = !this.cambioContextoColas;
                if(this.colaProcesos2[1] == null){
                    this.colaProcesos2[1] = this.colaProcesos1[1];
                    this.colaDeEjecucion = this.colaProcesos2;
                    this.colaDeExpirados = this.colaProcesos1;
                    this.colaProcesos1[1] = null;
                }else{
                    this.colaProcesos1[1] = this.colaProcesos2[1];
                    this.colaDeEjecucion = this.colaProcesos1;
                    this.colaDeExpirados = this.colaProcesos2;
                    this.colaProcesos2[1] = null;
                }
            }
        }
        return null;
    }
    
    public Proceso seleccionarProximoProcesoAEjecutar(){
        if (!this.colaDeEjecucion[0].esVacia()){
            return this.colaDeEjecucion[0].getUltimo();
        }else if(!this.colaDeEjecucion[1].esVacia()){
            return this.colaDeEjecucion[1].getUltimo();
        }else if(!this.colaDeEjecucion[2].esVacia()){
            return this.colaDeEjecucion[2].getUltimo();
        }else if(!this.colaDeEjecucion[3].esVacia()){
            return this.colaDeEjecucion[3].getUltimo();
        }else if(!this.colaDeExpirados[0].esVacia()){
            return this.colaDeExpirados[0].getUltimo();
        }else if(!this.colaDeExpirados[2].esVacia()){
            return this.colaDeExpirados[2].getUltimo();
        }else{
            return this.colaDeExpirados[3].getUltimo();
        }
    }
    
    private void actualizarBloqueados(){
        ArrayList<Proceso> listaBloqueados = this.bloqueadosPorES.toArray();
        ListaProcesos bloqueadosReSorted = new ListaProcesos();
        for(Proceso p : listaBloqueados){
            if(p.ejecutarCicloEnBloqueo()){
                this.bloqueadosPorES.eliminar(p.getID());
                if(p.isOfSO()){
                    this.colaDeExpirados[0].insertar(p.getPrioridad(), p);
                }else{
                    this.colaDeExpirados[3].insertar(p.getPrioridad(), p);
                }
            }else{
                bloqueadosReSorted.insertar(p.getValoresEjecucionProceso()[2], p);
            }
        }
        this.bloqueadosPorES = bloqueadosReSorted;
        
    }
    
    public boolean getHuvoCambioContextoColas(){
        return this.cambioContextoColas;
    }
    
    public ArrayList<Proceso> getColaDejecucionSO(){
        return this.colaDeEjecucion[0].toArray();
    }
    
    public ArrayList<Proceso> getColaDejecucionNuevos(){
        return this.colaDeEjecucion[1].toArray();
    }
    
    public ArrayList<Proceso> getColaDejecucionLimitadosCPU(){
        return this.colaDeEjecucion[2].toArray();
    }
    
    public ArrayList<Proceso> getColaDejecucionLimitadosES(){
        return this.colaDeEjecucion[3].toArray();
    }
    
    public ArrayList<Proceso> getColaDeExpiradosSO(){
        return this.colaDeExpirados[0].toArray();
    }
    
    public ArrayList<Proceso> getColaDeExpiradosLimitadosCPU(){
        return this.colaDeExpirados[2].toArray();
    }
    
    public ArrayList<Proceso> getColaDeExpiradosLimitadosES(){
        return this.colaDeExpirados[3].toArray();
    }
    
    public ArrayList<Proceso> getBloqueadosPorES(){
        return this.bloqueadosPorES.toArray();
    }
    
    public Set<Proceso> getSuspendidos(){
        return this.suspendidos.keySet();
    }
    
    public ArrayList<Proceso> getProcesosEnEjecucion(){
        ArrayList<Proceso> listaEjecucion = new ArrayList<>(CPUs.size());
        for (CPU c : this.CPUs){
            listaEjecucion.add(c.getProcesoEnejecucion());
        }
        return listaEjecucion;
    }
    
    public int getTiempoEjecucionRestanteProceso(int indiceCPUDelProceso){
        return this.CPUs.get(indiceCPUDelProceso).getTiempoDeEjecucionActual();
    }
    
    public int getCantidadProcesos(){
        return this.cantidadProcesos;
    }
    
    public int getTiemporEjecucionCPUs(){
        return this.tiempoDeEjecucion;
    }
    
    public int getCantProcesosSO(){
        int cantProcesosSO = this.colaDeEjecucion[0].size() + this.colaDeExpirados[0].size();
        for (CPU c : this.CPUs){
            if(c.getProcesoEnejecucion() != null && c.getProcesoEnejecucion().isOfSO()){
                cantProcesosSO++;
            }
        }
        for(Proceso p : bloqueadosPorES.toArray()){
            if(p.isOfSO()){
                cantProcesosSO++;
            }
        }
        for(Proceso p : suspendidos.keySet()){
            if(p.isOfSO()){
                cantProcesosSO++;
            }
        }
        return cantProcesosSO;
    }
    
    public int getProcesosUser(){
        return (this.cantidadProcesos - getCantProcesosSO());
    }
    
    public int getProcesosListos(){
        int cantProcesos = 0;
        
        cantProcesos += this.colaDeEjecucion[0].size();
        cantProcesos += this.colaDeEjecucion[1].size();
        cantProcesos += this.colaDeEjecucion[2].size();
        cantProcesos += this.colaDeEjecucion[3].size();
        
        cantProcesos += this.colaDeExpirados[0].size();
        cantProcesos += this.colaDeExpirados[2].size();
        cantProcesos += this.colaDeExpirados[3].size();
        
        return cantProcesos;
    }
    
    public int getCantProceosBloqueados(){
        return this.bloqueadosPorES.size();
    }
    
    public int getProcesosSuspendidos(){
        return this.suspendidos.size();
    }
        
}
   
