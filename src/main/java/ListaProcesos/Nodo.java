package ListaProcesos;
import Procesos.*;

public class Nodo{

	private Comparable etiqueta;
	private Nodo siguiente = null;
        private Nodo anterior = null;
        private final Proceso dato;
        
        
	public Nodo(Comparable clave, Proceso dato) {
            this.etiqueta = clave;
            this.dato = dato;
	}
        
        public Proceso getDato() {
            return this.dato;
        }
        

	public Comparable getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(Comparable unaClave) {
		this.etiqueta = unaClave;

	}

	public void setSiguiente(Nodo nodo) {
		this.siguiente = nodo;
	}

	public void setAnterior(Nodo nodo) {
		this.anterior = nodo;
	}

	public Nodo getSiguiente() {
		return this.siguiente;
	}
	
	public Nodo getAnterior() {
		return this.anterior;
	}
	public Nodo clonar(){
            return new Nodo(this.etiqueta, this.dato);
	}
	
	public boolean equals(Nodo unNodo){
		return this.etiqueta.equals(unNodo.getEtiqueta());
	}
	
	public int compareTo(Nodo unNodo){
		return this.etiqueta.compareTo(unNodo.getEtiqueta());
	}

}

