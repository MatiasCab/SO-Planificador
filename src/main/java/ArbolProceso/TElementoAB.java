package ArbolProceso;

public class TElementoAB<T> implements IElementoAB<T> {

    private Comparable etiqueta;
    private TElementoAB<T> hijoIzq;
    private TElementoAB<T> hijoDer;  
    private T datos;

    /**
     * @param unaEtiqueta
     * @param unosDatos 
     */
    @SuppressWarnings("unchecked")
    public TElementoAB(Comparable unaEtiqueta, T unosDatos) {
        etiqueta = unaEtiqueta;
        datos = unosDatos;
    }

    public TElementoAB<T> getHijoIzq() {
        return hijoIzq;
    }

    public TElementoAB<T> getHijoDer() {
        return hijoDer;
    }
    
    private int obtenerBalance(){
        if(hijoDer != null && hijoIzq != null ){
            return hijoDer.obtenerAltura() - hijoIzq.obtenerAltura();
        }else if(hijoIzq != null){
            return -1 * (this.obtenerAltura());
        }else{
            return this.obtenerAltura();
        }
    }
    
    private TElementoAB<T> balancear(TElementoAB nodoActual){
        int balance = nodoActual.obtenerBalance();
        if (balance == -2){
            int balanceIzq = nodoActual.hijoIzq.obtenerBalance();
            if (balanceIzq == -1){
                return rotacionLL(nodoActual);
            }else if (balanceIzq == 1){
                return rotacionLR(nodoActual);
            }    
        }else if(balance == 2){
            int balanceDer = nodoActual.hijoDer.obtenerBalance();
            if (balanceDer == 1){
                return rotacionRR(nodoActual);
            }else if(balanceDer == -1){
                return rotacionRL(nodoActual);
            }            
        }
        return nodoActual;
    }
    
    @Override
    public TElementoAB insertar(TElementoAB<T> unElemento){
        if(unElemento.getEtiqueta().compareTo(etiqueta) == 0){
            return this;
        }else if(unElemento.getEtiqueta().compareTo(etiqueta) < 0){
            if(this.hijoIzq == null){
                hijoIzq = unElemento;
            }else{
                hijoIzq = hijoIzq.insertar(unElemento);
            }
        }else{
            if(this.hijoDer == null){
                hijoDer = unElemento;
            }else{
                hijoDer = hijoDer.insertar(unElemento);
            }
        }
        return balancear(this);
    }
    
    @Override
    public TElementoAB eliminar(Comparable unaEtiqueta) {
        if (unaEtiqueta.compareTo(this.etiqueta) < 0){
            if (hijoIzq != null) {
                hijoIzq = hijoIzq.eliminar(unaEtiqueta);
            }
        } else if (unaEtiqueta.compareTo(this.etiqueta) > 0){
            if (hijoDer != null) {
                hijoDer = hijoDer.eliminar(unaEtiqueta);
            }
        } else {
            return quitaElNodo();
        }
        int diferencia = obtenerBalance();
        if(diferencia == -2){
            int balanceIzq = hijoIzq.obtenerBalance();
            if(balanceIzq <= 0){
                return rotacionLL(this);
            }else{
                return rotacionLR(this);
            }
        }else if(diferencia == 2){
            int balanceDer = hijoDer.obtenerBalance();
            if(balanceDer >= 0){
                return rotacionRR(this);
            }else{
                return rotacionRL(this);
            }
        }
        return this;
    }

  
    
    private TElementoAB quitaElNodo() {
        if( hijoIzq == null) {
            return hijoDer;
        }
        if( hijoDer == null) {
            return hijoIzq;
        }
        TElementoAB elHijo = hijoIzq;
        TElementoAB elPadre = this;
        while (elHijo.hijoDer != null) {
            elPadre = elHijo;
            elHijo = elHijo.hijoDer;
        }
        if(elPadre != this) {
            elPadre.hijoDer = elHijo.hijoIzq;
            elHijo.hijoIzq = hijoIzq;
        }
        elHijo.hijoDer = hijoDer;
        return elHijo;
 }
    
    public TElementoAB rotacionLL(TElementoAB nodoK2){
        TElementoAB nodoK1 = nodoK2.hijoIzq;
        nodoK2.setHijoIzq(nodoK1.getHijoDer());
        nodoK1.setHijoDer(nodoK2);
        return nodoK1;
    }
    
    public TElementoAB rotacionRR(TElementoAB nodoK1){
        TElementoAB nodoK2 = nodoK1.hijoDer;
        nodoK1.setHijoDer(nodoK2.getHijoIzq());
        nodoK2.setHijoIzq(nodoK1);
        return nodoK2;  
    }
    
    public TElementoAB rotacionLR(TElementoAB nodoK3){
        nodoK3.hijoIzq = rotacionRR(nodoK3.hijoIzq);
        return rotacionLL(nodoK3);
    }
    
    public TElementoAB rotacionRL(TElementoAB nodoK1){
        nodoK1.hijoDer = rotacionLL(nodoK1.hijoDer);
        return rotacionRR(nodoK1);
    }
    
    public int obtenerAltura() {
        int der = -1;
        int izq = -1;
        if(hijoIzq != null){
            izq = hijoIzq.obtenerAltura();
        }
        if(hijoDer != null){
            der = hijoDer.obtenerAltura();
        }
        return Math.max(izq, der)+1;
    }

    /**
     * @param unaEtiqueta
     * @return
     */
    @Override
    public TElementoAB<T> buscar(Comparable unaEtiqueta) {
        if (unaEtiqueta.compareTo(this.etiqueta) == 0) {
            return this;
        } else if (unaEtiqueta.compareTo(this.etiqueta) < 0) {
            if (this.hijoIzq != null) {
                return hijoIzq.buscar(unaEtiqueta);
            } else {
                return null;
            }
        } else {
            if (this.hijoDer != null) {
                return this.hijoDer.buscar(unaEtiqueta);
            } else {
                return null;
            }
        }
    }

    /**
     * @return recorrida en inorden del subArbol que cuelga del elemento actual
     */
    @Override
    public String inOrden() {
        StringBuilder preOrden = new StringBuilder();
        if (this.hijoIzq != null) {
            preOrden.append(this.hijoIzq.inOrden());
        }
        preOrden.append(this.etiqueta + " ");
        if (this.hijoDer != null){
            preOrden.append(this.hijoDer.inOrden());
        }
        return preOrden.toString();
    }


    public Comparable getEtiqueta() {
        return etiqueta;
    }

    @Override
    public T getDatos() {
        return datos;
    }

    public void setHijoIzq(TElementoAB<T> elemento) {
        this.hijoIzq = elemento;

    }

    public void setHijoDer(TElementoAB<T> elemento) {
        this.hijoDer = elemento;
    }
}
