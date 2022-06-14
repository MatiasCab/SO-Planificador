/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Procesos;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author matia
 */
public class Proceso {
    
    private static int IDENTIFICADOR_PROCESOS = 0;
    
    private int prioridad;
    
    private final String nombre;
    
    private final boolean isOfSO;
    
    private final boolean isApropiativo;
    
    private final Comparable iD;
    
    private int duracion;
    
    private int tiempoCorteES;
    
    private int duracionES;
    
    private LinkedList<Boolean> datosCortes = new LinkedList<>();
    
    private int[] valoresEjecucionProceso = new int[3];
    
    public Proceso(String nombre, int prioridad, boolean isOfSO, boolean isApropiativo, int duracion, int tiempoCorteES, int duracionES){
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.isOfSO = isOfSO;
        this.isApropiativo = isApropiativo;
        this.duracion = duracion;
        this.tiempoCorteES = tiempoCorteES;
        this.duracionES = duracionES;
        this.iD = IDENTIFICADOR_PROCESOS++;   
        this.valoresEjecucionProceso[0] = duracion;
        this.valoresEjecucionProceso[1] = (tiempoCorteES == 0 ? -1 : tiempoCorteES);
        this.valoresEjecucionProceso[2] = duracionES;
    }
    
    public void cambiarPrioridad(int prioridad){
        this.prioridad = prioridad;
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
    
    public Comparable getID(){
        return this.iD;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int getDuracion(){
        return this.duracion;
    }
    
    public int getPrioridad(){
        return this.prioridad;
    }
    
    public boolean isOfSO(){
        return this.isOfSO;
    }
    
    public boolean getIsApropiativo(){
        return this.isApropiativo;
    }
    
    public int getTiemporCorteES(){
        return this.tiempoCorteES;
    }
    
    public int getDuracionES(){
        return this.duracionES;
    }
    
    public int[] getValoresEjecucionProceso(){
        return this.valoresEjecucionProceso;
    }
    
    public void addEstadisticasData(boolean fuePorCPU){
        if (this.datosCortes.size() == 10){
            this.datosCortes.removeFirst();
            this.datosCortes.addLast(fuePorCPU);
        }else{
            this.datosCortes.addLast(fuePorCPU);
        }
    }
    
    public boolean isLimitedForCPU(){
        int balanceCPUYES = 0;
        for(boolean bool : this.datosCortes){
            if(bool){
                balanceCPUYES++;
            }else{
                balanceCPUYES--;
            }
        }
        if (balanceCPUYES == 0){
            return this.datosCortes.getFirst();
        }
        return balanceCPUYES > 0;
    }
    
}
