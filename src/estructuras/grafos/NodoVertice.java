package estructuras.grafos;

public class NodoVertice {

    private Object elemento;
    private NodoVertice sigVertice;
    private NodoAdyacente primerAdyacente;

    public NodoVertice(Object elemento, NodoVertice sigVertice) {
        this.elemento = elemento;
        this.sigVertice = sigVertice;
        this.primerAdyacente = null;
    }

    public Object getElemento() {
        return this.elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public NodoVertice getSigVertice() {
        return this.sigVertice;
    }

    public void setSigVertice(NodoVertice sigVertice) {
        this.sigVertice = sigVertice;
    }

    public NodoAdyacente getPrimerAdyacente() {
        return this.primerAdyacente;
    }

    public void setPrimerAdyacente(NodoAdyacente primerAdyacente) {
        this.primerAdyacente = primerAdyacente;
    }

}