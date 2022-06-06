/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Procesos;

/**
 *
 * @author matia
 */
public class Proceso {
    
    private static int IDENTIFICADOR_PROCESOS = 0;
    
    private int prioridad;
    
    private final String nombre;
    
    private final boolean isOfSO;
    
    private final int iD;
    
    private int duracion;
    
    private int tiempoCorteES;
    
    private int duracionES;
    
    private boolean ultimoCortePorCPU;
    
    private int[] valoresEjecucionProceso = new int[3];
    
    public Proceso(String nombre, int prioridad, boolean isOfSO, int duracion, int tiempoCorteES, int duracionES){
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.isOfSO = isOfSO;
        this.duracion = duracion;
        this.tiempoCorteES = tiempoCorteES;
        this.duracionES = duracionES;
        this.iD = IDENTIFICADOR_PROCESOS++;   
        this.valoresEjecucionProceso[0] = duracion;
        this.valoresEjecucionProceso[1] = (tiempoCorteES == 0 ? duracion + 1 : tiempoCorteES);
        this.valoresEjecucionProceso[2] = duracionES;
    }
    
    public boolean cambiarPrioridad(int prioridad){
        if(prioridad >= 1 && prioridad <= 99){
            this.prioridad = prioridad;
            return true;
        }
        return false;
    }
    
    public void ejecutarCiclo(){
        this.valoresEjecucionProceso[0] -= 1;
        this.valoresEjecucionProceso[1] -= 1;
    }
    
    public boolean ejecutarCicloEnBloqueo(){
        this.valoresEjecucionProceso[2] -= 1;
        if(this.valoresEjecucionProceso[2] == 0){
            this.valoresEjecucionProceso[1] = this.tiempoCorteES;
            this.valoresEjecucionProceso[2] = this.duracionES;
            return true;
        }
        return false;
    }
    
    public int getID(){
        return this.iD;
    }
    
    public int getPrioridad(){
        return this.prioridad;
    }
    
    public boolean isOfSO(){
        return this.isOfSO;
    }
    
    public int[] getValoresEjecucionProceso(){
        return this.valoresEjecucionProceso;
    }
    
    public void setUltimoCorteCPU(boolean fuePorCPU){
        this.ultimoCortePorCPU = fuePorCPU;
    }
    
    public boolean getUltimoCortePorCPU(){
        return this.ultimoCortePorCPU;
    }
    
}
