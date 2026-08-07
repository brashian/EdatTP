package estructuras.conjuntistas;

public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc(Comparable clave, Object dato) {
        this.clave = clave;
        this.dato = dato;
        this.altura = 0;
        this.izquierdo = null;
        this.derecho = null;
    }

    public void recalcularAltura() {
        int altIzq = -1;
        int altDer = -1;
        if (this.izquierdo != null) {
            altIzq = this.izquierdo.getAltura();
        }
        if (this.derecho != null) {
            altDer = this.derecho.getAltura();
        }
        int max = 0;
        if (altIzq > altDer) {
            max = altIzq;
        } else {
            max = altDer;
        }
        this.altura = 1 + max;
    }

    public int getAltura() {
        return this.altura;
    }

    public Comparable getClave() {
        return this.clave;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public Object getDato() {
        return this.dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public NodoAVLDicc getIzquierdo() {
        return this.izquierdo;
    }

    public NodoAVLDicc getDerecho() {
        return this.derecho;
    }

    public void setIzquierdo(NodoAVLDicc izq) {
        this.izquierdo = izq;
    }

    public void setDerecho(NodoAVLDicc der) {
        this.derecho = der;
    }

}
