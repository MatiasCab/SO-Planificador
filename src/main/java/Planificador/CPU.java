/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Planificador;
import Procesos.*;
import ListaProcesos.*;

/**
 *
 * @author matia
 */
public class CPU {
    
    private Proceso enEjecucion;
    
    private int tiempoEjecucionActual;
    
    public CPU(int tiempoEjecucionInicial){
        this.tiempoEjecucionActual = tiempoEjecucionInicial;
    }
    
    public Proceso getProcesoEnejecucion(){
        return this.enEjecucion;
    }
    
    public void asignarProceso(Proceso proceso){
        this.enEjecucion = proceso;
    }
    
    public void setTiempoEjecucionActual(int nuevoTiempo){
        this.tiempoEjecucionActual = nuevoTiempo;
    }
    
    public boolean ejecutarCiclo(int tiempoDeEjecucion, ListaProcesos[] colaDeExpirados, ListaProcesos bloqueados){
        
        if (this.enEjecucion != null){
            
            this.enEjecucion.ejecutarCiclo();
            this.tiempoEjecucionActual--;
            
            if (this.enEjecucion.getValoresEjecucionProceso()[0] == 0){
                this.enEjecucion = null;
                this.tiempoEjecucionActual = tiempoDeEjecucion;
                return false;
            } else if (this.enEjecucion.getValoresEjecucionProceso()[1] == 0){
                bloqueados.insertar(this.enEjecucion.getValoresEjecucionProceso()[2], this.enEjecucion);
                this.enEjecucion.setUltimoCorteCPU(false);
                this.enEjecucion = null;
                this.tiempoEjecucionActual = tiempoDeEjecucion;
                return false;
            } else if (this.tiempoEjecucionActual == 0){
                this.enEjecucion.setUltimoCorteCPU(true);
                if (this.enEjecucion.isOfSO()){
                    colaDeExpirados[0].insertar(this.enEjecucion.getPrioridad(), this.enEjecucion);
                }else if (this.enEjecucion.getUltimoCortePorCPU()) {
                    colaDeExpirados[2].insertar(this.enEjecucion.getPrioridad(), this.enEjecucion);
                }else{
                    colaDeExpirados[3].insertar(this.enEjecucion.getPrioridad(), this.enEjecucion);
                }
                this.enEjecucion = null;
                this.tiempoEjecucionActual = tiempoDeEjecucion;
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    
}
