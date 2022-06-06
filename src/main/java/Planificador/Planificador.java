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
    public Proceso enEjecucion;
    public ListaProcesos[] colaProcesos1 = new ListaProcesos[4];
    public ListaProcesos[] colaProcesos2 = new ListaProcesos[4];
    public ListaProcesos bloqueadosPorUsuario = new ListaProcesos();
    
    public int tiempoDeEjecucion;
    
    private int tiempoDeEjecucionActual;
    
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
        this.tiempoDeEjecucionActual = tiempoDeEjecucion;
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
        for (Proceso i : procesosNuevos){
            colaDeEjecucion[0].insertar(i.getPrioridad(), i);
        }
    }
    
    public void ejecutarCiclo(){
        
        if (this.enEjecucion != null){
            
            this.enEjecucion.ejecutarCiclo();
            actualizarBloqueados();
            this.tiempoDeEjecucionActual--;
            
            if (this.enEjecucion.getValoresEjecucionProceso()[0] == 0){
                this.enEjecucion = null;
                this.tiempoDeEjecucionActual = this.tiempoDeEjecucion;
            } else if (this.enEjecucion.getValoresEjecucionProceso()[1] == 0){
                this.colaDeEjecucion[1].insertar(this.enEjecucion.getValoresEjecucionProceso()[2], this.enEjecucion);
                this.enEjecucion.setUltimoCorteCPU(false);
                this.enEjecucion = null;
                this.tiempoDeEjecucionActual = this.tiempoDeEjecucion;
            } else if (this.tiempoDeEjecucionActual == 0 && this.enEjecucion != null){
                this.enEjecucion.setUltimoCorteCPU(true);
                if (this.enEjecucion.isOfSO()){
                    this.colaDeExpirados[0].insertar(this.enEjecucion.getPrioridad(), this.enEjecucion);
                }else if (this.enEjecucion.getUltimoCortePorCPU()) {
                    this.colaDeExpirados[3].insertar(this.enEjecucion.getPrioridad(), this.enEjecucion);
                }else{
                    this.colaDeExpirados[2].insertar(this.enEjecucion.getPrioridad(), this.enEjecucion);
                }
                this.enEjecucion = null;
                this.tiempoDeEjecucionActual = this.tiempoDeEjecucion;
            }
        }
        
        for (int i = 0; i < 2; i++){
            if (this.enEjecucion == null){
                this.tiempoDeEjecucionActual = this.tiempoDeEjecucion;
                if (!this.colaDeEjecucion[0].esVacia()){
                    this.enEjecucion = this.colaDeEjecucion[0].getPrimero().getDato();
                    this.colaDeEjecucion[0].eliminar(this.enEjecucion.getID());
                    break;
                }else if(!this.colaDeEjecucion[2].esVacia()){
                    this.enEjecucion = this.colaDeEjecucion[2].getPrimero().getDato();
                    this.colaDeEjecucion[2].eliminar(this.enEjecucion.getID());
                    break;
                }else if(!this.colaDeEjecucion[3].esVacia()){
                    this.enEjecucion = this.colaDeEjecucion[3].getPrimero().getDato();
                    this.colaDeEjecucion[3].eliminar(this.enEjecucion.getID());
                    break;
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
            }else { break; }
        }
    }
    
    private void actualizarBloqueados(){
        ArrayList<Proceso> listaBloqueados = this.colaDeEjecucion[1].toArray();
        for(Proceso p : listaBloqueados){
            p.ejecutarCicloEnBloqueo();
            if(p.ejecutarCicloEnBloqueo()){
                this.colaDeEjecucion[1].eliminar(p.getID());
                if(p.isOfSO()){
                    this.colaDeExpirados[0].insertar(p.getPrioridad(), p);
                }else{
                    this.colaDeExpirados[2].insertar(p.getPrioridad(), p);
                }
            }
        }
    }
        
}
   
