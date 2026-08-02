package estructuras.grafos;

public class NodoAdyacente {

    private NodoVertice vertice;
    private int etiqueta;
    private NodoAdyacente sigAdyacente;

    public NodoAdyacente(NodoVertice vertice, int etiqueta, NodoAdyacente sigAdyacente) {

        this.vertice = vertice;
        this.etiqueta = etiqueta;
        this.sigAdyacente = sigAdyacente;
    }

    public NodoVertice getVertice() {
        return this.vertice;
    }

    public void setVertice(NodoVertice vertice) {
        this.vertice = vertice;
    }

    public int getEtiqueta() {
        return this.etiqueta;
    }

    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
    }

    public NodoAdyacente getSigAdyacente() {
        return this.sigAdyacente;
    }

    public void setSigAdyacente(NodoAdyacente sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }

}
