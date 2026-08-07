package estructuras.conjuntistas;

import estructuras.lineales.Lista;

public class DiccionarioAVL {
    private NodoAVLDicc raiz;

    public DiccionarioAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVLDicc(clave, dato);
        } else {
            boolean[] exitoArr = { true };
            this.raiz = insertarAux(this.raiz, clave, dato, exitoArr);
            exito = exitoArr[0];
        }
        return exito;
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc n, Comparable clave, Object dato, boolean[] exito) {
        if (n == null) {
            n = new NodoAVLDicc(clave, dato);
        } else {
            int cmp = clave.compareTo(n.getClave());
            if (cmp == 0) {
                exito[0] = false;
            } else if (cmp < 0) {
                n.setIzquierdo(insertarAux(n.getIzquierdo(), clave, dato, exito));
            } else {
                n.setDerecho(insertarAux(n.getDerecho(), clave, dato, exito));
            }
            if (exito[0]) {
                n.recalcularAltura();
                n = balancear(n);
            }
        }
        return n;
    }

    public boolean existeClave(Comparable clave) {
        boolean existe = false;
        if (this.raiz != null) {
            existe = existeClaveAux(this.raiz, clave);
        }
        return existe;
    }

    private boolean existeClaveAux(NodoAVLDicc n, Comparable clave) {
        boolean existe = false;
        int cmp = clave.compareTo(n.getClave());
        if (cmp == 0) {
            existe = true;
        } else if (cmp < 0) {
            if (n.getIzquierdo() != null) {
                existe = existeClaveAux(n.getIzquierdo(), clave);
            }
        } else {
            if (n.getDerecho() != null) {
                existe = existeClaveAux(n.getDerecho(), clave);
            }
        }
        return existe;
    }

    // Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance
    // -2) y su *hijo derecho* está caído hacia el mismo lado (balance -1) o es
    // neutro (balance 0).
    private NodoAVLDicc rotarIzquierda(NodoAVLDicc r) {
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura(); // r bajó, se recalcula primero
        h.recalcularAltura(); // h subió, depende de r ya actualizado
        return h;
    }

    // Esta rotación se aplica cuando el nodo padre está caído a la izquierda
    // (balance 2) y su *hijo izquierdo* está caído hacia el mismo lado (balance 1)
    // o es neutro (balance 0).
    private NodoAVLDicc rotarDerecha(NodoAVLDicc r) {
        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    // Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance
    // -2) y su hijo derecho está caído hacia el lado contrario (balance 1).
    private NodoAVLDicc rotarDerechaIzquierda(NodoAVLDicc r) {
        r.setDerecho(rotarDerecha(r.getDerecho())); // paso 1: rotar hijo
        return rotarIzquierda(r); // paso 2: rotar padre
    }

    // Esta rotación se aplica cuando el nodo padre está caído hacia la izquierda
    // (balance 2) y su hijo izquierdo está caído hacia el lado contrario (balance
    // -1).
    private NodoAVLDicc rotarIzquierdaDerecha(NodoAVLDicc r) {
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo())); // paso 1: rotar hijo
        return rotarDerecha(r); // paso 2: rotar padre
    }

    private int getBalance(NodoAVLDicc r) {
        int balIzq = -1;
        int balDer = -1;
        if (r.getIzquierdo() != null) {
            balIzq = r.getIzquierdo().getAltura();
        }
        if (r.getDerecho() != null) {
            balDer = r.getDerecho().getAltura();
        }
        return balIzq - balDer;
    }

    private NodoAVLDicc balancear(NodoAVLDicc n) {
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

    public boolean eliminar(Comparable clave) {
        boolean exito = false;
        if (this.raiz != null) {
            boolean[] arr = { false };
            this.raiz = eliminarAux(this.raiz, clave, arr);
            exito = arr[0];
        }
        return exito;
    }

    private NodoAVLDicc eliminarAux(NodoAVLDicc n, Comparable clave, boolean[] exito) {
        if (n != null) {
            int cmp = clave.compareTo(n.getClave());
            if (cmp < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), clave, exito));
            } else if (cmp > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), clave, exito));
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
                    // CASO 3: dos hijos — candidato: mínimo del subárbol derecho
                    NodoAVLDicc candidato = n.getDerecho();
                    while (candidato.getIzquierdo() != null) {
                        candidato = candidato.getIzquierdo();
                    }
                    n.setClave(candidato.getClave());
                    n.setDato(candidato.getDato());
                    // eliminar el candidato del subárbol derecho
                    n.setDerecho(eliminarAux(n.getDerecho(), candidato.getClave(), new boolean[] { true }));
                }
            }
            if (n != null && exito[0]) {
                n.recalcularAltura();
                n = balancear(n);
            }
        }
        return n;
    }

    public Object obtenerInformacion(Comparable clave) {
        Object dato = null;
        if (this.raiz != null) {
            dato = obtenerInformacionAux(this.raiz, clave);
        }
        return dato;
    }

    private Object obtenerInformacionAux(NodoAVLDicc n, Comparable clave) {
        Object dato = null;
        int cmp = clave.compareTo(n.getClave());
        if (cmp == 0) {
            dato = n.getDato();
        } else if (cmp < 0) {
            if (n.getIzquierdo() != null) {
                dato = obtenerInformacionAux(n.getIzquierdo(), clave);
            }
        } else {
            if (n.getDerecho() != null) {
                dato = obtenerInformacionAux(n.getDerecho(), clave);
            }
        }
        return dato;
    }

    public Lista listarClaves() {
        Lista lis = new Lista();
        listarClavesAux(this.raiz, lis);
        return lis;
    }

    private void listarClavesAux(NodoAVLDicc n, Lista lis) {
        if (n != null) {
            listarClavesAux(n.getIzquierdo(), lis);
            lis.insertar(n.getClave(), lis.longitud() + 1);
            listarClavesAux(n.getDerecho(), lis);
        }
    }

    public Lista listarDatos() {
        Lista lis = new Lista();
        listarDatosAux(this.raiz, lis);
        return lis;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista lis) {
        if (n != null) {
            listarDatosAux(n.getIzquierdo(), lis);
            lis.insertar(n.getDato(), lis.longitud() + 1);
            listarDatosAux(n.getDerecho(), lis);
        }
    }

    public Lista listarDatosRango(Comparable claveMinima, Comparable claveMaxima) {
        Lista lis = new Lista();
        listarDatosRangoAux(this.raiz, claveMinima, claveMaxima, lis);
        return lis;
    }

    private void listarDatosRangoAux(NodoAVLDicc n, Comparable min, Comparable max, Lista lis) {
        if (n != null) {
            int cmpMin = n.getClave().compareTo(min);
            int cmpMax = n.getClave().compareTo(max);

            if (cmpMin > 0)
                listarDatosRangoAux(n.getIzquierdo(), min, max, lis);

            if (cmpMin >= 0 && cmpMax <= 0)
                lis.insertar(n.getDato(), lis.longitud() + 1);

            if (cmpMax < 0)
                listarDatosRangoAux(n.getDerecho(), min, max, lis);
        }
    }

    public Lista listarDatosMayorIgualQue(Comparable claveMinima) {
        Lista lis = new Lista();
        listarDatosMayorIgualQueAux(this.raiz, claveMinima, lis);
        return lis;
    }

    private void listarDatosMayorIgualQueAux(NodoAVLDicc n, Comparable minima, Lista lis) {
        if (n != null) {
            int cmp = n.getClave().compareTo(minima);
            if (cmp >= 0) {
                listarDatosMayorIgualQueAux(n.getIzquierdo(), minima, lis);
                lis.insertar(n.getDato(), lis.longitud() + 1);
            }
            listarDatosMayorIgualQueAux(n.getDerecho(), minima, lis);
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public DiccionarioAVL clone() {
        DiccionarioAVL clon = new DiccionarioAVL();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVLDicc cloneAux(NodoAVLDicc n) {
        NodoAVLDicc nuevo = null;
        if (n != null) {
            nuevo = new NodoAVLDicc(n.getClave(), n.getDato());
            nuevo.setIzquierdo(cloneAux(n.getIzquierdo()));
            nuevo.setDerecho(cloneAux(n.getDerecho()));
            nuevo.recalcularAltura();
        }
        return nuevo;
    }

    @Override
    public String toString() {
        return toStringAux(this.raiz, 0);
    }

    private String toStringAux(NodoAVLDicc n, int nivel) {
        String s = "";
        if (n != null) {
            s += toStringAux(n.getDerecho(), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                s += "    ";
            }
            s += n.getClave() + " (altura=" + n.getAltura() + ")\n";
            s += toStringAux(n.getIzquierdo(), nivel + 1);
        }
        return s;
    }
}
