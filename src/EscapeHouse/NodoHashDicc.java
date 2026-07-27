public class NodoHashDicc {
    private Object clave;
    private Object dato;
    private NodoHashDicc enlace;

    public NodoHashDicc(Object clave, Object dato, NodoHashDicc enlace) {
        this.clave = clave;
        this.dato = dato;
        this.enlace = enlace;
    }

    public Object getClave() {
        return clave;
    }

    public void setClave(Object clave) {
        this.clave = clave;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public NodoHashDicc getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoHashDicc enlace) {
        this.enlace = enlace;
    }

}
