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
    
    public HashMap<Integer, Proceso> procesos = new HashMap<>();
    public ArrayList<CPU> CPUs = new ArrayList();
    public ListaProcesos[] colaProcesos1 = new ListaProcesos[4];
    public ListaProcesos[] colaProcesos2 = new ListaProcesos[4];
    public ListaProcesos bloqueadosPorUsuario = new ListaProcesos();
    public ListaProcesos bloqueadosPorES = new ListaProcesos();
    
    
    public int tiempoDeEjecucion = 0;
    
    
    private ListaProcesos[] colaDeEjecucion;
    
    private ListaProcesos[] colaDeExpirados;
    
    
    private Planificador(int tiempoDeEjecucion){
        
        colaProcesos1[0] = new ListaProcesos();
        colaProcesos1[1] = new ListaProcesos();
        colaProcesos1[2] = new ListaProcesos();
        colaProcesos1[3] = new ListaProcesos();
        
        colaDeEjecucion = colaProcesos1;
        colaDeExpirados = colaProcesos2;
        
        colaProcesos2[0] = new ListaProcesos();
        colaProcesos2[2] = new ListaProcesos();
        colaProcesos2[3] = new ListaProcesos();
        
        this.tiempoDeEjecucion = tiempoDeEjecucion;
    }
    
    public boolean modificarPrioridad(int idProceso, int prioridad){
        Proceso pro = procesos.get(idProceso);
        if(pro != null){
            return pro.cambiarPrioridad(prioridad);
        }
        return false;
    }
    
    public void modificarTiempoCPU(int nuevoTiempo){
        this.tiempoDeEjecucion = nuevoTiempo;
    }
    
    public boolean bloquearProceso(int idProceso){
        Proceso proceso = this.colaDeEjecucion[0].eliminar(idProceso);
        if(proceso == null){
            proceso = this.colaDeEjecucion[1].eliminar(idProceso);
        }if(proceso == null){
            proceso = this.colaDeEjecucion[2].eliminar(idProceso);
        }if(proceso == null){
            proceso = this.colaDeEjecucion[3].eliminar(idProceso);
        }if(proceso == null){
            proceso = this.colaDeExpirados[0].eliminar(idProceso);
        }if(proceso == null){
            proceso = this.colaDeExpirados[2].eliminar(idProceso);
        }if(proceso == null){
            proceso = this.colaDeExpirados[3].eliminar(idProceso);
        }if(proceso == null){
            return false;
        }
        this.bloqueadosPorUsuario.insertar(proceso.getPrioridad(), proceso);
        return true;
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
    
    public void crearCPUs(int cantidad){
        for (int i = 0; i < cantidad; i++){
            this.CPUs.add(new CPU(this.tiempoDeEjecucion));
        }
    }
    
    public void ejecutarCiclo(){
        for (CPU c : this.CPUs){
            if(!c.ejecutarCiclo(this.tiempoDeEjecucion, this.colaDeExpirados, this.bloqueadosPorES)){
                c.asignarProceso(seleccionarProceso());
            }    
        }
        actualizarBloqueados();
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
    
    private void actualizarBloqueados(){
        ArrayList<Proceso> listaBloqueados = this.colaDeEjecucion[1].toArray();
        for(Proceso p : listaBloqueados){
            if(p.ejecutarCicloEnBloqueo()){
                this.bloqueadosPorES.eliminar(p.getID());
                if(p.isOfSO()){
                    this.colaDeExpirados[0].insertar(p.getPrioridad(), p);
                }else{
                    this.colaDeExpirados[3].insertar(p.getPrioridad(), p);
                }
            }
        }
    }
        
}
   
