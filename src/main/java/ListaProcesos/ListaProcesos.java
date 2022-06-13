package ListaProcesos;
import Procesos.*;
import java.util.*;

public class ListaProcesos {
    
    private Nodo primero;
    private Nodo ultimo;
    private int tamaño = 0;

    public Proceso buscar(Comparable identificadorProceso) {
       if (this.primero == null) {
           return null;
       } else {
           Nodo nodoActual = this.primero;
           while (nodoActual != null) {
               Comparable iDProceso = nodoActual.getDato().getID();
               if (iDProceso.compareTo(identificadorProceso) == 0) {
                   return nodoActual.getDato();
               } else {
                   nodoActual = nodoActual.getSiguiente();
               }
           }
       }
       return null;   
    }

    public Proceso eliminar(Comparable identificadorProceso) {
        if (this.primero == null) {
            return null;
        } else {
            Nodo nodoActual = this.primero;
            while (nodoActual != null) {
                Comparable iDProceso = nodoActual.getDato().getID();
                if (iDProceso.compareTo(identificadorProceso) == 0){
                    if(nodoActual.getAnterior() != null){
                        nodoActual.getAnterior().setSiguiente(nodoActual.getSiguiente());
                    }else{
                        this.primero = nodoActual.getSiguiente();
                    }
                    if(nodoActual.getSiguiente() != null){
                        nodoActual.getSiguiente().setAnterior(nodoActual.getAnterior());
                    }else{
                        this.ultimo = nodoActual.getAnterior();
                    }
                    nodoActual.setSiguiente(null);
                    nodoActual.setAnterior(null);
                    this.tamaño--;
                    return nodoActual.getDato();
                }
                nodoActual = nodoActual.getSiguiente();
            }
            return null;
        }
    }

    public void insertar(Comparable etiquetaOrdenancion, Proceso proceso) {
        Nodo nuevoNodo = new Nodo(etiquetaOrdenancion, proceso);
        this.tamaño++;
        if(esVacia()) {
            this.primero = nuevoNodo;
            this.ultimo = nuevoNodo;
            return;
        }
        Nodo nodoActual = primero;
        Nodo ultimoNodo = primero;
        while (nodoActual != null){
            if (nuevoNodo.getEtiqueta().compareTo(nodoActual.getEtiqueta()) <= 0){
                nuevoNodo.setAnterior(nodoActual.getAnterior());
                nuevoNodo.setSiguiente(nodoActual);
                nodoActual.setAnterior(nuevoNodo);
                if (nuevoNodo.getAnterior() != null){
                    nuevoNodo.getAnterior().setSiguiente(nuevoNodo);
                }else{
                    this.primero = nuevoNodo;
                }
                return;
            }
            ultimoNodo = nodoActual;
            nodoActual = nodoActual.getSiguiente();
        }
        nuevoNodo.setAnterior(ultimoNodo);
        nuevoNodo.setSiguiente(null);
        ultimoNodo.setSiguiente(nuevoNodo);
        this.ultimo = nuevoNodo;
    }
    
    public ArrayList<Proceso> toArray(){
        ArrayList<Proceso> nuevaLista = new ArrayList(this.tamaño);
        if (esVacia()) {
           return nuevaLista;
        } else {
            Nodo nodoActual = this.primero;
            while (nodoActual != null) {
                nuevaLista.add(nodoActual.getDato());
                nodoActual = nodoActual.getSiguiente();
            }
           return nuevaLista;
       } 
    }
    
    public ListaProcesos reSorted(){
        ArrayList<Proceso> ant = this.toArray();
        ListaProcesos result = new ListaProcesos();
        for (Proceso p : ant){
            result.insertar(p.getPrioridad(), p);
        }
        return result;
    }

    public boolean esVacia() {
        return this.primero == null;
    }

    public void setPrimero(Nodo unNodo) {
        unNodo.setSiguiente(this.primero.getSiguiente());
        this.primero = unNodo;    
    }
        
    public Nodo getPrimero() {
        return primero;
    }
    
    public Proceso eliminarUltimo(){
        if(esVacia()){
            return null;
        }
        Proceso ultimoProceso = ultimo.getDato();
        if(tamaño == 1){
            ultimo = null;
            primero = null;
            tamaño--;
            return ultimoProceso;
        }
        ultimo = ultimo.getAnterior();
        ultimo.setSiguiente(null);
        tamaño--;
        return ultimoProceso;
    }
    
    public Proceso getUltimo() {
        if(!esVacia()){
            return ultimo.getDato();
        }
        return null;
    }
    
    public int size(){
        return this.tamaño;
    }
    
    
}


