package estructuras.conjuntistas;
import estructuras.lineales.Lista;


public class NodoHashMapeoM {
    private Object dominio;
    private Lista rango;
    private NodoHashMapeoM enlace;

    public NodoHashMapeoM(Object dominio, NodoHashMapeoM enlace) {
        this.dominio = dominio;
        this.rango = new Lista();
        this.enlace = enlace;
    }

    public Object getDominio() {
        return dominio;
    }

    public void setDominio(Object dominio) {
        this.dominio = dominio;
    }

    public Lista getRango() {
        return rango;
    }

    public void setRango(Lista rango) {
        this.rango = rango;
    }

    public NodoHashMapeoM getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoHashMapeoM enlace) {
        this.enlace = enlace;
    }

}
