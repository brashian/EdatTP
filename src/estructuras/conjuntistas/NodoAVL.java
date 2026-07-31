package estructuras.conjuntistas;

public class NodoAVL {
    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL(Comparable elem) {
        this.elem = elem;
        this.altura = 0;
        this.izquierdo = null;
        this.derecho = null;
    }

    public void recalcularAltura(){
        int altIzq=-1;
        int altDer=-1;
        if (this.izquierdo!=null){
            altIzq=this.izquierdo.getAltura();
        }
        if (this.derecho!=null){
            altDer=this.derecho.getAltura();
        }
        int max=0;
        if (altIzq>altDer){
            max=altIzq;
        } else{
            max=altDer;
        }
        this.altura=1+max;
    }

    public int getAltura(){
        return this.altura;
    }

    public Comparable getElem() {
        return this.elem;
    }

    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public NodoAVL getDerecho() {
        return this.derecho;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izquierdo = izq;
    }

    public void setDerecho(NodoAVL der) {
        this.derecho = der;
    }

}
