package estructuras.conjuntistas;
import estructuras.lineales.Lista;

public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem);
        } else {
            boolean[] exitoArr = { true };
            this.raiz = insertarAux(this.raiz, elem, exitoArr);
            exito = exitoArr[0];
        }
        return exito;
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable elem, boolean[] exito) {
        if (n == null) {
            n = new NodoAVL(elem);
        } else {
            int cmp = elem.compareTo(n.getElem());
            if (cmp == 0) {
                exito[0] = false;
            } else if (cmp < 0) {
                n.setIzquierdo(insertarAux(n.getIzquierdo(), elem, exito));
            } else {
                n.setDerecho(insertarAux(n.getDerecho(), elem, exito));
            }
            if (exito[0]) {
                n.recalcularAltura();
                n = balancear(n);
            }
        }
        return n;
    }

    public boolean pertenece(Comparable elem) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = perteneceAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean perteneceAux(NodoAVL n, Comparable elem) {
        boolean exito = false;
        int cmp = elem.compareTo(n.getElem());
        if (cmp == 0) {
            exito = true;
        } else if (cmp < 0) {
            if (n.getIzquierdo() != null) {
                exito = perteneceAux(n.getIzquierdo(), elem);
            }
        } else {
            if (n.getDerecho() != null) {
                exito = perteneceAux(n.getDerecho(), elem);
            }
        }
        return exito;
    }

    // Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance
    // -2) y su *hijo derecho* está caído hacia el mismo lado (balance -1) o es
    // neutro (balance 0).
    private NodoAVL rotarIzquierda(NodoAVL r) {
        NodoAVL h = r.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura(); // r bajó, se recalcula primero
        h.recalcularAltura(); // h subió, depende de r ya actualizado
        return h;
    }

    // Esta rotación se aplica cuando el nodo padre está caído a la izquierda
    // (balance 2) y su *hijo izquierdo* está caído hacia el mismo lado (balance 1)
    // o es neutro (balance 0).
    private NodoAVL rotarDerecha(NodoAVL r) {
        NodoAVL h = r.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    // Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance
    // -2) y su hijo derecho está caído hacia el lado contrario (balance 1).

    private NodoAVL rotarDerechaIzquierda(NodoAVL r) {
        r.setDerecho(rotarDerecha(r.getDerecho())); // paso 1: rotar hijo
        return rotarIzquierda(r); // paso 2: rotar padre
    }

    // Esta rotación se aplica cuando el nodo padre está caído hacia la izquierda
    // (balance 2) y su hijo izquierdo está caído hacia el lado contrario (balance
    // -1).

    private NodoAVL rotarIzquierdaDerecha(NodoAVL r) {
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo())); // paso 1: rotar hijo
        return rotarDerecha(r); // paso 2: rotar padre
    }

    private int getBalance(NodoAVL r) {
        int balIzq = -1;
        int balDer = -1;
        if (r.getIzquierdo() != null) {
            balIzq = r.getIzquierdo().getAltura();
        }
        if (r.getDerecho() != null) {
            balDer = r.getDerecho().getAltura();
        }
        int balance = balIzq - balDer;

        return balance;
    }

    private NodoAVL balancear(NodoAVL n) {
        int b = getBalance(n);
        if (b > 1) {
            if (getBalance(n.getIzquierdo()) >= 0) {
                n = rotarDerecha(n);
            } else {
                n = rotarIzquierdaDerecha(n);
            }
        } else if (b < -1) {
            if (getBalance(n.getDerecho()) <= 0) {
                n = rotarIzquierda(n);
            } else {
                n = rotarDerechaIzquierda(n);
            }
        }
        return n;
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = false;
        if (this.raiz != null) {
            boolean[] arr = { false };
            this.raiz = eliminarAux(this.raiz, elem, arr);
            exito = arr[0];
        }
        return exito;
    }

    private NodoAVL eliminarAux(NodoAVL n, Comparable elem, boolean[] exito) {
        if (n != null) {
            int cmp = elem.compareTo(n.getElem());
            if (cmp < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), elem, exito));
            } else if (cmp > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), elem, exito));
            } else {
                // encontrado — los 3 casos del apunte
                exito[0] = true;
                if (n.getIzquierdo() == null && n.getDerecho() == null) {
                    // CASO 1: hoja — se elimina devolviendo null al padre
                    n = null;
                } else if (n.getIzquierdo() == null) {
                    // CASO 2: un solo hijo derecho — el hijo sube
                    n = n.getDerecho();
                } else if (n.getDerecho() == null) {
                    // CASO 2: un solo hijo izquierdo — el hijo sube
                    n = n.getIzquierdo();
                } else {
                    // CASO 3: dos hijos — candidato B: mínimo del subárbol derecho
                    NodoAVL candidato = n.getDerecho();
                    while (candidato.getIzquierdo() != null) {
                        candidato = candidato.getIzquierdo();
                    }
                    n.setElem(candidato.getElem());
                    // eliminar el candidato del subárbol derecho
                    n.setDerecho(eliminarAux(n.getDerecho(), candidato.getElem(), new boolean[] { true }));
                }
            }
            if (n != null && exito[0]) {
                n.recalcularAltura();
                n = balancear(n);
            }
        }
        return n;
    }

    public Lista listar() {
        Lista lis = new Lista();
        listarAux(this.raiz, lis);
        return lis;
    }

    private void listarAux(NodoAVL nodo, Lista lis) {
        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            listarAux(nodo.getDerecho(), lis);
        }
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        Lista lis = new Lista();
        listarRangoAux(this.raiz, elemMinimo, elemMaximo, lis);
        return lis;
    }

    private void listarRangoAux(NodoAVL n, Comparable min, Comparable max, Lista lis) {
        if (n != null) {
            int cmpMin = n.getElem().compareTo(min);
            int cmpMax = n.getElem().compareTo(max);

            if (cmpMin > 0)
                listarRangoAux(n.getIzquierdo(), min, max, lis);

            if (cmpMin >= 0 && cmpMax <= 0)
                lis.insertar(n.getElem(), lis.longitud() + 1);

            if (cmpMax < 0)
                listarRangoAux(n.getDerecho(), min, max, lis);
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public Comparable minimoElem() {
        Comparable elem = null;
        if (this.raiz != null) {
            NodoAVL n = this.raiz;
            while (n.getIzquierdo() != null) {
                n = n.getIzquierdo();
            }
            elem = n.getElem();
        }
        return elem;
    }

    public Comparable maximoElem() {
        Comparable elem = null;
        if (this.raiz != null) {
            NodoAVL n = this.raiz;
            while (n.getDerecho() != null) {
                n = n.getDerecho();
            }
            elem = n.getElem();
        }
        return elem;
    }

    // en vez de volver boolean como el pertence, devuelve el elemento, necesario
    // para el sistema.
    public Comparable obtenerDato(Comparable elem) {
        Comparable dato = null;
        if (this.raiz != null) {
            dato = obtenerDatoAux(this.raiz, elem);
        }
        return dato;
    }

    private Comparable obtenerDatoAux(NodoAVL n, Comparable elem) {
        Comparable dato = null;
        int cmp = elem.compareTo(n.getElem());
        if (cmp == 0) {
            dato = n.getElem();
        } else if (cmp < 0) {
            if (n.getIzquierdo() != null) {
                dato = obtenerDatoAux(n.getIzquierdo(), elem);
            }
        } else {
            if (n.getDerecho() != null) {
                dato = obtenerDatoAux(n.getDerecho(), elem);
            }
        }

        return dato;
    }

    public String toString() {
        return toStringAux(this.raiz, 0);
    }

    private String toStringAux(NodoAVL n, int nivel) {
        String s = "";
        if (n != null) {
            s += toStringAux(n.getDerecho(), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                s += "    ";
            }
            s += n.getElem() + " (altura=" + n.getAltura() + ")\n";
            s += toStringAux(n.getIzquierdo(), nivel + 1);
        }
        return s;
    }

    public Lista listarMayorIgualQue(Comparable minimo) {
        Lista lis = new Lista();
        listarMayorIgualQueAux(this.raiz, minimo, lis);
        return lis;
    }

    private void listarMayorIgualQueAux(NodoAVL n, Comparable minimo, Lista lis) {
        if (n != null) {
            int cmp = n.getElem().compareTo(minimo);

            if (cmp >= 0) {
                listarMayorIgualQueAux(n.getIzquierdo(), minimo, lis);
                lis.insertar(n.getElem(), lis.longitud() + 1);
                listarMayorIgualQueAux(n.getDerecho(), minimo, lis);
            } else {
                listarMayorIgualQueAux(n.getDerecho(), minimo, lis);
            }
        }
    }
}